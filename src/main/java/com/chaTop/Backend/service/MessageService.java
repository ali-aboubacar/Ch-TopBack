package com.chaTop.Backend.service;

import com.chaTop.Backend.model.Message;
import com.chaTop.Backend.model.Rental;
import com.chaTop.Backend.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class MessageService {
    @Autowired
    MessageRepository messageRepository;

    public List<Message> getAllMessagesOfUser() {
        List<Message> messages = new ArrayList<Message>();
        messageRepository.findAll().forEach(messages::add);
        return messages;
    }

    public Message createMessage(Message message ){
        return messageRepository.save(new Message(
                message.getMessage()
        ));
    }
}
