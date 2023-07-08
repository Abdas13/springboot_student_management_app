package com.project.schoolmanagement.springboot.entity.concretes;


import com.project.schoolmanagement.springboot.entity.abstracts.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@ToString(callSuper = true)
public class Teacher extends User {


    // TODO learn about cascade types and orphanRemoval
    @OneToOne(mappedBy = "teacher", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private AdvisoryTeacher advisoryTeacher;

    @Column(name = "isAdvisor")
    private boolean isAdvisor;

    @Column(unique = true)
    private String email;
}
