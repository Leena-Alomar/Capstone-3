package com.example.capstoneproject.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
public class TargetAduince {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull(message = "The Min Age Cannot Be Null")
    @Column(columnDefinition = "int not null")
    private Integer minAge;
    @NotNull(message = "The Max Age Cannot Be Null")
    @Max(value = 90,message = "The Max age Cannot Be Greater Than 90")
    @Column(columnDefinition = "int not null")
    private Integer maxAge;
    @NotEmpty(message = "The gender Cannot Be Empty")
    @Pattern(regexp = "^(female|male)$",message = "gender must be female or male")
    @Column(columnDefinition = "varchar(50) not null")
    private String gender;
    @NotEmpty(message = "The Interset Cannot Be Empty")
    @Column(columnDefinition = "varchar(50) not null")
    private String interset;
    @NotEmpty(message = "The Location Cannot Be Empty")
    @Size(min =2,message = "The Location Length Must Be at Least Value of 2")
    @Column(columnDefinition = "varchar(40) not null")
    private String location;
    @NotEmpty(message = "The Income Level Cannot Be Empty")
    @Pattern(regexp = "^(high|medium|low)$",message = "The Income Level Must Be high,medium,low")
    @Column(columnDefinition = "varchar(6) not null")
    private String incomeLevel;

    @OneToOne
    @MapsId
    @JsonIgnore
    private Campagin campaign;
}
