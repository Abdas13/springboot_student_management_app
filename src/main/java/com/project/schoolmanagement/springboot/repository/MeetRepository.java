package com.project.schoolmanagement.springboot.repository;

import com.project.schoolmanagement.springboot.entity.concretes.Meet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MeetRepository extends JpaRepository<Meet, Long> {

    List<Meet> findByStudentList_IdEquals(Long studentId);

    List<Meet> getByAdvisoryTeacher_idEquals(Long advisoryTeacherId);

    Page<Meet> findByAdvisoryTeacher_idEquals(Long advisoryTeacherId, Pageable pageable);
}
