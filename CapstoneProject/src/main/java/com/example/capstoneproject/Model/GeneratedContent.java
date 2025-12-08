package com.example.capstoneproject.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
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
    @NotEmpty(message="Generated content must have a title")
    @Column(columnDefinition = "varchar(50) not null")
    private String title;
    @NotEmpty(message="Generated content can not be empty")
    @Column(columnDefinition="varchar(200) not null")
    private String content;
    @Pattern(regexp="^(Post|Caption|Script)$",message = "Type must be one of these types: Post, Caption or Script")
    private String type;
    @ManyToOne
    @JsonIgnore
    private Campagin campaign;
}
