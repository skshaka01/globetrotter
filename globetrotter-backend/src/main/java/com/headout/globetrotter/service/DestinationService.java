package com.headout.globetrotter.service;

import com.headout.globetrotter.dto.DestinationQuestionDto;
import com.headout.globetrotter.model.Destination;
import com.headout.globetrotter.repository.DestinationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DestinationService {
    @Autowired
    private DestinationRepository destinationRepository;
    private final Random random = new Random();

    public List<Destination> getAllDestinations() {
        return destinationRepository.findAll();
    }

    public Optional<Destination> getDestinationById(Long id) {
        return destinationRepository.findById(id);
    }

    //Get Random Destination-for Debug use only
    public Destination getRandomDestination() {
        List<Destination> destinations = destinationRepository.findAll();
        return destinations.isEmpty() ? null : destinations.get(random.nextInt(destinations.size()));
    }

    //Get Random Destination for question
    public DestinationQuestionDto getRandomQuestion() {
        List<Destination> destinations = destinationRepository.findAll();
        if (destinations.isEmpty()) return null;

        // Pick a random destination
        Destination randomDestination = destinations.get(random.nextInt(destinations.size()));

        // Generate options dynamically from the database
        List<String> allCities = destinations.stream()
                .map(Destination::getCity)
                .collect(Collectors.toList());

        // Remove the correct answer from the distractors list and then randomly pick distractors
        List<String> distractors = allCities.stream()
                .filter(city -> !city.equalsIgnoreCase(randomDestination.getCity()))
                .collect(Collectors.toList());

        // Randomly select 3 distractors (if available)
        Collections.shuffle(distractors);
        List<String> selectedDistractors = distractors.size() >= 3
                ? distractors.subList(0, 3)
                : distractors;

        // Combine the correct answer with distractors and shuffle the options
        List<String> options = new ArrayList<>();
        options.add(randomDestination.getCity());
        options.addAll(selectedDistractors);
        Collections.shuffle(options);

        // Construct and return the DTO
        return new DestinationQuestionDto(randomDestination.getId(), randomDestination.getClues(), options);
    }

    //Save a destination (Admin)
    public Destination saveDestination(Destination destination) {
        return destinationRepository.save(destination);
    }

    //Check user's answer
    public boolean checkAnswer(Long destinationId, String userGuess) {
        Optional<Destination> destinationOpt = destinationRepository.findById(destinationId);
        return destinationOpt.map(destination -> destination.getCity().equalsIgnoreCase(userGuess)).orElse(false);
    }
}