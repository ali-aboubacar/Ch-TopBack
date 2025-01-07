package com.chaTop.Backend.controller;

import com.chaTop.Backend.dtos.RentalDto;
import com.chaTop.Backend.mapper.RentalMapper;
import com.chaTop.Backend.model.Rental;
import com.chaTop.Backend.payload.response.MessageResponse;
import com.chaTop.Backend.payload.response.RentalsResponse;
import com.chaTop.Backend.service.RentalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Controller pour gerer les rentals via des endpoints REST.
 */
@Tag(name = "Rental", description = "Rental management APIs")
@RestController
@RequestMapping("/api")
public class RentalController {
    @Autowired
    RentalService rentalService;

    /**
     * Recuperer tout les rentals disponible dans le systeme.
     * @return Un RentalResponse
     */
    @Operation(
            summary = "Recuperation de tous les rentals",
            description = "Get a list of rental.",
            tags = { "rentals", "get" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Rental.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @GetMapping("/rentals")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<RentalsResponse> getAllRentals(){
        try {
            List<RentalDto> rentalsList = new ArrayList<>();
            rentalsList = rentalService.getAllRentals();
            RentalsResponse rentals = new RentalsResponse();
            rentals.setRentals(rentalsList);
            return new ResponseEntity<>(rentals, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Recupere le rental qui correspond a l'id fournie.
     * @param id l'id du rental
     * @return Un rental.
     */
    @Operation(
            summary = "Recuperation d'un rental par Id",
            description = "Get a Rental object by specifying its id. The response is Rental object.",
            tags = { "rentals", "get" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Rental.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @GetMapping("/rentals/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<RentalDto> getRentalById(@PathVariable("id") long id){
        try {
            Optional<RentalDto> rental = rentalService.getRentalById(id);
            return rental.map(value -> new ResponseEntity(value, HttpStatus.OK))
                         .orElseGet(() -> new ResponseEntity(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Creation du rental dans le systeme
     * @param name nom saisi par l'utilisateur
     * @param surface surface saisie par l'utilisateur
     * @param price prix saisi par l'utilisateur
     * @param picture image choisie par l'utilisateur
     * @param description description saisie par l'utilisateur
     * @return un rental
     */
    @Operation(
            summary = "Creation d'un rental",
            description = ".",
            tags = { "rentals", "post" })
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = { @Content(schema = @Schema(implementation = Rental.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @PostMapping("/rentals")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<MessageResponse> createRental( @RequestParam("name") String name,
                                                @RequestParam("surface") int surface,
                                                @RequestParam("price") double price,
                                                @RequestParam("picture") MultipartFile picture,
                                                @RequestParam("description") String description ){
        try {
            rentalService.createRental(name, surface, price, picture, description);
            return new ResponseEntity<MessageResponse>(new MessageResponse("Rental created !"), HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * Modifier un rental.
     * @param id l'identifiant du rental a modifier
     * @param
     * @return un rental
     */
    @Operation(
            summary = "Retrieve a Rental by Id",
            description = "Get a Rental object by specifying its id. The response is Rental object.",
            tags = { "rentals", "put" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Rental.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @PutMapping("/rentals/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<MessageResponse> updateRental(@PathVariable("id") long id, @RequestParam String name, @RequestParam Integer surface, @RequestParam Integer price , @RequestParam String description){
        try {
            Optional<RentalDto> rentalData = rentalService.getRentalById(id);
            if (rentalData.isPresent()){
                rentalService.updateRental(rentalData.get(), name, surface, price, description);
                return new ResponseEntity<MessageResponse>(new MessageResponse("Rental updated !"), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Supprime le rental qui correspond a l'id fournie.
     * @param id l'id du rental
     * @return HttpStatus
     */
    @Operation(
            summary = "Retrieve a Rental by Id",
            description = "Get a Rental object by specifying its id. The response is Rental object.",
            tags = { "rentals", "delete" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Rental.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
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
