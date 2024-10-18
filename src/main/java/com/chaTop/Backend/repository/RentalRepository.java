package com.chaTop.Backend.repository;

import com.chaTop.Backend.model.Rental;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentalRepository extends JpaRepository<Rental, Long> {
}
