package com.project.schoolmanagement.springboot.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ContactMessageRequest implements Serializable {

    @NotNull(message = "Please enter name")
    @Size(min = 4, max = 16, message = "Your name should be at least 4 characters")
    @Pattern(regexp = "\\A(?!\\s*\\Z).+", message = "Your name must consist of the characters.")
    private String name;

    @Email(message = "Please enter a valid email")
    @Size(min = 5, max = 20, message = "Your email should be at least 5 characters.")
    @NotNull(message = "Please enter your email")
    // @Column should be in entity class for DB validation, but it is acceptable here
    @Column(nullable = false, unique = true, length = 20)
    private String email;

    @NotNull(message = "Please enter subject")
    @Size(min = 4, max = 50, message = "Your subject should be at least 4 characters")
    @Pattern(regexp = "\\A(?!\\s*\\Z).+", message = "Your subject must consist of the characters.")
    private String subject;

    @NotNull(message = "Please enter message")
    @Size(min = 16, max = 50, message = "Your message should be at least 16 characters")
    @Pattern(regexp = "\\A(?!\\s*\\Z).+", message = "Your message must consist of the characters.")
    private String message;

}
