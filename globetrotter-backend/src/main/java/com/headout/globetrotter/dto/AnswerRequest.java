package com.headout.globetrotter.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NonNull;

@Data
public class AnswerRequest {
    @NotNull(message = "Destination ID is required")
    private Long destinationId;

    @NotBlank(message = "user guess cannot be blank")
    private String userGuess;

    //optional if username is needed for score tracking
    private String username;
}
