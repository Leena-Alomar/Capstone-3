package com.example.capstoneproject.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
public class TargetAudience {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(columnDefinition = "int not null")
    private Integer minAge;
    @Column(columnDefinition = "int not null")
    private Integer maxAge;
    @Column(columnDefinition = "varchar(50) not null")
    private String gender;
    @Column(columnDefinition = "varchar(50) not null")
    private String interest;
    @Column(columnDefinition = "varchar(40) not null")
    private String location;
    @Column(columnDefinition = "varchar(6) not null")
    private String incomeLevel;

    @OneToOne
    @MapsId
    @JsonIgnore
    private Campaign campaign;
}
