package com.project.schoolmanagement.springboot.entity.concretes;

import com.project.schoolmanagement.springboot.entity.abstracts.User;
import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class Dean extends User {


}
