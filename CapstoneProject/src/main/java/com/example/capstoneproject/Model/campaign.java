package com.example.capstoneproject.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Entity
@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
public class campaign {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // in my opinion campaigns do not necessarily need a name (already has description and goal)
    @Column(columnDefinition = "varchar(20)")
    private String name;

    @NotEmpty(message="Campaign description can not be empty")
    @Column(columnDefinition="varchar(200) not null")
    private String description;

    @NotEmpty(message="Campaign release platform can not be empty")
    @Column(columnDefinition="varchar(25) not null")
    @Pattern(regexp="^(X|TikTok|Instagram)$",message = "platform must be one of known platforms like: X, TikTok or Instagram")
    private String platform;

    @NotEmpty(message="Campaign goal can not be empty")
    @Column(columnDefinition="varchar(155) not null")
    private String goal;

    @PositiveOrZero
    @Column(columnDefinition="double")
    private double budget=0;

    @Column(columnDefinition="date")
    private LocalDate startDate;

    @Column(columnDefinition="date")
    private LocalDate endDate;

    @Column(columnDefinition="varchar(25)")
    @Pattern(regexp="^(?i)(Draft|Running|Completed)$",message = "status must be one of these status: Draft, Running or Completed")
    private String status;

    @OneToOne(cascade = CascadeType.ALL,mappedBy = "campaign")
    @PrimaryKeyJoinColumn
    private TargetAudience targetAudience;

    @ManyToOne
    @JsonIgnore
    private Project project;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "campaign")
    private Set<GeneratedContent> contents;
}
