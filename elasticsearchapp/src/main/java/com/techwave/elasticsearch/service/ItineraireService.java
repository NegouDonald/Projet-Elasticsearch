/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.techwave.elasticsearch.service;

import com.techwave.elasticsearch.model.Itineraire;
import com.techwave.elasticsearch.repository.ItineraireRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItineraireService {

    private static final String ITINERAIRES_JSON = "itineraires.json";

    @Autowired
    private ItineraireRepository itineraireRepository;

    private ObjectMapper objectMapper = new ObjectMapper();

    @PostConstruct
    public void loadItineraires() throws IOException {
        File file = new File(ITINERAIRES_JSON);
        if (file.exists()) {
            List<Itineraire> itineraires = objectMapper.readValue(file, new TypeReference<List<Itineraire>>() {});
            itineraireRepository.saveAll(itineraires);
        }
    }

    public List<Itineraire> getAllItineraires() {
        Iterable<Itineraire> iterable = itineraireRepository.findAll();
        return StreamSupport.stream(iterable.spliterator(), false)
                .collect(Collectors.toList());
    }

    public Optional<Itineraire> getItineraireById(String id) {
        return itineraireRepository.findById(id);
    }

    public Itineraire addItineraire(Itineraire itineraire) throws IOException {
        Itineraire saved = itineraireRepository.save(itineraire);
        saveItinerairesToJson();
        return saved;
    }

    public Itineraire updateItineraire(String id, Itineraire itineraire) throws IOException {
        itineraire.setId(id);
        Itineraire updated = itineraireRepository.save(itineraire);
        saveItinerairesToJson();
        return updated;
    }

    public void deleteItineraire(String id) throws IOException {
        itineraireRepository.deleteById(id);
        saveItinerairesToJson();
    }

    public List<Itineraire> searchItineraires(String query) {
        List<Itineraire> byDepart = itineraireRepository.findByDepartContainingIgnoreCase(query);
        List<Itineraire> byArrivee = itineraireRepository.findByArriveeContainingIgnoreCase(query);
        byDepart.addAll(byArrivee);
        return byDepart;
    }

    private void saveItinerairesToJson() throws IOException {
        List<Itineraire> allItineraires = getAllItineraires();
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(ITINERAIRES_JSON), allItineraires);
    }
}