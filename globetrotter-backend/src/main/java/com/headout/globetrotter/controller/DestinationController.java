package com.headout.globetrotter.controller;

import com.headout.globetrotter.dto.AnswerRequest;
import com.headout.globetrotter.dto.DestinationQuestionDto;
import com.headout.globetrotter.model.Destination;
import com.headout.globetrotter.service.DestinationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/destinations")
@CrossOrigin(origins = "*")
public class DestinationController {
    @Autowired
    private DestinationService destinationService;

    //Fetch a random destination to debug the application
    @GetMapping("/random")
    public ResponseEntity<Destination> getRandomDestination() {
        Destination destination = destinationService.getRandomDestination();
        return destination != null ? ResponseEntity.ok(destination) : ResponseEntity.notFound().build();
    }

    //Fetch a random destination for question
    @GetMapping("/randomQuestion")
    public ResponseEntity<DestinationQuestionDto> getRandomQuestion() {
        DestinationQuestionDto question = destinationService.getRandomQuestion();
        return question != null ? ResponseEntity.ok(question) : ResponseEntity.notFound().build();
    }

    //Fetch all destination
    @GetMapping("/all")
    public ResponseEntity<List<Destination>> getAllDestinations() {
        List<Destination> destinations = destinationService.getAllDestinations();
        return ResponseEntity.ok(destinations);
    }
    //Check user's answer (using DTO for validation)
    @PostMapping("/answer")
    public ResponseEntity<Map<String, Object>> checkAnswer(@Valid @RequestBody AnswerRequest request) {
        boolean isCorrect = destinationService.checkAnswer(request.getDestinationId(), request.getUserGuess());
        Map<String, Object> response = new HashMap<>();
        response.put("correct", isCorrect);
        response.put("message", isCorrect ? "🎉 Correct! Well done!" : "😥 Incorrect! try again.");

        // Retrieve the destination to get its fun facts
        Optional<Destination> destinationOpt = destinationService.getDestinationById(request.getDestinationId());
        if (destinationOpt.isPresent()) {
            Destination destination = destinationOpt.get();
            List<String> funFacts = destination.getFunFact();
            if (funFacts != null && !funFacts.isEmpty()) {
                // Pick a random fun fact
                String randomFunFact = funFacts.get(new Random().nextInt(funFacts.size()));
                response.put("funFact", randomFunFact);
            }
        }
        return ResponseEntity.ok(response);
    }

    //Admin endpoint: Add a new destination
    @PostMapping("/add")
    public ResponseEntity<Destination> addDestination(@RequestBody @Valid Destination destination) {
        Destination savedDestination = destinationService.saveDestination(destination);
        return ResponseEntity.ok(savedDestination);
    }
}