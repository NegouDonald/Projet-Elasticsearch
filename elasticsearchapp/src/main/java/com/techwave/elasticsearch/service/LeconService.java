/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.techwave.elasticsearch.service;

import com.techwave.elasticsearch.repository.LeconRepository;
import com.techwave.elasticsearch.model.Lecon;
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
public class LeconService {

    private static final String LECONS_JSON = "lecons.json";

    @Autowired
    private LeconRepository leconRepository;

    private ObjectMapper objectMapper = new ObjectMapper();

    @PostConstruct
    public void loadLecons() throws IOException {
        File file = new File(LECONS_JSON);
        if (file.exists()) {
            List<Lecon> lecons = objectMapper.readValue(file, new TypeReference<List<Lecon>>() {});
            leconRepository.saveAll(lecons);
        }
    }

    public List<Lecon> getAllLecons() {
        Iterable<Lecon> iterable = leconRepository.findAll();
        return StreamSupport.stream(iterable.spliterator(), false)
                .collect(Collectors.toList());
    }

    public Optional<Lecon> getLeconById(String id) {
        return leconRepository.findById(id);
    }

    public Lecon addLecon(Lecon lecon) throws IOException {
        Lecon saved = leconRepository.save(lecon);
        saveLeconsToJson();
        return saved;
    }

    public Lecon updateLecon(String id, Lecon lecon) throws IOException {
        lecon.setId(id);
        Lecon updated = leconRepository.save(lecon);
        saveLeconsToJson();
        return updated;
    }

    public void deleteLecon(String id) throws IOException {
        leconRepository.deleteById(id);
        saveLeconsToJson();
    }

    public List<Lecon> searchLecons(String query) {
        List<Lecon> byInstructeurId = leconRepository.findByInstructeurIdContainingIgnoreCase(query);
        List<Lecon> byEleveId = leconRepository.findByEleveIdContainingIgnoreCase(query);
        byInstructeurId.addAll(byEleveId);
        return byInstructeurId;
    }

    private void saveLeconsToJson() throws IOException {
        List<Lecon> allLecons = getAllLecons();
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(LECONS_JSON), allLecons);
    }
}