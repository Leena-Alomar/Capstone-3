package com.example.capstoneproject.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Entity
public class GeneratedContent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(columnDefinition = "varchar(50) ")
    private String title;
    @Column(columnDefinition="varchar(200) ")
    private String content;
    @Pattern(regexp="^(Post|Caption|Script)$",message = "Type must be one of these types: Post, Caption or Script")
    private String type;

    @ManyToOne
    @JsonIgnore
    private Campaign campaign;
}
