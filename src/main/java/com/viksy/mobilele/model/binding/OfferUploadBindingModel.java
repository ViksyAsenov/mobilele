package com.viksy.mobilele.model.binding;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

public class OfferUploadBindingModel {
    @NotBlank
    private String brandModelName;
    @NotNull
    @Min(100)
    private int price;
    @NotBlank
    private String engine;
    @NotBlank
    private String transmission;
    @NotNull
    @Min(1886)
    private int year;
    @NotNull
    @PositiveOrZero
    private int mileage;
    @NotBlank
    private String description;

    public String getBrandModelName() {
        return brandModelName;
    }

    public OfferUploadBindingModel setBrandModelName(String brandModelName) {
        this.brandModelName = brandModelName;
        return this;
    }

    public int getPrice() {
        return price;
    }

    public OfferUploadBindingModel setPrice(int price) {
        this.price = price;
        return this;
    }

    public String getEngine() {
        return engine;
    }

    public OfferUploadBindingModel setEngine(String engine) {
        this.engine = engine;
        return this;
    }

    public String getTransmission() {
        return transmission;
    }

    public OfferUploadBindingModel setTransmission(String transmission) {
        this.transmission = transmission;
        return this;
    }

    public int getYear() {
        return year;
    }

    public OfferUploadBindingModel setYear(int year) {
        this.year = year;
        return this;
    }

    public int getMileage() {
        return mileage;
    }

    public OfferUploadBindingModel setMileage(int mileage) {
        this.mileage = mileage;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public OfferUploadBindingModel setDescription(String description) {
        this.description = description;
        return this;
    }
}
