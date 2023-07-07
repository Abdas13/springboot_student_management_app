package com.project.schoolmanagement.springboot.repository;

import com.project.schoolmanagement.springboot.entity.concretes.ContactMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface ContactMessageRepository extends JpaRepository<ContactMessage, Long> {

    boolean existsByEmailEqualsAndDateEquals(String email, LocalDate date);
}
