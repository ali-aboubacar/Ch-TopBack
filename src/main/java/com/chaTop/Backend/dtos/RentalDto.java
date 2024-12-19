package com.chaTop.Backend.dtos;

import java.time.LocalDateTime;
import java.util.List;

public class RentalDto {
    private long id;
    private String name;
    private Integer surface;
    private double price;
    private List<String> picture;
    private String description;
    private String createdAt;
    private String updatedAt;
    private long owner_id;

    public long getOwner_id() {
        return owner_id;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public Integer getSurface() {
        return surface;
    }

    public List<String> getPicture() {
        return picture;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSurface(Integer surface) {
        this.surface = surface;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setPicture(List<String> picture) {
        this.picture = picture;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setOwner_id(long owner_id) {
        this.owner_id = owner_id;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
