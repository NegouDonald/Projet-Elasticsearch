/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.techwave.elasticsearch.repository;

import com.techwave.elasticsearch.model.ClientLocation;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import java.util.List;

public interface ClientLocationRepository extends ElasticsearchRepository<ClientLocation, String> {

    List<ClientLocation> findByNomContainingIgnoreCase(String nom);

    List<ClientLocation> findByPrenomContainingIgnoreCase(String prenom);
}
