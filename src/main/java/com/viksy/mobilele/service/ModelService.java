package com.viksy.mobilele.service;

import com.viksy.mobilele.model.entity.ModelEntity;

import java.util.List;

public interface ModelService {
    List<ModelEntity> getAllModels();
    List<ModelEntity> getAllModelsByBrand(Long id);
}
