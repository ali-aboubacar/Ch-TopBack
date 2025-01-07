package com.chaTop.Backend.service;

import com.chaTop.Backend.dtos.RentalDto;
import com.chaTop.Backend.mapper.RentalMapper;
import com.chaTop.Backend.model.Rental;
import com.chaTop.Backend.model.User;
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
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Class de service pour la gestion des rentals.
 */
@Service
public class RentalService {
    private static final Logger logger = LoggerFactory.getLogger(RentalService.class);

    private final Path root = Paths.get("uploads");
    @Autowired
    RentalRepository rentalRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RentalMapper rentalMapper;
    /**
     * Recuperation de tous les rentals.
     * @return
     */
    public List<RentalDto> getAllRentals(){
        List<Rental> rentals = new ArrayList<>();
        rentalRepository.findAll().forEach(rentals::add);
        List<RentalDto> rentalDtoList = rentals.stream().map(rentalMapper::toRentalDto).collect(Collectors.toList());

        return rentalDtoList;
    }

    /**
     * Recuperation d'un rental.
     * @param id l'id du rental a recuperer
     * @return un rental.
     */
    public Optional<RentalDto> getRentalById(long id){
        Optional<Rental> foundRental = rentalRepository.findById(id);
        return foundRental.map(rentalMapper::toRentalDto);
    }

    /**
     * Creation du rental dans le systeme
     * @param name nom saisi par l'utilisateur
     * @param surface surface saisie par l'utilisateur
     * @param price prix saisi par l'utilisateur
     * @param img image choisie par l'utilisateur
     * @param description description saisie par l'utilisateur
     * @return un rental
     */
    public RentalDto createRental(String name, int surface, double price, MultipartFile img, String description) {
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            Long userId = userDetails.getId();
            User owner = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
            List<String> imgUrl = new ArrayList<>();
            String extractedUrl = extractUrl(img);
            imgUrl.add(extractedUrl);
            Rental savedRental = rentalRepository.save(new Rental(name,
                    surface,
                    price,
                    imgUrl,
                    description,
                    owner));
            return rentalMapper.toRentalDto(savedRental);
        } catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * extraction de l'url de l'image.
     * @param img l'image fournie par l'utlisateur.
     * @return l'url de la location de l'image.
     */
    private String extractUrl(MultipartFile img) {
        try {
            String sanitizedFileName = img.getOriginalFilename().replaceAll("[^a-zA-Z0-9\\.\\-_]", "_");
            Files.copy(img.getInputStream(), this.root.resolve(sanitizedFileName));
            return "http://localhost:3001/uploads/" + sanitizedFileName;
        } catch (Exception e) {
            if (e instanceof FileAlreadyExistsException) {
                throw new RuntimeException("A file of that name already exists.");
            }

            throw new RuntimeException(e.getMessage());
        }

    }

    /**
     * Modifier un rental.
     * @param initialRental le rental existant dans le system.
     * @param nom le nouveau rental à modifier.
     * @return un rental
     */
    public RentalDto updateRental(RentalDto initialRental, String name, Integer surface, double price, String description ){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Long userId = userDetails.getId();
        if (userId == initialRental.getOwner_id()){
            if (name != null) initialRental.setName(name);
            if (description != null) initialRental.setDescription(description);
            if (price > 0) initialRental.setPrice(price);
            if (surface > 0) initialRental.setSurface(surface);
            initialRental.setUpdated_at(LocalDate.now().toString());
            Rental updatedRental = rentalMapper.toRental(initialRental);
            Rental savedRental = rentalRepository.save(updatedRental);
            return rentalMapper.toRentalDto(savedRental);
        } else {
            throw new RuntimeException("cette utilisateur n'est pas le proprietaire.");
        }
    }

    /**
     * Supprime le rental qui correspond a l'id fournie.
     * @param id l'id du rental
     * @return HttpStatus
     */
    public void deleteRental(long id){
        rentalRepository.deleteById(id);
    }
}
