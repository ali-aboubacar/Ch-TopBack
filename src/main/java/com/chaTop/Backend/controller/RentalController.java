package com.chaTop.Backend.controller;

import com.chaTop.Backend.model.Rental;
import com.chaTop.Backend.service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.text.html.HTML;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class RentalController {
    @Autowired
    RentalService rentalService;

    @GetMapping("/rentals")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<List<Rental>> getAllRentals(){
        try {
            List<Rental> rentals = new ArrayList<Rental>();
            rentals = rentalService.getAllRentals();

            return new ResponseEntity<>(rentals, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/rentals/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Rental> getRentalById(@PathVariable("id") long id){
        try {
            Optional<Rental> rental = rentalService.getRentalById(id);
            return rental.map(value -> new ResponseEntity(value, HttpStatus.OK))
                         .orElseGet(() -> new ResponseEntity(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/rentals")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Rental> createRental( @RequestParam("name") String name,
                                                @RequestParam("surface") int surface,
                                                @RequestParam("price") double price,
                                                @RequestParam("picture") MultipartFile picture,
                                                @RequestParam("description") String description ){
        try {
            return new ResponseEntity<>(rentalService.createRental(name, surface, price, picture, description), HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/rentals/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Rental> updateRental(@PathVariable("id") long id, @RequestBody Rental rental){
        try {
            Optional<Rental> rentalData = rentalService.getRentalById(id);
            if (rentalData.isPresent()){
                Rental updatedRental = rentalService.updateRental(rentalData.get(), rental);
                return new ResponseEntity<>(updatedRental, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/rentals/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<HttpStatus> deleteRental(@PathVariable("id") long id){
        try {
            rentalService.deleteRental(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
