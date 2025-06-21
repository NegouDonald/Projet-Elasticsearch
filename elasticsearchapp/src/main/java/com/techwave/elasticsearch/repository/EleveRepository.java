/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.techwave.elasticsearch.repository;

import com.techwave.elasticsearch.model.Eleve;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import java.util.List;

public interface EleveRepository extends ElasticsearchRepository<Eleve, String> {

    List<Eleve> findByNomContainingIgnoreCase(String nom);

    List<Eleve> findByPrenomContainingIgnoreCase(String prenom);
}