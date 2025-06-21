/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.techwave.elasticsearch.repository;

import com.techwave.elasticsearch.model.ReservationLocation;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import java.util.List;

public interface ReservationLocationRepository extends ElasticsearchRepository<ReservationLocation, String> {

    List<ReservationLocation> findByVoitureIdContainingIgnoreCase(String voitureId);

    List<ReservationLocation> findByClientIdContainingIgnoreCase(String clientId);
}