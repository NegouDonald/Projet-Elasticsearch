/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.techwave.elasticsearch.service;

import com.techwave.elasticsearch.model.Conducteur;
import com.techwave.elasticsearch.repository.ConducteurRepository;
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
public class ConducteurService {

    private static final String CONDUCTEURS_JSON = "conducteurs.json";

    @Autowired
    private ConducteurRepository conducteurRepository;

    private ObjectMapper objectMapper = new ObjectMapper();

    @PostConstruct
    public void loadConducteurs() throws IOException {
        File file = new File(CONDUCTEURS_JSON);
        if (file.exists()) {
            List<Conducteur> conducteurs = objectMapper.readValue(file, new TypeReference<List<Conducteur>>() {});
            conducteurRepository.saveAll(conducteurs);
        }
    }

    public List<Conducteur> getAllConducteurs() {
        Iterable<Conducteur> iterable = conducteurRepository.findAll();
        return StreamSupport.stream(iterable.spliterator(), false)
                .collect(Collectors.toList());
    }

    public Optional<Conducteur> getConducteurById(String id) {
        return conducteurRepository.findById(id);
    }

    public Conducteur addConducteur(Conducteur conducteur) throws IOException {
        Conducteur saved = conducteurRepository.save(conducteur);
        saveConducteursToJson();
        return saved;
    }

    public Conducteur updateConducteur(String id, Conducteur conducteur) throws IOException {
        conducteur.setId(id);
        Conducteur updated = conducteurRepository.save(conducteur);
        saveConducteursToJson();
        return updated;
    }

    public void deleteConducteur(String id) throws IOException {
        conducteurRepository.deleteById(id);
        saveConducteursToJson();
    }

    public List<Conducteur> searchConducteurs(String query) {
        List<Conducteur> byNom = conducteurRepository.findByNomContainingIgnoreCase(query);
        List<Conducteur> byPrenom = conducteurRepository.findByPrenomContainingIgnoreCase(query);
        byNom.addAll(byPrenom);
        return byNom;
    }

    private void saveConducteursToJson() throws IOException {
        List<Conducteur> allConducteurs = getAllConducteurs();
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(CONDUCTEURS_JSON), allConducteurs);
    }
}
