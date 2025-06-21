/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.techwave.elasticsearch.service;

import com.techwave.elasticsearch.repository.VehiculeTransportRepository;
import com.techwave.elasticsearch.model.VehiculeTransport;
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
public class VehiculeTransportService {

    private static final String VEHICULES_TRANSPORT_JSON = "vehicules_transport.json";

    @Autowired
    private VehiculeTransportRepository vehiculeTransportRepository;

    private ObjectMapper objectMapper = new ObjectMapper();

    @PostConstruct
    public void loadVehiculesTransport() throws IOException {
        File file = new File(VEHICULES_TRANSPORT_JSON);
        if (file.exists()) {
            List<VehiculeTransport> vehicules = objectMapper.readValue(file, new TypeReference<List<VehiculeTransport>>() {});
            vehiculeTransportRepository.saveAll(vehicules);
        }
    }

    public List<VehiculeTransport> getAllVehiculesTransport() {
        Iterable<VehiculeTransport> iterable = vehiculeTransportRepository.findAll();
        return StreamSupport.stream(iterable.spliterator(), false)
                .collect(Collectors.toList());
    }

    public Optional<VehiculeTransport> getVehiculeTransportById(String id) {
        return vehiculeTransportRepository.findById(id);
    }

    public VehiculeTransport addVehiculeTransport(VehiculeTransport vehicule) throws IOException {
        VehiculeTransport saved = vehiculeTransportRepository.save(vehicule);
        saveVehiculesTransportToJson();
        return saved;
    }

    public VehiculeTransport updateVehiculeTransport(String id, VehiculeTransport vehicule) throws IOException {
        vehicule.setId(id);
        VehiculeTransport updated = vehiculeTransportRepository.save(vehicule);
        saveVehiculesTransportToJson();
        return updated;
    }

    public void deleteVehiculeTransport(String id) throws IOException {
        vehiculeTransportRepository.deleteById(id);
        saveVehiculesTransportToJson();
    }

    public List<VehiculeTransport> searchVehiculesTransport(String query) {
        List<VehiculeTransport> byMarque = vehiculeTransportRepository.findByMarqueContainingIgnoreCase(query);
        List<VehiculeTransport> byModele = vehiculeTransportRepository.findByModeleContainingIgnoreCase(query);
        byMarque.addAll(byModele);
        return byMarque;
    }

    private void saveVehiculesTransportToJson() throws IOException {
        List<VehiculeTransport> allVehicules = getAllVehiculesTransport();
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(VEHICULES_TRANSPORT_JSON), allVehicules);
    }
}