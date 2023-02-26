package com.viksy.mobilele.model.binding;

import com.viksy.mobilele.model.entity.enums.EngineEnum;
import com.viksy.mobilele.model.entity.enums.TransmissionEnum;

import javax.validation.constraints.*;

public class OfferUpdateBindingModel {
    @NotBlank
    private String description;
    @NotNull
    private EngineEnum engine;
    @NotNull
    @PositiveOrZero
    private Integer mileage;
    @NotNull
    @Min(100)
    private Integer price;
    @NotNull
    private TransmissionEnum transmission;
    @NotNull
    @Min(1886)
    private Integer year;
    private Long id;

    public String getDescription() {
        return description;
    }

    public OfferUpdateBindingModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public EngineEnum getEngine() {
        return engine;
    }

    public OfferUpdateBindingModel setEngine(EngineEnum engine) {
        this.engine = engine;
        return this;
    }

    public int getMileage() {
        return mileage;
    }

    public OfferUpdateBindingModel setMileage(int mileage) {
        this.mileage = mileage;
        return this;
    }

    public int getPrice() {
        return price;
    }

    public OfferUpdateBindingModel setPrice(int price) {
        this.price = price;
        return this;
    }

    public TransmissionEnum getTransmission() {
        return transmission;
    }

    public OfferUpdateBindingModel setTransmission(TransmissionEnum transmission) {
        this.transmission = transmission;
        return this;
    }

    public int getYear() {
        return year;
    }

    public OfferUpdateBindingModel setYear(int year) {
        this.year = year;
        return this;
    }

    public Long getId() {
        return id;
    }

    public OfferUpdateBindingModel setId(Long id) {
        this.id = id;
        return this;
    }
}
