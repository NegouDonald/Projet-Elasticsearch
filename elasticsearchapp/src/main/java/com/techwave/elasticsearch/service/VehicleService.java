package com.techwave.elasticsearch.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techwave.elasticsearch.model.Vehicle;
import com.techwave.elasticsearch.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class VehicleService {

    private static final String VEHICLES_JSON = "vehicles.json";

    @Autowired
    private VehicleRepository vehicleRepository;

    private ObjectMapper objectMapper = new ObjectMapper();

    // Charger les données JSON au démarrage et indexer dans Elasticsearch
    @PostConstruct
    public void loadVehicles() throws IOException {
        File file = new File(VEHICLES_JSON);
        if (file.exists()) {
            List<Vehicle> vehicles = objectMapper.readValue(file, new TypeReference<List<Vehicle>>() {});
            vehicleRepository.saveAll(vehicles);
        }
    }


        public List<Vehicle> getAllVehicles() {
            Iterable<Vehicle> iterable = vehicleRepository.findAll();
            System.out.println(vehicleRepository.findAll());

            return (List<Vehicle>) StreamSupport.stream(iterable.spliterator(), false)
                    .collect(Collectors.toList());
        }



    public Optional<Vehicle> getVehicleById(String id) {
        return vehicleRepository.findById(id);
    }

    public Vehicle addVehicle(Vehicle vehicle) throws IOException {
        Vehicle saved = vehicleRepository.save(vehicle);
        saveVehiclesToJson();  // Mettre à jour JSON
        return saved;
    }

    public Vehicle updateVehicle(String id, Vehicle vehicle) throws IOException {
        vehicle.setId(id);
        Vehicle updated = vehicleRepository.save(vehicle);
        saveVehiclesToJson();
        return updated;
    }

    public void deleteVehicle(String id) throws IOException {
        vehicleRepository.deleteById(id);
        saveVehiclesToJson();
    }

    public List<Vehicle> searchVehicles(String query) {
        // Recherche simple sur marque et modèle
        List<Vehicle> byMarque = vehicleRepository.findByMarqueContainingIgnoreCase(query);
        List<Vehicle> byModele = vehicleRepository.findByModeleContainingIgnoreCase(query);
        byMarque.addAll(byModele);
        return byMarque;
    }

    // Sauvegarde le contenu de Elasticsearch dans le fichier JSON
    private void saveVehiclesToJson() throws IOException {
        List<Vehicle> allVehicles = getAllVehicles();
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(VEHICLES_JSON), allVehicles);
    }
}
