package com.viksy.mobilele.model.view;

import com.viksy.mobilele.model.entity.enums.EngineEnum;
import com.viksy.mobilele.model.entity.enums.TransmissionEnum;

import java.time.Instant;

public class OfferDetailsView {
    private String description;
    private EngineEnum engine;
    private Instant created;
    private Instant modified;
    private String imageUrl;
    private int mileage;
    private String firstName;
    private String lastName;
    private int price;
    private TransmissionEnum transmission;
    private int year;
    private String model;
    private String brand;
    private boolean isOwner;
    private Long id;

    public String getDescription() {
        return description;
    }

    public OfferDetailsView setDescription(String description) {
        this.description = description;
        return this;
    }

    public EngineEnum getEngine() {
        return engine;
    }

    public OfferDetailsView setEngine(EngineEnum engine) {
        this.engine = engine;
        return this;
    }

    public Instant getCreated() {
        return created;
    }

    public OfferDetailsView setCreated(Instant created) {
        this.created = created;
        return this;
    }

    public Instant getModified() {
        return modified;
    }

    public OfferDetailsView setModified(Instant modified) {
        this.modified = modified;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public OfferDetailsView setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public int getMileage() {
        return mileage;
    }

    public OfferDetailsView setMileage(int mileage) {
        this.mileage = mileage;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public OfferDetailsView setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public OfferDetailsView setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public int getPrice() {
        return price;
    }

    public OfferDetailsView setPrice(int price) {
        this.price = price;
        return this;
    }

    public TransmissionEnum getTransmission() {
        return transmission;
    }

    public OfferDetailsView setTransmission(TransmissionEnum transmission) {
        this.transmission = transmission;
        return this;
    }

    public int getYear() {
        return year;
    }

    public OfferDetailsView setYear(int year) {
        this.year = year;
        return this;
    }

    public String getModel() {
        return model;
    }

    public OfferDetailsView setModel(String model) {
        this.model = model;
        return this;
    }

    public String getBrand() {
        return brand;
    }

    public OfferDetailsView setBrand(String brand) {
        this.brand = brand;
        return this;
    }

    public boolean isOwner() {
        return isOwner;
    }

    public OfferDetailsView setOwner(boolean owner) {
        isOwner = owner;
        return this;
    }

    public Long getId() {
        return id;
    }

    public OfferDetailsView setId(Long id) {
        this.id = id;
        return this;
    }
}
