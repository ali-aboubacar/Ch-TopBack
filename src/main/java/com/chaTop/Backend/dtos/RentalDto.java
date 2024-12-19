package com.chaTop.Backend.dtos;

import com.chaTop.Backend.model.Rental;

import java.time.LocalDateTime;
import java.util.List;

public class RentalsDto {
    private long id;
    private String name;

    private Integer surface;

    private double price;

    private List<String> picture;

    private String description;

    private LocalDateTime createdAt;

}
