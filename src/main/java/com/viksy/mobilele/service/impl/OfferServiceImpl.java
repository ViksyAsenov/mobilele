package com.viksy.mobilele.service.impl;

import com.viksy.mobilele.model.entity.OfferEntity;
import com.viksy.mobilele.model.entity.UserEntity;
import com.viksy.mobilele.model.entity.UserRoleEntity;
import com.viksy.mobilele.model.entity.enums.EngineEnum;
import com.viksy.mobilele.model.entity.enums.TransmissionEnum;
import com.viksy.mobilele.model.entity.enums.UserRoleEnum;
import com.viksy.mobilele.model.service.OfferUpdateServiceModel;
import com.viksy.mobilele.model.service.OfferUploadServiceModel;
import com.viksy.mobilele.model.view.OfferSummaryView;
import com.viksy.mobilele.repository.BrandRepository;
import com.viksy.mobilele.repository.ModelRepository;
import com.viksy.mobilele.repository.OfferRepository;
import com.viksy.mobilele.repository.UserRepository;
import com.viksy.mobilele.service.OfferService;
import com.viksy.mobilele.web.exception.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OfferServiceImpl implements OfferService {
    private final OfferRepository offerRepository;
    private final ModelMapper modelMapper;
    private final BrandRepository brandRepository;
    private final ModelRepository modelRepository;
    private final UserRepository userRepository;

    public OfferServiceImpl(OfferRepository offerRepository, ModelMapper modelMapper, BrandRepository brandRepository, ModelRepository modelRepository, UserRepository userRepository) {
        this.offerRepository = offerRepository;
        this.modelMapper = modelMapper;
        this.brandRepository = brandRepository;
        this.modelRepository = modelRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<OfferSummaryView> getAllOffers() {
        return offerRepository.
                findAll().
                stream().
                map(this::map).
                collect(Collectors.toList());
    }

    @Override
    public OfferEntity findById(Long id) {
        return offerRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Offer with id " + id + " was not found!"));
    }

    @Override
    public void deleteOffer(Long id) {
        offerRepository.deleteById(id);
    }

    @Override
    public boolean isOwner(String username, Long id, boolean canThrowError) {
        OfferEntity offerEntity = offerRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Offer with id " + id + " was not found!"));
        UserEntity userEntity = userRepository.findByUsername(username).orElseThrow(() -> new ObjectNotFoundException("User with name " + username + " was not found!"));

        boolean isOfferOwner = isAdmin(userEntity) || offerEntity.getSeller().getUsername().equals(username);

        if(!isOfferOwner && canThrowError) {
            throw new ObjectNotFoundException("You don't have access to this page!");
        }

        return isOfferOwner;
    }

    @Override
    public boolean isActive(String username) {
        UserEntity userEntity = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new ObjectNotFoundException("User with name " + username + " not found"));

        if(!userEntity.isVerified()) {
            throw new ObjectNotFoundException("You need to verify your account before uploading an offer!");
        }

        if(userEntity.isLocked()) {
            throw new ObjectNotFoundException("Your account has been locked!");
        }

        return (userEntity.isVerified() && !userEntity.isLocked());
    }

    private boolean isAdmin(UserEntity userEntity) {
        return userEntity
                .getRoles()
                .stream()
                .map(UserRoleEntity::getRole)
                .anyMatch(r -> r.equals(UserRoleEnum.ADMIN));
    }

    @Override
    public void updateOffer(OfferUpdateServiceModel offerModel) {
        OfferEntity offerEntity = offerRepository.findById(offerModel.getId()).orElseThrow(() ->
                new ObjectNotFoundException("Offer with id " + offerModel.getId() + " not found!"));

        offerEntity
                .setMileage(offerModel.getMileage())
                .setPrice(offerModel.getPrice())
                .setEngine(offerModel.getEngine())
                .setTransmission(offerModel.getTransmission())
                .setYear(offerModel.getYear())
                .setDescription(offerModel.getDescription())
                .setImageUrl(offerModel.getImageUrl())
                .setModified(Instant.now());

        offerRepository.save(offerEntity);
    }

    @Override
    public void saveOffer(OfferUploadServiceModel offerModel, Principal principal) {
        OfferEntity offerEntity = new OfferEntity();

        String brandName = offerModel.getBrandModelName().split(" ")[0];
        String modelName = offerModel.getBrandModelName().split(" ")[1];

        offerEntity
                .setBrand(brandRepository.getByName(brandName))
                .setModel(modelRepository.getByName(modelName))
                .setPrice(offerModel.getPrice())
                .setEngine(EngineEnum.valueOf(offerModel.getEngine()))
                .setTransmission(TransmissionEnum.valueOf(offerModel.getTransmission()))
                .setYear(offerModel.getYear())
                .setMileage(offerModel.getMileage())
                .setDescription(offerModel.getDescription())
                .setImageUrl(offerModel.getImageUrl())
                .setSeller(userRepository.findByUsername(principal.getName()).orElse(null));

        offerRepository.save(offerEntity);
    }

    private OfferSummaryView map(OfferEntity offerEntity) {
        OfferSummaryView summaryView = this.modelMapper.map(offerEntity, OfferSummaryView.class);
        summaryView.setModel(offerEntity.getModel().getName());
        summaryView.setBrand(offerEntity.getBrand().getName());
        return summaryView;
    }
}
