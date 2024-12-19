package com.chaTop.Backend.payload.response;

import com.chaTop.Backend.dtos.RentalDto;
import com.chaTop.Backend.model.Rental;

import java.util.List;
import java.util.Map;

public class RentalsResponse {
    private List<RentalDto> rentals;

    public List<RentalDto> getRentals(){
        return this.rentals;
    }

    public void setRentals(List<RentalDto> rentals){
        this.rentals = rentals;
    }
}
