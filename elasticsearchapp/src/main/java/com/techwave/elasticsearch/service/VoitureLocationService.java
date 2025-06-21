/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.techwave.elasticsearch.service;

import com.techwave.elasticsearch.repository.VoitureLocationRepository;
import com.techwave.elasticsearch.model.VoitureLocation;
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
public class VoitureLocationService {

    private static final String VOITURES_LOCATION_JSON = "voitures_location.json";

    @Autowired
    private VoitureLocationRepository voitureLocationRepository;

    private ObjectMapper objectMapper = new ObjectMapper();

    @PostConstruct
    public void loadVoituresLocation() throws IOException {
        File file = new File(VOITURES_LOCATION_JSON);
        if (file.exists()) {
            List<VoitureLocation> voitures = objectMapper.readValue(file, new TypeReference<List<VoitureLocation>>() {});
            voitureLocationRepository.saveAll(voitures);
        }
    }

    public List<VoitureLocation> getAllVoituresLocation() {
        Iterable<VoitureLocation> iterable = voitureLocationRepository.findAll();
        return StreamSupport.stream(iterable.spliterator(), false)
                .collect(Collectors.toList());
    }

    public Optional<VoitureLocation> getVoitureLocationById(String id) {
        return voitureLocationRepository.findById(id);
    }

    public VoitureLocation addVoitureLocation(VoitureLocation voiture) throws IOException {
        VoitureLocation saved = voitureLocationRepository.save(voiture);
        saveVoituresLocationToJson();
        return saved;
    }

    public VoitureLocation updateVoitureLocation(String id, VoitureLocation voiture) throws IOException {
        voiture.setId(id);
        VoitureLocation updated = voitureLocationRepository.save(voiture);
        saveVoituresLocationToJson();
        return updated;
    }

    public void deleteVoitureLocation(String id) throws IOException {
        voitureLocationRepository.deleteById(id);
        saveVoituresLocationToJson();
    }

    public List<VoitureLocation> searchVoituresLocation(String query) {
        List<VoitureLocation> byMarque = voitureLocationRepository.findByMarqueContainingIgnoreCase(query);
        List<VoitureLocation> byModele = voitureLocationRepository.findByModeleContainingIgnoreCase(query);
        byMarque.addAll(byModele);
        return byMarque;
    }

    private void saveVoituresLocationToJson() throws IOException {
        List<VoitureLocation> allVoitures = getAllVoituresLocation();
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(VOITURES_LOCATION_JSON), allVoitures);
    }
}