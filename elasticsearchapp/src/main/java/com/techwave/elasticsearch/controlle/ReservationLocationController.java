/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.techwave.elasticsearch.controlle;

import com.techwave.elasticsearch.model.ReservationLocation;
import com.techwave.elasticsearch.service.ReservationLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reservations-location")
public class ReservationLocationController {

    @Autowired
    private ReservationLocationService reservationLocationService;

    /*@GetMapping
    public List<ReservationLocation> getAllReservationsLocation() {
        return reservationLocationService.getAllReservationsLocation();
    }*/

    @GetMapping("/{id}")
    public ResponseEntity<ReservationLocation> getReservationLocationById(@PathVariable String id) {
        Optional<ReservationLocation> reservation = reservationLocationService.getReservationLocationById(id);
        return reservation.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ReservationLocation> addReservationLocation(@RequestBody ReservationLocation reservation) throws IOException {
        ReservationLocation saved = reservationLocationService.addReservationLocation(reservation);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReservationLocation> updateReservationLocation(@PathVariable String id, @RequestBody ReservationLocation reservation) throws IOException {
        ReservationLocation updated = reservationLocationService.updateReservationLocation(id, reservation);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservationLocation(@PathVariable String id) throws IOException {
        reservationLocationService.deleteReservationLocation(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public List<ReservationLocation> searchReservationsLocation(@RequestParam String q) {
        return reservationLocationService.searchReservationsLocation(q);
    }
}