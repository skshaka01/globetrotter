package com.headout.globetrotter.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DestinationQuestionDto {
    private Long id;           // destination ID (used for answer validation)
    private List<String> clues; // Clues to display to the user
    private List<String> options; // Multiple-choice options (includes the correct answer, but not flagged)
}
