package com.example.capstoneproject.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
@Table(name = "logo")
public class Logo {

    @Id
    private Integer id;
    @NotEmpty(message = "the name cannot be empty")
    @Size(min = 3,message = "the name length must be at least value of 3")
    @Column(columnDefinition = "varchar(30) not null")
    private String name;
    @NotEmpty(message = "the data cannot be empty")
    @Size(min = 3,message = "the data length must be at least value of 3")
    @Column(columnDefinition = "varchar(50) not null")
    private String data;
    @NotEmpty(message = "the type cannot be empty")
    @Size(min = 4,message = "the type length must be at least value of 4")
    @Column(columnDefinition = "varchar(15) not null")
    private String type;

    @OneToOne
    @MapsId
    @JsonIgnore
    private Project project;
}
