package com.viksy.mobilele.service;

import com.viksy.mobilele.model.binding.OfferUploadBindingModel;
import com.viksy.mobilele.model.entity.OfferEntity;
import com.viksy.mobilele.model.service.OfferUpdateServiceModel;
import com.viksy.mobilele.model.service.OfferUploadServiceModel;
import com.viksy.mobilele.model.view.OfferDetailsView;
import com.viksy.mobilele.model.view.OfferSummaryView;

import java.security.Principal;
import java.util.List;

public interface OfferService {
    List<OfferSummaryView> getAllOffers();
    OfferEntity findById(Long id);
    void deleteOffer(Long id);
    boolean isOwner(String username, Long id, boolean canThrowError);
    boolean isActive(String username);
    void updateOffer(OfferUpdateServiceModel offerModel);
    void saveOffer(OfferUploadServiceModel offerModel, Principal principal);
}
