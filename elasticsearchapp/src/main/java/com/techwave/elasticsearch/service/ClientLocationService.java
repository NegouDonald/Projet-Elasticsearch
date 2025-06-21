/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.techwave.elasticsearch.service;

import com.techwave.elasticsearch.model.ClientLocation;
import com.techwave.elasticsearch.repository.ClientLocationRepository;
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
public class ClientLocationService {

    private static final String CLIENTS_LOCATION_JSON = "clients_location.json";

    @Autowired
    private ClientLocationRepository clientLocationRepository;

    private ObjectMapper objectMapper = new ObjectMapper();

    @PostConstruct
    public void loadClientsLocation() throws IOException {
        File file = new File(CLIENTS_LOCATION_JSON);
        if (file.exists()) {
            List<ClientLocation> clients = objectMapper.readValue(file, new TypeReference<List<ClientLocation>>() {});
            clientLocationRepository.saveAll(clients);
        }
    }

    public List<ClientLocation> getAllClientsLocation() {
        Iterable<ClientLocation> iterable = clientLocationRepository.findAll();
        return StreamSupport.stream(iterable.spliterator(), false)
                .collect(Collectors.toList());
    }

    public Optional<ClientLocation> getClientLocationById(String id) {
        return clientLocationRepository.findById(id);
    }

    public ClientLocation addClientLocation(ClientLocation client) throws IOException {
        ClientLocation saved = clientLocationRepository.save(client);
        saveClientsLocationToJson();
        return saved;
    }

    public ClientLocation updateClientLocation(String id, ClientLocation client) throws IOException {
        client.setId(id);
        ClientLocation updated = clientLocationRepository.save(client);
        saveClientsLocationToJson();
        return updated;
    }

    public void deleteClientLocation(String id) throws IOException {
        clientLocationRepository.deleteById(id);
        saveClientsLocationToJson();
    }

    public List<ClientLocation> searchClientsLocation(String query) {
        List<ClientLocation> byNom = clientLocationRepository.findByNomContainingIgnoreCase(query);
        List<ClientLocation> byPrenom = clientLocationRepository.findByPrenomContainingIgnoreCase(query);
        byNom.addAll(byPrenom);
        return byNom;
    }

    private void saveClientsLocationToJson() throws IOException {
        List<ClientLocation> allClients = getAllClientsLocation();
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(CLIENTS_LOCATION_JSON), allClients);
    }
}