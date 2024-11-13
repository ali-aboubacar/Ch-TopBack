package com.chaTop.Backend.repository;

import com.chaTop.Backend.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
