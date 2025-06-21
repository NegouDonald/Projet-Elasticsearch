/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.techwave.elasticsearch.repository;

import com.techwave.elasticsearch.model.Instructeur;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import java.util.List;

public interface InstructeurRepository extends ElasticsearchRepository<Instructeur, String> {

    List<Instructeur> findByNomContainingIgnoreCase(String nom);

    List<Instructeur> findByPrenomContainingIgnoreCase(String prenom);
}