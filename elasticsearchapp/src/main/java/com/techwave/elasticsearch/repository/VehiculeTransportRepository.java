/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.techwave.elasticsearch.repository;

import com.techwave.elasticsearch.model.VehiculeTransport;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import java.util.List;

public interface VehiculeTransportRepository extends ElasticsearchRepository<VehiculeTransport, String> {

    List<VehiculeTransport> findByMarqueContainingIgnoreCase(String marque);

    List<VehiculeTransport> findByModeleContainingIgnoreCase(String modele);
}