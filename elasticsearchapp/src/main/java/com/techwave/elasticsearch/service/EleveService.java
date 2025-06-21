/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.techwave.elasticsearch.service;

import com.techwave.elasticsearch.repository.EleveRepository;
import com.techwave.elasticsearch.model.Eleve;
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
public class EleveService  {

    private static final String ELEVES_JSON = "eleves.json";

    @Autowired
    private EleveRepository eleveRepository;

    private ObjectMapper objectMapper = new ObjectMapper();

    @PostConstruct
    public void loadEleves() throws IOException {
        File file = new File(ELEVES_JSON);
        if (file.exists()) {
            List<Eleve> eleves = objectMapper.readValue(file, new TypeReference<List<Eleve>>() {});
            eleveRepository.saveAll(eleves);
        }
    }

    public List<Eleve> getAllEleves() {
        Iterable<Eleve> iterable = eleveRepository.findAll();
        return StreamSupport.stream(iterable.spliterator(), false)
                .collect(Collectors.toList());
    }

    public Optional<Eleve> getEleveById(String id) {
        return eleveRepository.findById(id);
    }

    public Eleve addEleve(Eleve eleve) throws IOException {
        Eleve saved = eleveRepository.save(eleve);
        saveElevesToJson();
        return saved;
    }

    public Eleve updateEleve(String id, Eleve eleve) throws IOException {
        eleve.setId(id);
        Eleve updated = eleveRepository.save(eleve);
        saveElevesToJson();
        return updated;
    }

    public void deleteEleve(String id) throws IOException {
        eleveRepository.deleteById(id);
        saveElevesToJson();
    }

    public List<Eleve> searchEleves(String query) {
        List<Eleve> byNom = eleveRepository.findByNomContainingIgnoreCase(query);
        List<Eleve> byPrenom = eleveRepository.findByPrenomContainingIgnoreCase(query);
        byNom.addAll(byPrenom);
        return byNom;
    }

    private void saveElevesToJson() throws IOException {
        List<Eleve> allEleves = getAllEleves();
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(ELEVES_JSON), allEleves);
    }
}