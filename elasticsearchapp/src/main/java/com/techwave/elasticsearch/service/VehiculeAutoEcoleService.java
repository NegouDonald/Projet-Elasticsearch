/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.techwave.elasticsearch.service;

import com.techwave.elasticsearch.repository.VehiculeAutoEcoleRepository;
import com.techwave.elasticsearch.model.VehiculeAutoEcole;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techwave.elasticsearch.model.VehiculeAutoEcole;
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
public class VehiculeAutoEcoleService {

    private static final String VEHICULES_AUTO_ECOLE_JSON = "vehicules_auto_ecole.json";

    @Autowired
    private VehiculeAutoEcoleRepository vehiculeAutoEcoleRepository;

    private ObjectMapper objectMapper = new ObjectMapper();

    @PostConstruct
    public void loadVehiculesAutoEcole() throws IOException {
        File file = new File(VEHICULES_AUTO_ECOLE_JSON);
        if (file.exists()) {
            List<VehiculeAutoEcole> vehicules = objectMapper.readValue(file, new TypeReference<List<VehiculeAutoEcole>>() {});
            vehiculeAutoEcoleRepository.saveAll(vehicules);
        }
    }

    public List<VehiculeAutoEcole> getAllVehiculesAutoEcole() {
        Iterable<VehiculeAutoEcole> iterable = vehiculeAutoEcoleRepository.findAll();
        return StreamSupport.stream(iterable.spliterator(), false)
                .collect(Collectors.toList());
    }

    public Optional<VehiculeAutoEcole> getVehiculeAutoEcoleById(String id) {
        return vehiculeAutoEcoleRepository.findById(id);
    }

    public VehiculeAutoEcole addVehiculeAutoEcole(VehiculeAutoEcole vehicule) throws IOException {
        VehiculeAutoEcole saved = vehiculeAutoEcoleRepository.save(vehicule);
        saveVehiculesAutoEcoleToJson();
        return saved;
    }

    public VehiculeAutoEcole updateVehiculeAutoEcole(String id, VehiculeAutoEcole vehicule) throws IOException {
        vehicule.setId(id);
        VehiculeAutoEcole updated = vehiculeAutoEcoleRepository.save(vehicule);
        saveVehiculesAutoEcoleToJson();
        return updated;
    }

    public void deleteVehiculeAutoEcole(String id) throws IOException {
        vehiculeAutoEcoleRepository.deleteById(id);
        saveVehiculesAutoEcoleToJson();
    }

    public List<VehiculeAutoEcole> searchVehiculesAutoEcole(String query) {
        List<VehiculeAutoEcole> byMarque = vehiculeAutoEcoleRepository.findByMarqueContainingIgnoreCase(query);
        List<VehiculeAutoEcole> byModele = vehiculeAutoEcoleRepository.findByModeleContainingIgnoreCase(query);
        byMarque.addAll(byModele);
        return byMarque;
    }

    private void saveVehiculesAutoEcoleToJson() throws IOException {
        List<VehiculeAutoEcole> allVehicules = getAllVehiculesAutoEcole();
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(VEHICULES_AUTO_ECOLE_JSON), allVehicules);
    }
}
