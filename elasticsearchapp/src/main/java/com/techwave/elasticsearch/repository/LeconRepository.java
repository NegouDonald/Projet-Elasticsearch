/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.techwave.elasticsearch.repository;

import com.techwave.elasticsearch.model.Lecon;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import java.util.List;

public interface LeconRepository extends ElasticsearchRepository<Lecon, String> {

    List<Lecon> findByInstructeurIdContainingIgnoreCase(String instructeurId);

    List<Lecon> findByEleveIdContainingIgnoreCase(String eleveId);
}
