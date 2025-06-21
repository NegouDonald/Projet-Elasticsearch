/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.techwave.elasticsearch.repository;

import com.techwave.elasticsearch.model.Conducteur;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import java.util.List;

public interface ConducteurRepository extends ElasticsearchRepository<Conducteur, String> {

    List<Conducteur> findByNomContainingIgnoreCase(String nom);

    List<Conducteur> findByPrenomContainingIgnoreCase(String prenom);
}
