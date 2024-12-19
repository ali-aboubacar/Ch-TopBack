package com.chaTop.Backend.controller;

import com.chaTop.Backend.model.Message;
import com.chaTop.Backend.model.Rental;
import com.chaTop.Backend.payload.request.MessageRequest;
import com.chaTop.Backend.service.MessageService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller pour gerer les messages via des endpoints REST.
 */
@RestController
@Tag(name = "Message", description = "Message management APIs")
@RequestMapping("/api")
public class MessageController {
    @Autowired
    MessageService messageService;

    /**
     * Recuperation de tous les messages de l'utilisateur.
     * @return la liste de messages.
     */
    @GetMapping("/messages")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public  ResponseEntity<List<Message>> getAllMessagesOfClient(){
        try {
            List<Message> messagesOfUser = new ArrayList<Message>();
            messagesOfUser = messageService.getAllMessagesOfUser();

            return new ResponseEntity<>(messagesOfUser, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Creation d'un message
     * @param message
     * @return un message
     */
    @PostMapping("/messages")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Message> createMessage(@RequestBody MessageRequest message){
        try {
            return new ResponseEntity<>(messageService.createMessage(message), HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
