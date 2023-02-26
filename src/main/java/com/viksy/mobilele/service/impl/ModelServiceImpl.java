package com.viksy.mobilele.service.impl;

import com.viksy.mobilele.model.entity.ModelEntity;
import com.viksy.mobilele.repository.ModelRepository;
import com.viksy.mobilele.service.ModelService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ModelServiceImpl implements ModelService {
    private final ModelRepository modelRepository;

    public ModelServiceImpl(ModelRepository modelRepository) {
        this.modelRepository = modelRepository;
    }

    @Override
    public List<ModelEntity> getAllModels() {
        return new ArrayList<>(modelRepository.findAll());
    }

    @Override
    public List<ModelEntity> getAllModelsByBrand(Long id) {
        return modelRepository.findByBrand_Id(id);
    }
}
