/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.techwave.elasticsearch.repository;

import com.techwave.elasticsearch.model.Itineraire;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import java.util.List;

public interface ItineraireRepository extends ElasticsearchRepository<Itineraire, String> {

    List<Itineraire> findByDepartContainingIgnoreCase(String depart);

    List<Itineraire> findByArriveeContainingIgnoreCase(String arrivee);
}