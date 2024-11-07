package com.chaTop.Backend.service;

import com.chaTop.Backend.model.Rental;
import com.chaTop.Backend.repository.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class RentalService {
    @Autowired
    RentalRepository rentalRepository;

    public List<Rental> getAllRentals(){
        List<Rental> rentals = new ArrayList<Rental>();
        rentalRepository.findAll().forEach(rentals::add);

        return rentals;
    }

    public Optional<Rental> getRentalById(long id){
        return rentalRepository.findById(id);
    }

    public Rental createRental(Rental rental){
        return rentalRepository.save(new Rental(rental.getName(),
                rental.getSurface(),
                rental.getPrice(),
                rental.getDescription(),
                rental.getPicture()));
    }

    public Rental updateRental(Rental initialRental, Rental newRental){
        initialRental.setName(newRental.getName());
        initialRental.setDescription(newRental.getDescription());
        initialRental.setPicture(newRental.getPicture());
        initialRental.setPrice(newRental.getPrice());
        initialRental.setSurface(newRental.getSurface());
        return rentalRepository.save(initialRental);
    }

    public void deleteRental(long id){
        rentalRepository.deleteById(id);
    }
}
