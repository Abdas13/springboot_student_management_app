package com.project.schoolmanagement.springboot.repository;

import com.project.schoolmanagement.springboot.entity.concretes.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {

    boolean existsByUsername(String username);

    boolean existsBySsn(String ssn);

    boolean existsByPhoneNumber(String phoneNumber);

    Admin findByUsernameEquals(String username);

}
