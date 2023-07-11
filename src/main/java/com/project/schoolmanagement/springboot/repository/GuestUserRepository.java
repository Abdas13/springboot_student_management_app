package com.project.schoolmanagement.springboot.repository;

import com.project.schoolmanagement.springboot.entity.concretes.GuestUser;
import com.project.schoolmanagement.springboot.entity.concretes.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestUserRepository extends JpaRepository<GuestUser, Long> {

    boolean existsByUsername(String username);

    boolean existsBySsn(String ssn);

    boolean existsByPhoneNumber(String phoneNumber);

}
