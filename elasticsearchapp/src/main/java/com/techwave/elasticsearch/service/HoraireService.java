/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.techwave.elasticsearch.service;

import com.techwave.elasticsearch.model.Horaire;
import com.techwave.elasticsearch.repository.HoraireRepository;
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
public class HoraireService {

    private static final String HORAIRES_JSON = "horaires.json";

    @Autowired
    private HoraireRepository horaireRepository;

    private ObjectMapper objectMapper = new ObjectMapper();

    @PostConstruct
    public void loadHoraires() throws IOException {
        File file = new File(HORAIRES_JSON);
        if (file.exists()) {
            List<Horaire> horaires = objectMapper.readValue(file, new TypeReference<List<Horaire>>() {});
            horaireRepository.saveAll(horaires);
        }
    }

    public List<Horaire> getAllHoraires() {
        Iterable<Horaire> iterable = horaireRepository.findAll();
        return StreamSupport.stream(iterable.spliterator(), false)
                .collect(Collectors.toList());
    }

    public Optional<Horaire> getHoraireById(String id) {
        return horaireRepository.findById(id);
    }

    public Horaire addHoraire(Horaire horaire) throws IOException {
        Horaire saved = horaireRepository.save(horaire);
        saveHorairesToJson();
        return saved;
    }

    public Horaire updateHoraire(String id, Horaire horaire) throws IOException {
        horaire.setId(id);
        Horaire updated = horaireRepository.save(horaire);
        saveHorairesToJson();
        return updated;
    }

    public void deleteHoraire(String id) throws IOException {
        horaireRepository.deleteById(id);
        saveHorairesToJson();
    }

    public List<Horaire> searchHoraires(String query) {
        List<Horaire> byItineraireId = horaireRepository.findByItineraireIdContainingIgnoreCase(query);
        List<Horaire> byConducteurId = horaireRepository.findByConducteurIdContainingIgnoreCase(query);
        byItineraireId.addAll(byConducteurId);
        return byItineraireId;
    }

    private void saveHorairesToJson() throws IOException {
        List<Horaire> allHoraires = getAllHoraires();
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(HORAIRES_JSON), allHoraires);
    }
}