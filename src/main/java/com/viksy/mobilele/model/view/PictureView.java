package com.viksy.mobilele.model.view;

public class PictureView {
    private String title;
    private String publicId;
    private String url;

    public String getTitle() {
        return title;
    }

    public PictureView setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getPublicId() {
        return publicId;
    }

    public PictureView setPublicId(String publicId) {
        this.publicId = publicId;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public PictureView setUrl(String url) {
        this.url = url;
        return this;
    }
}
