package com.project.schoolmanagement.springboot.repository;

import com.project.schoolmanagement.springboot.entity.concretes.ViceDean;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ViceDeanRepository extends JpaRepository {

    boolean existsByUsername(String username);

    boolean existsBySsn(String ssn);

    boolean existsByPhoneNumber(String phoneNumber);

    ViceDean findByUsernameEquals(String username);





}
