/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.techwave.elasticsearch.service;

import com.techwave.elasticsearch.model.Instructeur;
import com.techwave.elasticsearch.repository.InstructeurRepository;
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
public class InstructeurService {

    private static final String INSTRUCTEURS_JSON = "instructeurs.json";

    @Autowired
    private InstructeurRepository instructeurRepository;

    private ObjectMapper objectMapper = new ObjectMapper();

    @PostConstruct
    public void loadInstructeurs() throws IOException {
        File file = new File(INSTRUCTEURS_JSON);
        if (file.exists()) {
            List<Instructeur> instructeurs = objectMapper.readValue(file, new TypeReference<List<Instructeur>>() {});
            instructeurRepository.saveAll(instructeurs);
        }
    }

    public List<Instructeur> getAllInstructeurs() {
        Iterable<Instructeur> iterable = instructeurRepository.findAll();
        return StreamSupport.stream(iterable.spliterator(), false)
                .collect(Collectors.toList());
    }

    public Optional<Instructeur> getInstructeurById(String id) {
        return instructeurRepository.findById(id);
    }

    public Instructeur addInstructeur(Instructeur instructeur) throws IOException {
        Instructeur saved = instructeurRepository.save(instructeur);
        saveInstructeursToJson();
        return saved;
    }

    public Instructeur updateInstructeur(String id, Instructeur instructeur) throws IOException {
        instructeur.setId(id);
        Instructeur updated = instructeurRepository.save(instructeur);
        saveInstructeursToJson();
        return updated;
    }

    public void deleteInstructeur(String id) throws IOException {
        instructeurRepository.deleteById(id);
        saveInstructeursToJson();
    }

    public List<Instructeur> searchInstructeurs(String query) {
        List<Instructeur> byNom = instructeurRepository.findByNomContainingIgnoreCase(query);
        List<Instructeur> byPrenom = instructeurRepository.findByPrenomContainingIgnoreCase(query);
        byNom.addAll(byPrenom);
        return byNom;
    }

    private void saveInstructeursToJson() throws IOException {
        List<Instructeur> allInstructeurs = getAllInstructeurs();
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(INSTRUCTEURS_JSON), allInstructeurs);
    }
}