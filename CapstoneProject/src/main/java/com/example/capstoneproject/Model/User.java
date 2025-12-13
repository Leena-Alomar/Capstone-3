package com.example.capstoneproject.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty(message = "the name cannot be empty")
    @Size(min = 3,message = "the name length must be at least value of 3")
    @Column(columnDefinition = "varchar(50) not null")
    private String name;
    @NotEmpty(message = "the password cannot be empty")
    @Size(min = 3,message = "the password length must be at least value of 3")
    @Column(columnDefinition = "varchar(25) not null")
    private String password;
    @NotEmpty(message = "the email cannot be empty")
    @Size(min = 6,message = "the email length must be at least value of 6")
    @Column(columnDefinition = "varchar(20) not null unique")
    @Email
    private String email;
    @NotEmpty(message = "the phone number cannot be empty")
    @Size(min = 10,message = "the phone number length must be value of 10")
    @Column(columnDefinition = "varchar(10) not null")
    private String phoneNumber;
    private Boolean subscription;

    @Column(columnDefinition = "int default 0")
    private Integer createdCounter = 0;

    @OneToMany(cascade = CascadeType.ALL ,mappedBy = "user", orphanRemoval = true)
    private Set<Project> projects;
}
