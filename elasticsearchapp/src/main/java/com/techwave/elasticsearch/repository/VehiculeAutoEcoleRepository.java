/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.techwave.elasticsearch.repository;

import com.techwave.elasticsearch.model.VehiculeAutoEcole;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import java.util.List;

public interface VehiculeAutoEcoleRepository extends ElasticsearchRepository<VehiculeAutoEcole, String> {

    List<VehiculeAutoEcole> findByMarqueContainingIgnoreCase(String marque);

    List<VehiculeAutoEcole> findByModeleContainingIgnoreCase(String modele);
}
