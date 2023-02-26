package com.viksy.mobilele.repository;

import com.viksy.mobilele.model.entity.PictureEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PictureRepository extends JpaRepository<PictureEntity, Long> {
    PictureEntity findPictureEntityByUrl(String url);
    void deleteByPublicId(String publicId);
}
