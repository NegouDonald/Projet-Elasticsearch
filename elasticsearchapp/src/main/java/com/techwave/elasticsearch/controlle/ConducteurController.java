/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.techwave.elasticsearch.controlle;

import com.techwave.elasticsearch.model.Conducteur;
import com.techwave.elasticsearch.service.ConducteurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/conducteurs")
public class ConducteurController {

    @Autowired
    private ConducteurService conducteurService;

    /*@GetMapping
    public List<Conducteur> getAllConducteurs() {
        return conducteurService.getAllConducteurs();
    }*/

    @GetMapping("/{id}")
    public ResponseEntity<Conducteur> getConducteurById(@PathVariable String id) {
        Optional<Conducteur> conducteur = conducteurService.getConducteurById(id);
        return conducteur.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Conducteur> addConducteur(@RequestBody Conducteur conducteur) throws IOException {
        Conducteur saved = conducteurService.addConducteur(conducteur);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Conducteur> updateConducteur(@PathVariable String id, @RequestBody Conducteur conducteur) throws IOException {
        Conducteur updated = conducteurService.updateConducteur(id, conducteur);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConducteur(@PathVariable String id) throws IOException {
        conducteurService.deleteConducteur(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public List<Conducteur> searchConducteurs(@RequestParam String q) {
        return conducteurService.searchConducteurs(q);
    }
}