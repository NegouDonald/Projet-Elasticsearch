/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.techwave.elasticsearch.controlle;

import com.techwave.elasticsearch.model.VoitureLocation;
import com.techwave.elasticsearch.service.VoitureLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/voitures-location")
public class VoitureLocationController {

    @Autowired
    private VoitureLocationService voitureLocationService;

    /*@GetMapping
    public List<VoitureLocation> getAllVoituresLocation() {
        return voitureLocationService.getAllVoituresLocation();
    }*/

    @GetMapping("/{id}")
    public ResponseEntity<VoitureLocation> getVoitureLocationById(@PathVariable String id) {
        Optional<VoitureLocation> voiture = voitureLocationService.getVoitureLocationById(id);
        return voiture.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<VoitureLocation> addVoitureLocation(@RequestBody VoitureLocation voiture) throws IOException {
        VoitureLocation saved = voitureLocationService.addVoitureLocation(voiture);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VoitureLocation> updateVoitureLocation(@PathVariable String id, @RequestBody VoitureLocation voiture) throws IOException {
        VoitureLocation updated = voitureLocationService.updateVoitureLocation(id, voiture);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVoitureLocation(@PathVariable String id) throws IOException {
        voitureLocationService.deleteVoitureLocation(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public List<VoitureLocation> searchVoituresLocation(@RequestParam String q) {
        return voitureLocationService.searchVoituresLocation(q);
    }
}