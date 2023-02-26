package com.viksy.mobilele.model.binding;

import org.springframework.web.multipart.MultipartFile;

public class PictureBindingModel {
    private MultipartFile picture;

    public MultipartFile getPicture() {
        return picture;
    }

    public PictureBindingModel setPicture(MultipartFile picture) {
        this.picture = picture;
        return this;
    }
}
