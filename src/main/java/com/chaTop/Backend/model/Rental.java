package com.chaTop.Backend.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "rentals")
public class Rental {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "surface")
    private Integer surface;

    @Column(name = "price")
    private double price;

    @Column(name = "picture")
    private String picture;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User user;
    @OneToMany(mappedBy = "id")
    private Set<Message> messages = new HashSet<>();
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @CreationTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    public Rental() {

    }

    public Rental(String name, Integer surface, double price, String picture, String description, User user) {
        this.name = name;
        this.surface = surface;
        this.price = price;
        this.picture = picture;
        this.description = description;
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
    public Integer getSurface() {
        return surface;
    }

    public void setSurface(Integer email) {
        this.surface = surface;
    }
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Rental [id=" + id + ", name=" + name + ", surface=" + surface + ", description=" + description + ", price-"+ price +"]";
    }
}
