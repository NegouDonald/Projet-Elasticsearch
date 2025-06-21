/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.techwave.elasticsearch.controlle;

import com.techwave.elasticsearch.model.Itineraire;
import com.techwave.elasticsearch.service.ItineraireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/itineraires")
public class ItineraireController {

    @Autowired
    private ItineraireService itineraireService;

   /* @GetMapping
    public List<Itineraire> getAllItineraires() {
        return itineraireService.getAllItineraires();
    }*/

    @GetMapping("/{id}")
    public ResponseEntity<Itineraire> getItineraireById(@PathVariable String id) {
        Optional<Itineraire> itineraire = itineraireService.getItineraireById(id);
        return itineraire.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }}