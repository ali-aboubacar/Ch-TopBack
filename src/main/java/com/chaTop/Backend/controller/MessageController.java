package com.chaTop.Backend.controller;

import com.chaTop.Backend.model.Message;
import com.chaTop.Backend.model.Rental;
import com.chaTop.Backend.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class MessageController {
    @Autowired
    MessageService messageService;

    @GetMapping("/messages")
    public  ResponseEntity<List<Message>> getAllMessagesOfClient(){
        try {
            List<Message> messagesOfUser = new ArrayList<Message>();
            messagesOfUser = messageService.getAllMessagesOfUser();

            return new ResponseEntity<>(messagesOfUser, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/messages")
    public ResponseEntity<Message> createMessage(@RequestBody Message message){
        try {
            return new ResponseEntity<>(messageService.createMessage(message), HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
