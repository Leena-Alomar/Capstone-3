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
    @Column(columnDefinition="varchar(500) ")
    private String content;
    @Column(columnDefinition = "varchar(8) ")
    @Pattern(regexp="^(Post|Caption|Script)$",message = "Type must be one of these types: Post, Caption or Script")
    private String type;

    @Pattern(regexp="^(?i)(Draft|Approved|Rejected)$",message = "status must be one of these status: Draft, Approved or rejected")
    private String status="Drift";

    @ManyToOne
    @JsonIgnore
    private Campaign campaign;
}
