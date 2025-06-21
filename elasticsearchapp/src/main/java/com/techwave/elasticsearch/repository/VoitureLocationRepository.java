/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.techwave.elasticsearch.repository;

import com.techwave.elasticsearch.model.VoitureLocation;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import java.util.List;

public interface VoitureLocationRepository extends ElasticsearchRepository<VoitureLocation, String> {

    List<VoitureLocation> findByMarqueContainingIgnoreCase(String marque);

    List<VoitureLocation> findByModeleContainingIgnoreCase(String modele);
}
