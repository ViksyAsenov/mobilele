package com.viksy.mobilele.model.view;

import com.viksy.mobilele.model.entity.ModelEntity;

import java.util.List;

public class UploadSummaryView {
    private String name;
    private List<ModelEntity> models;

    public String getName() {
        return name;
    }

    public UploadSummaryView setName(String name) {
        this.name = name;
        return this;
    }

    public List<ModelEntity> getModels() {
        return models;
    }

    public UploadSummaryView setModels(List<ModelEntity> models) {
        this.models = models;
        return this;
    }
}
