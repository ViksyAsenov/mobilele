package com.viksy.mobilele.repository;

import com.viksy.mobilele.model.entity.BrandEntity;
import com.viksy.mobilele.model.entity.ModelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModelRepository extends JpaRepository<ModelEntity, Long> {
    List<ModelEntity> findByBrand_Id(Long id);
    ModelEntity getByName(String name);
}
