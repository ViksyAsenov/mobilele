package com.viksy.mobilele.web;

import com.viksy.mobilele.model.binding.OfferUpdateBindingModel;
import com.viksy.mobilele.model.binding.OfferUploadBindingModel;
import com.viksy.mobilele.model.binding.PictureBindingModel;
import com.viksy.mobilele.model.entity.BrandEntity;
import com.viksy.mobilele.model.entity.CloudinaryImage;
import com.viksy.mobilele.model.entity.OfferEntity;
import com.viksy.mobilele.model.entity.PictureEntity;
import com.viksy.mobilele.model.entity.enums.EngineEnum;
import com.viksy.mobilele.model.entity.enums.TransmissionEnum;
import com.viksy.mobilele.model.service.OfferUpdateServiceModel;
import com.viksy.mobilele.model.service.OfferUploadServiceModel;
import com.viksy.mobilele.model.view.OfferDetailsView;
import com.viksy.mobilele.model.view.UploadSummaryView;
import com.viksy.mobilele.repository.PictureRepository;
import com.viksy.mobilele.service.BrandService;
import com.viksy.mobilele.service.CloudinaryService;
import com.viksy.mobilele.service.ModelService;
import com.viksy.mobilele.service.OfferService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class OffersController {
    private final OfferService offerService;
    private final BrandService brandService;
    private final ModelService modelService;
    private final ModelMapper modelMapper;
    private final CloudinaryService cloudinaryService;
    private final PictureRepository pictureRepository;

    public OffersController(OfferService offerService, BrandService brandService, ModelService modelService, ModelMapper modelMapper, CloudinaryService cloudinaryService, PictureRepository pictureRepository) {
        this.offerService = offerService;
        this.brandService = brandService;
        this.modelService = modelService;
        this.modelMapper = modelMapper;
        this.cloudinaryService = cloudinaryService;
        this.pictureRepository = pictureRepository;
    }

    @PreAuthorize("@offerServiceImpl.isActive(#principal.name)")
    @GetMapping("/offers/add")
    public String addOffer(Model model, Principal principal) {
        List<UploadSummaryView> uploadSummaryViews = getUploadSummaryViews();

        model.addAttribute("uploadSummaryViews", uploadSummaryViews);
        model.addAttribute("offerUploadBindingModel", new OfferUploadBindingModel());
        model.addAttribute("pictureModel", new PictureBindingModel());
        model.addAttribute("engines", EngineEnum.values());
        model.addAttribute("transmissions", TransmissionEnum.values());

        return "offer-add";
    }

    @Transactional
    @PreAuthorize("@offerServiceImpl.isActive(#principal.name)")
    @PostMapping("/offers/add")
    public String saveOffer(@Valid OfferUploadBindingModel offerUploadBindingModel, BindingResult bindingResult, RedirectAttributes redirectAttributes, PictureBindingModel pictureBindingModel, Principal principal) throws IOException {
        if(bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("offerUploadBindingModel", offerUploadBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.offerUploadBindingModel", bindingResult);

            return "redirect:/offers/add/errors";
        }

        OfferUploadServiceModel offerModel = modelMapper.map(offerUploadBindingModel, OfferUploadServiceModel.class);

        if(!pictureBindingModel.getPicture().isEmpty()) {
            PictureEntity pictureEntity = createPictureEntity(pictureBindingModel.getPicture(), pictureBindingModel.getPicture().getOriginalFilename());

            pictureRepository.save(pictureEntity);

            offerModel.setImageUrl(pictureEntity.getUrl());
        } else {
            offerModel.setImageUrl(cloudinaryService.getDefaultUrl());
        }

        offerService.saveOffer(offerModel, principal);

        return "redirect:/offers/all";
    }

    @GetMapping("/offers/add/errors")
    public String addOfferErrors(Model model) {
        List<UploadSummaryView> uploadSummaryViews = getUploadSummaryViews();

        model.addAttribute("uploadSummaryViews", uploadSummaryViews);
        model.addAttribute("engines", EngineEnum.values());
        model.addAttribute("transmissions", TransmissionEnum.values());
        model.addAttribute("pictureModel", new PictureBindingModel());

        return "offer-add";
    }

    @GetMapping("/offers/all")
    public String allOffers(Model model) {
        model.addAttribute("offers", offerService.getAllOffers());
        return "offers";
    }

    @GetMapping("/offers/{id}/details")
    public String showOffer(@PathVariable Long id, Principal principal, Model model) {
        OfferEntity offerEntity = offerService.findById(id);

        OfferDetailsView offerDetailsView = getOfferDetailsView(offerEntity, principal, id);

        model.addAttribute("offer", offerDetailsView);

        return "details";
    }

    @Transactional
    @PreAuthorize("@offerServiceImpl.isOwner(#principal.name, #id, true)")
    @DeleteMapping("/offers/{id}")
    public String deleteOffer(@PathVariable Long id, Principal principal) {
        OfferEntity offerEntity = offerService.findById(id);

        PictureEntity pictureEntity = pictureRepository.findPictureEntityByUrl(offerEntity.getImageUrl());

        if(cloudinaryService.delete(pictureEntity.getPublicId())) {
            pictureRepository.deleteByPublicId(pictureEntity.getPublicId());
        }

        offerService.deleteOffer(id);

        return "redirect:/offers/all";
    }

    @PreAuthorize("@offerServiceImpl.isOwner(#principal.name, #id, true)")
    @GetMapping("/offers/{id}/update")
    public String updateOffer(@PathVariable Long id, Principal principal, Model model) {
        OfferEntity offerEntity = offerService.findById(id);

        OfferUpdateBindingModel offerModel = modelMapper.map(offerEntity, OfferUpdateBindingModel.class);

        model.addAttribute("offerModel", offerModel);
        model.addAttribute("pictureModel", new PictureBindingModel());
        model.addAttribute("engines", EngineEnum.values());
        model.addAttribute("transmissions", TransmissionEnum.values());

        return "update";
    }

    @GetMapping("/offers/{id}/update/errors")
    public String updateOfferErrors(@PathVariable Long id, Model model) {
        model.addAttribute("engines", EngineEnum.values());
        model.addAttribute("transmissions", TransmissionEnum.values());
        model.addAttribute("pictureModel", new PictureBindingModel());

        return "update";
    }

    @Transactional
    @PreAuthorize("@offerServiceImpl.isOwner(#principal.name, #id, true)")
    @PatchMapping("/offers/{id}/update")
    public String updateOffer(@PathVariable Long id, @Valid OfferUpdateBindingModel offerModel, BindingResult bindingResult, RedirectAttributes redirectAttributes, PictureBindingModel pictureBindingModel, Principal principal) throws IOException {
        if(bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("offerModel", offerModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.offerModel", bindingResult);

            return "redirect:/offers/" + id + "/update/errors";
        }

        OfferUpdateServiceModel serviceModel = modelMapper.map(offerModel, OfferUpdateServiceModel.class);
        PictureEntity oldPictureEntity = pictureRepository.findPictureEntityByUrl(offerService.findById(id).getImageUrl());

        if(!pictureBindingModel.getPicture().isEmpty()) {
            if(oldPictureEntity != null) {
                cloudinaryService.delete(oldPictureEntity.getPublicId());
            }

            PictureEntity newPictureEntity = createPictureEntity(pictureBindingModel.getPicture(), pictureBindingModel.getPicture().getName());

            pictureRepository.save(newPictureEntity);
            serviceModel.setImageUrl(newPictureEntity.getUrl());
        } else {
            serviceModel.setImageUrl(oldPictureEntity.getUrl());
        }

        serviceModel.setId(id);
        offerService.updateOffer(serviceModel);

        return "redirect:/offers/" + id + "/details";
    }

    private OfferDetailsView getOfferDetailsView(OfferEntity offerEntity, Principal principal, Long id) {
        OfferDetailsView offerDetailsView = modelMapper.map(offerEntity, OfferDetailsView.class);

        offerDetailsView.setModel(offerEntity.getModel().getName());
        offerDetailsView.setBrand(offerEntity.getBrand().getName());
        offerDetailsView.setFirstName(offerEntity.getSeller().getFirstName());
        offerDetailsView.setLastName(offerEntity.getSeller().getLastName());
        offerDetailsView.setOwner(offerService.isOwner(principal.getName(), id, false));

        return offerDetailsView;
    }
    private List<UploadSummaryView> getUploadSummaryViews() {
        List<UploadSummaryView> uploadSummaryViews = new ArrayList<>();
        List<BrandEntity> brandEntities = brandService.getAllBrands();

        for (int i = 0; i < brandEntities.size(); i++) {
            uploadSummaryViews.add(i, new UploadSummaryView());
            BrandEntity brandEntity = brandEntities.get(i);

            uploadSummaryViews.get(i)
                    .setName(brandEntity.getName())
                    .setModels(modelService.getAllModelsByBrand(brandEntity.getId()));
        }

        return uploadSummaryViews;
    }

    private PictureEntity createPictureEntity(MultipartFile file, String title) throws IOException {
        CloudinaryImage uploadResult = this.cloudinaryService.upload(file);

        return new PictureEntity()
                .setTitle(title)
                .setUrl(uploadResult.getUrl())
                .setPublicId(uploadResult.getPublicId());
    }
}
