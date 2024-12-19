package com.chaTop.Backend.service;

import com.chaTop.Backend.model.Message;
import com.chaTop.Backend.model.Rental;
import com.chaTop.Backend.model.User;
import com.chaTop.Backend.payload.request.MessageRequest;
import com.chaTop.Backend.repository.MessageRepository;
import com.chaTop.Backend.repository.RentalRepository;
import com.chaTop.Backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Class de service pour la gestion des messages.
 */
@Service
public class MessageService {
    @Autowired
    MessageRepository messageRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RentalRepository rentalRepository;

    /**
     * Recuperation de tous les messages de l'utilisateur.
     * @return la liste de messages.
     */
    public List<Message> getAllMessagesOfUser() {
        List<Message> messages = new ArrayList<Message>();
        messageRepository.findAll().forEach(messages::add);
        return messages;
    }

    /**
     * Creation d'un message
     * @param message
     * @return un message
     */
    public Message createMessage(MessageRequest message ){
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            Long userId = userDetails.getId();
            Rental rental = rentalRepository.findById(message.getRental_id()).orElseThrow(() -> new RuntimeException("Rental not found"));
            User owner = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
            return messageRepository.save(new Message(
                    message.getMessage(),
                    owner,
                    rental
            ));
        } catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }
}
