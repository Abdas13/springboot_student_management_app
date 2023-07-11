package com.project.schoolmanagement.springboot.repository;

import com.project.schoolmanagement.springboot.entity.concretes.Dean;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeanRepository extends JpaRepository<Dean, Long> {

    boolean existsByUsername(String username);

    boolean existsBySsn(String ssn);

    boolean existsByPhoneNumber(String phoneNumber);

    Dean findByUsernameEquals(String username);
}
