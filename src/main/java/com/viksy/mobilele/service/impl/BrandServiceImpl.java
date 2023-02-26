package com.viksy.mobilele.service.impl;

import com.viksy.mobilele.model.entity.BrandEntity;
import com.viksy.mobilele.repository.BrandRepository;
import com.viksy.mobilele.service.BrandService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {
    private final BrandRepository brandRepository;

    public BrandServiceImpl(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    @Override
    public List<BrandEntity> getAllBrands() {
        return new ArrayList<>(brandRepository.findAll());
    }
}
