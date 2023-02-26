package com.viksy.mobilele.model.service;


public class OfferUploadServiceModel {
    private String brandModelName;
    private int price;
    private String engine;
    private String transmission;
    private int year;
    private int mileage;
    private String description;
    private String imageUrl;

    public String getBrandModelName() {
        return brandModelName;
    }

    public OfferUploadServiceModel setBrandModelName(String brandModelName) {
        this.brandModelName = brandModelName;
        return this;
    }

    public int getPrice() {
        return price;
    }

    public OfferUploadServiceModel setPrice(int price) {
        this.price = price;
        return this;
    }

    public String getEngine() {
        return engine;
    }

    public OfferUploadServiceModel setEngine(String engine) {
        this.engine = engine;
        return this;
    }

    public String getTransmission() {
        return transmission;
    }

    public OfferUploadServiceModel setTransmission(String transmission) {
        this.transmission = transmission;
        return this;
    }

    public int getYear() {
        return year;
    }

    public OfferUploadServiceModel setYear(int year) {
        this.year = year;
        return this;
    }

    public int getMileage() {
        return mileage;
    }

    public OfferUploadServiceModel setMileage(int mileage) {
        this.mileage = mileage;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public OfferUploadServiceModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public OfferUploadServiceModel setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }
}
