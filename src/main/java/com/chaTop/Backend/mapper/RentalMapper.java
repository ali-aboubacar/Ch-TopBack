package com.chaTop.Backend.mapper;

import com.chaTop.Backend.dtos.RentalDto;
import com.chaTop.Backend.model.Rental;
import com.chaTop.Backend.model.User;
import com.chaTop.Backend.repository.UserRepository;
import com.chaTop.Backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class RentalMapper {
    @Autowired
    UserService userService;
    public RentalDto toRentalDto(Rental rental){
        if (rental == null){
            return null;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");

        RentalDto rentalDto = new RentalDto();
        rentalDto.setId(rental.getId());
        rentalDto.setName(rental.getName());
        rentalDto.setSurface(rental.getSurface());
        rentalDto.setPrice(rental.getPrice());
        rentalDto.setPicture(rental.getPicture());
        rentalDto.setDescription(rental.getDescription());
//        rentalDto.setCreatedAt(rental.getCreatedAt());
//        rentalDto.setUpdatedAt(rental.getUpdatedAt());
        rentalDto.setOwner_id(rental.getOwner().getId());
        return rentalDto;
    }

    public Rental toRental(RentalDto rentalDto){
        if (rentalDto == null){
            return null;
        }
        User owner = userService.getUserById(rentalDto.getOwner_id()).orElseThrow(() -> new RuntimeException("User not found"));
        Rental rental = new Rental();
        rental.setId(rentalDto.getId());
        rental.setName(rentalDto.getName());
        rental.setSurface(rentalDto.getSurface());
        rental.setPrice(rentalDto.getPrice());
        rental.setPicture(rentalDto.getPicture());
        rental.setDescription(rentalDto.getDescription());
//        rental.setUpdatedAt(rentalDto.getUpdatedAt());
//        rental.setCreatedAt(rentalDto.getCreatedAt());
        rental.setOwner(owner);
        return rental;
    }
}
