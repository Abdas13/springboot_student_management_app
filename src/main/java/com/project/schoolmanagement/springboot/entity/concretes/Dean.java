package com.project.schoolmanagement.springboot.entity.concretes;

import com.project.schoolmanagement.springboot.entity.abstracts.User;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
//@NoArgsConstructor
//@AllArgsConstructor
@SuperBuilder
public class Dean extends User {


}
