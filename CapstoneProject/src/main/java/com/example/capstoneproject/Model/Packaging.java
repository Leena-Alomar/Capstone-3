package com.example.capstoneproject.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
public class Packaging {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty(message = "package description cannot be empty")
    @Column(columnDefinition = "varchar(250) not null")
    private String description;
    @NotEmpty(message = "mock type  cannot be empty")
    @Pattern(regexp = "^(box|bottle|label|pouch|bag)$",message = "the mock type must be box ,bottle, label, pouch or bag")
    @Column(columnDefinition = "varchar(20) not null")
    private String mockupType;
    @NotEmpty(message = " color palette cannot be empty")
    @Column(columnDefinition = "varchar(50) not null")
    private String colorPalette;
    @NotEmpty(message = " dimensions cannot be empty")
    @Column(columnDefinition = "varchar(150) not null")
    private String dimensions;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] data;
    private String type;

    @OneToOne
    @MapsId
    @JsonIgnore
    private Project project;
}
