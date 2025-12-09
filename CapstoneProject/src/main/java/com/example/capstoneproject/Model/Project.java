package com.example.capstoneproject.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
@Entity
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message="Project name can not be empty")
    @Column(columnDefinition = "varchar(50) not null")
    private String name;

    @NotEmpty(message="Campaign description can not be empty")
    @Column(columnDefinition="varchar(200) not null")
    private String description;

    @Column(columnDefinition="varchar(50) not null")
    private String type;

    @ManyToOne
    @JsonIgnore
    private User user;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "project")
    @PrimaryKeyJoinColumn
    private Logo logo;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "project")
    private Set<campaign> campaigns;
}
