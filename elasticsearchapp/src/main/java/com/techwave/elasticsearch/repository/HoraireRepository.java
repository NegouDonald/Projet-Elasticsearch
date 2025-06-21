/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.techwave.elasticsearch.repository;

import com.techwave.elasticsearch.model.Horaire;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import java.util.List;

public interface HoraireRepository extends ElasticsearchRepository<Horaire, String> {

    List<Horaire> findByItineraireIdContainingIgnoreCase(String itineraireId);

    List<Horaire> findByConducteurIdContainingIgnoreCase(String conducteurId);
}