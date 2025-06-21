/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.techwave.elasticsearch.controlle;

import com.techwave.elasticsearch.model.Horaire;
import com.techwave.elasticsearch.service.HoraireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/horaires")
public class HoraireController {

    @Autowired
    private HoraireService horaireService;

    /*@GetMapping
    public List<Horaire> getAllHoraires() {
        return horaireService.getAllHoraires();
    }*/

    @GetMapping("/{id}")
    public ResponseEntity<Horaire> getHoraireById(@PathVariable String id) {
        Optional<Horaire> horaire = horaireService.getHoraireById(id);
        return horaire.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Horaire> addHoraire(@RequestBody Horaire horaire) throws IOException {
        Horaire saved = horaireService.addHoraire(horaire);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Horaire> updateHoraire(@PathVariable String id, @RequestBody Horaire horaire) throws IOException {
        Horaire updated = horaireService.updateHoraire(id, horaire);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHoraire(@PathVariable String id) throws IOException {
        horaireService.deleteHoraire(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public List<Horaire> searchHoraires(@RequestParam String q) {
        return horaireService.searchHoraires(q);
    }
}