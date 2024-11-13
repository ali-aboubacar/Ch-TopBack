package com.chaTop.Backend.repository;

import com.chaTop.Backend.model.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface RentalRepository extends JpaRepository<Rental, Long> {
}
