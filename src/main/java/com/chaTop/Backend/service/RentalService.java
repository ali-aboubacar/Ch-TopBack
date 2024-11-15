package com.chaTop.Backend.service;

import com.chaTop.Backend.jwtUtil.JwtUtils;
import com.chaTop.Backend.model.Rental;
import com.chaTop.Backend.model.User;
import com.chaTop.Backend.payload.response.JwtResponse;
import com.chaTop.Backend.repository.RentalRepository;
import com.chaTop.Backend.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class RentalService {
    private static final Logger logger = LoggerFactory.getLogger(RentalService.class);

    private final Path root = Paths.get("uploads");
    @Autowired
    RentalRepository rentalRepository;

    @Autowired
    UserRepository userRepository;

    public List<Rental> getAllRentals(){
        List<Rental> rentals = new ArrayList<Rental>();
        rentalRepository.findAll().forEach(rentals::add);

        return rentals;
    }

    public Optional<Rental> getRentalById(long id){
        return rentalRepository.findById(id);
    }

    public Rental createRental(String name, int surface, double price, MultipartFile img, String description) {
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            Long userId = userDetails.getId();
            User owner = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
            String imgUrl = extractUrl(img);
            return rentalRepository.save(new Rental(name,
                    surface,
                    price,
                    imgUrl,
                    description,
                    owner));
        } catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    private String extractUrl(MultipartFile img) {
        try {
            Files.copy(img.getInputStream(), this.root.resolve(img.getOriginalFilename()));
            return "/uploads/" + img.getOriginalFilename();
        } catch (Exception e) {
            if (e instanceof FileAlreadyExistsException) {
                throw new RuntimeException("A file of that name already exists.");
            }

            throw new RuntimeException(e.getMessage());
        }

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
