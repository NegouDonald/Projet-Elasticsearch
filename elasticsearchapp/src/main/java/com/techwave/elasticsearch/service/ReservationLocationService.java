/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.techwave.elasticsearch.service;

import com.techwave.elasticsearch.repository.ReservationLocationRepository;
import com.techwave.elasticsearch.model.ReservationLocation;
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
public class ReservationLocationService {

    private static final String RESERVATIONS_LOCATION_JSON = "reservations_location.json";

    @Autowired
    private ReservationLocationRepository reservationLocationRepository;

    private ObjectMapper objectMapper = new ObjectMapper();

    @PostConstruct
    public void loadReservationsLocation() throws IOException {
        File file = new File(RESERVATIONS_LOCATION_JSON);
        if (file.exists()) {
            List<ReservationLocation> reservations = objectMapper.readValue(file, new TypeReference<List<ReservationLocation>>() {});
            reservationLocationRepository.saveAll(reservations);
        }
    }

    public List<ReservationLocation> getAllReservationsLocation() {
        Iterable<ReservationLocation> iterable = reservationLocationRepository.findAll();
        return StreamSupport.stream(iterable.spliterator(), false)
                .collect(Collectors.toList());
    }

    public Optional<ReservationLocation> getReservationLocationById(String id) {
        return reservationLocationRepository.findById(id);
    }

    public ReservationLocation addReservationLocation(ReservationLocation reservation) throws IOException {
        ReservationLocation saved = reservationLocationRepository.save(reservation);
        saveReservationsLocationToJson();
        return saved;
    }

    public ReservationLocation updateReservationLocation(String id, ReservationLocation reservation) throws IOException {
        reservation.setId(id);
        ReservationLocation updated = reservationLocationRepository.save(reservation);
        saveReservationsLocationToJson();
        return updated;
    }

    public void deleteReservationLocation(String id) throws IOException {
        reservationLocationRepository.deleteById(id);
        saveReservationsLocationToJson();
    }

    public List<ReservationLocation> searchReservationsLocation(String query) {
        List<ReservationLocation> byVoitureId = reservationLocationRepository.findByVoitureIdContainingIgnoreCase(query);
        List<ReservationLocation> byClientId = reservationLocationRepository.findByClientIdContainingIgnoreCase(query);
        byVoitureId.addAll(byClientId);
        return byVoitureId;
    }

    private void saveReservationsLocationToJson() throws IOException {
        List<ReservationLocation> allReservations = getAllReservationsLocation();
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(RESERVATIONS_LOCATION_JSON), allReservations);
    }
}