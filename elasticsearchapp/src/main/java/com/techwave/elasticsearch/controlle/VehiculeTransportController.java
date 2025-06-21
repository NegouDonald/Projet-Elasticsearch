/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.techwave.elasticsearch.controlle;

import com.techwave.elasticsearch.model.VehiculeTransport;
import com.techwave.elasticsearch.service.VehiculeTransportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/vehicules-transport")
public class VehiculeTransportController {

    @Autowired
    private VehiculeTransportService vehiculeTransportService;

    /*@GetMapping
    public List<VehiculeTransport> getAllVehiculesTransport() {
        return vehiculeTransportService.getAllVehiculesTransport();
    }*/

    @GetMapping("/{id}")
    public ResponseEntity<VehiculeTransport> getVehiculeTransportById(@PathVariable String id) {
        Optional<VehiculeTransport> vehicule = vehiculeTransportService.getVehiculeTransportById(id);
        return vehicule.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<VehiculeTransport> addVehiculeTransport(@RequestBody VehiculeTransport vehicule) throws IOException {
        VehiculeTransport saved = vehiculeTransportService.addVehiculeTransport(vehicule);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VehiculeTransport> updateVehiculeTransport(@PathVariable String id, @RequestBody VehiculeTransport vehicule) throws IOException {
        VehiculeTransport updated = vehiculeTransportService.updateVehiculeTransport(id, vehicule);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehiculeTransport(@PathVariable String id) throws IOException {
        vehiculeTransportService.deleteVehiculeTransport(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public List<VehiculeTransport> searchVehiculesTransport(@RequestParam String q) {
        return vehiculeTransportService.searchVehiculesTransport(q);
    }
}