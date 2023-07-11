package com.project.schoolmanagement.springboot.entity.concretes;


import com.project.schoolmanagement.springboot.entity.abstracts.User;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class GuestUser extends User {
}
