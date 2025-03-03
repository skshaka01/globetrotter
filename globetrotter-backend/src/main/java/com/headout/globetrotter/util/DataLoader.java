package com.headout.globetrotter.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.headout.globetrotter.model.Destination;
import com.headout.globetrotter.repository.DestinationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.boot.CommandLineRunner;

import java.io.InputStream;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {
    @Autowired
    private DestinationRepository destinationRepository;

    @Override
    public void run(String... args) throws Exception {
        //Load data only id no destination exist
        if (destinationRepository.count() == 0) {
            ObjectMapper mapper = new ObjectMapper();
            TypeReference<List<Destination>> typeReference = new TypeReference<List<Destination>>() {};
            InputStream inputStream = getClass().getResourceAsStream("/data.json");
            try {
                List<Destination> destinations = mapper.readValue(inputStream, typeReference);
                destinationRepository.saveAll(destinations);
                System.out.println("Destination data loaded successfully.");
            } catch (Exception e) {
                System.err.println("Unable to load destination data: " + e.getMessage());
            }
        }
    }
}
