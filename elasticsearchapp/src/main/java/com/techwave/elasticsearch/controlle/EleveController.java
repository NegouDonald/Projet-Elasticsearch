/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.techwave.elasticsearch.controlle;

import com.techwave.elasticsearch.model.Eleve;
import com.techwave.elasticsearch.service.EleveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/eleves")
public class EleveController {

    @Autowired
    private EleveService eleveService;

    /*@GetMapping
    public List<Eleve> getAllEleves() {
        return eleveService.getAllEleves();
    }*/

    @GetMapping("/{id}")
    public ResponseEntity<Eleve> getEleveById(@PathVariable String id) {
        Optional<Eleve> eleve = eleveService.getEleveById(id);
        return eleve.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Eleve> addEleve(@RequestBody Eleve eleve) throws IOException {
        Eleve saved = eleveService.addEleve(eleve);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Eleve> updateEleve(@PathVariable String id, @RequestBody Eleve eleve) throws IOException {
        Eleve updated = eleveService.updateEleve(id, eleve);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEleve(@PathVariable String id) throws IOException {
        eleveService.deleteEleve(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public List<Eleve> searchEleves(@RequestParam String q) {
        return eleveService.searchEleves(q);
    }
}