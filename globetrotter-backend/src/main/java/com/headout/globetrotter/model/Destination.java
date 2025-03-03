package com.headout.globetrotter.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "destinations")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Destination {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "City is required")
    private String city;

    @NotBlank(message = "Country is required")
    private String country;

    @ElementCollection
    @CollectionTable(name = "destination_clues", joinColumns = @JoinColumn(name = "destination_id"))
    @Column(name = "clue")
    private List<String> clues;

    @JsonProperty("fun_fact")
    @ElementCollection
    @CollectionTable(name = "destination_fun_fact", joinColumns = @JoinColumn(name = "destination_id"))
    @Column(name = "fun_fact")
    private List<String> funFact;

    @ElementCollection
    @CollectionTable(name = "destination_trivia", joinColumns = @JoinColumn(name = "destination_id"))
    @Column(name = "trivia")
    private List<String> trivia;
}
