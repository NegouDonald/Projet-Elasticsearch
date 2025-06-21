/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.techwave.elasticsearch.controlle;

import com.techwave.elasticsearch.model.VehiculeAutoEcole;
import com.techwave.elasticsearch.service.VehiculeAutoEcoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/vehicules-auto-ecole")
public class VehiculeAutoEcoleController {

    @Autowired
    private VehiculeAutoEcoleService vehiculeAutoEcoleService;

   /* @GetMapping
    public List<VehiculeAutoEcole> getAllVehiculesAutoEcole() {
        return vehiculeAutoEcoleService.getAllVehiculesAutoEcole();
    }*/

    @GetMapping("/{id}")
    public ResponseEntity<VehiculeAutoEcole> getVehiculeAutoEcoleById(@PathVariable String id) {
        Optional<VehiculeAutoEcole> vehicule = vehiculeAutoEcoleService.getVehiculeAutoEcoleById(id);
        return vehicule.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<VehiculeAutoEcole> addVehiculeAutoEcole(@RequestBody VehiculeAutoEcole vehicule) throws IOException {
        VehiculeAutoEcole saved = vehiculeAutoEcoleService.addVehiculeAutoEcole(vehicule);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VehiculeAutoEcole> updateVehiculeAutoEcole(@PathVariable String id, @RequestBody VehiculeAutoEcole vehicule) throws IOException {
        VehiculeAutoEcole updated = vehiculeAutoEcoleService.updateVehiculeAutoEcole(id, vehicule);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehiculeAutoEcole(@PathVariable String id) throws IOException {
        vehiculeAutoEcoleService.deleteVehiculeAutoEcole(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public List<VehiculeAutoEcole> searchVehiculesAutoEcole(@RequestParam String q) {
        return vehiculeAutoEcoleService.searchVehiculesAutoEcole(q);
    }
}