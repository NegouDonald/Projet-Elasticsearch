/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.techwave.elasticsearch.service;

import com.techwave.elasticsearch.model.Conducteur;
import com.techwave.elasticsearch.model.SearchResult;
import com.techwave.elasticsearch.model.VoitureLocation;
import com.techwave.elasticsearch.model.VehiculeTransport;
import com.techwave.elasticsearch.model.VehiculeAutoEcole;
import com.techwave.elasticsearch.model.ReservationLocation;
import com.techwave.elasticsearch.model.Horaire;
import com.techwave.elasticsearch.model.Lecon;
import com.techwave.elasticsearch.model.Itineraire;
import com.techwave.elasticsearch.model.Instructeur;
import com.techwave.elasticsearch.model.IndexMetadata;
import com.techwave.elasticsearch.model.Eleve;
import com.techwave.elasticsearch.model.ClientLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.data.elasticsearch.core.query.StringQuery;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SearchService {

    private static final Logger log = LoggerFactory.getLogger(SearchService.class);

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;
    
    public List<String> getSuggestions(String query) {
    List<String> suggestions = new ArrayList<>();

    Map<String, Class<?>> indexToClass = new HashMap<>();
    indexToClass.put("eleves", Eleve.class);
    indexToClass.put("instructeurs", Instructeur.class);
    indexToClass.put("voitures_location", VoitureLocation.class);
    indexToClass.put("lecons", Lecon.class);
    indexToClass.put("vehicules_auto_ecole", VehiculeAutoEcole.class);
    indexToClass.put("clients_location", ClientLocation.class);
    indexToClass.put("reservations_location", ReservationLocation.class);
    indexToClass.put("itineraires", Itineraire.class);
    indexToClass.put("vehicules_transport", VehiculeTransport.class);
    indexToClass.put("conducteurs", Conducteur.class);
    indexToClass.put("horaires", Horaire.class);

    for (IndexMetadata metadata : IndexMetadata.values()) {
        String indexName = metadata.getIndexName();
        Class<?> entityClass = indexToClass.get(indexName);
        if (entityClass == null) continue;

        try {
            String queryString = String.format(
                "{\"query\": {\"match_phrase_prefix\": {\"nom\": \"%s\"}}}",
                query.replace("\"", "\\\"")
            );

            Query searchQuery = new StringQuery(queryString);
            SearchHits<?> searchHits = elasticsearchOperations.search(searchQuery, entityClass, IndexCoordinates.of(indexName));

            for (var hit : searchHits) {
                Object content = hit.getContent();
                try {
                    String value = (String) entityClass.getMethod("getNom").invoke(content);
                    if (value != null && !value.isBlank() && !suggestions.contains(value)) {
                        suggestions.add(value);
                    }
                } catch (NoSuchMethodException ignored) {
                    // Classe sans méthode getNom, on saute
                }
            }
        } catch (Exception e) {
            log.warn("Erreur de suggestion pour l'index {}: {}", indexName, e.getMessage());
        }
    }

    return suggestions;
}


    public List<SearchResult> searchAcrossAllIndexes(String query) {
    List<SearchResult> results = new ArrayList<>();
    Map<String, Class<?>> indexToClass = new HashMap<>();
    indexToClass.put("eleves", Eleve.class);
    indexToClass.put("instructeurs", Instructeur.class);
    indexToClass.put("voitures_location", VoitureLocation.class);
    indexToClass.put("lecons", Lecon.class);
    indexToClass.put("vehicules_auto_ecole", VehiculeAutoEcole.class);
    indexToClass.put("clients_location", ClientLocation.class);
    indexToClass.put("reservations_location", ReservationLocation.class);
    indexToClass.put("itineraires", Itineraire.class);
    indexToClass.put("vehicules_transport", VehiculeTransport.class);
    indexToClass.put("conducteurs", Conducteur.class);
    indexToClass.put("horaires", Horaire.class);

    log.info("Starting search for query: {}", query);
    for (IndexMetadata metadata : IndexMetadata.values()) {
        String indexName = metadata.getIndexName();
        log.debug("Searching index: {}", indexName);
        try {
            Class<?> entityClass = indexToClass.get(indexName);
            if (entityClass == null) {
                log.warn("No entity class mapped for index: {}", indexName);
                continue;
            }

            String queryString = String.format(
                    "{\"query_string\": {\"query\": \"*%s*\", \"fields\": [\"nom\", \"prenom\", \"marque\", \"modele\", \"depart\", \"arrivee\"], \"default_operator\": \"OR\"}}",
                    query.replace("\"", "\\\"")
                );
            log.debug("Query string: {}", queryString);
            Query searchQuery = new StringQuery(queryString);
            IndexCoordinates index = IndexCoordinates.of(indexName);
            SearchHits<?> searchHits = elasticsearchOperations.search(searchQuery, entityClass, index);
            log.debug("Found {} hits in index {}", searchHits.getTotalHits(), indexName);

            searchHits.getSearchHits().forEach(hit -> {
                log.debug("Hit content: {}", hit.getContent());
                SearchResult result = new SearchResult();
                result.setEntity(hit.getContent());
                result.setServiceName(metadata.getServiceName());
                result.setWebUrl(metadata.getWebUrl());
                results.add(result);
            });
        } catch (Exception e) {
            log.error("Error searching in index {}: {}", indexName, e.getMessage(), e);
        }
    }

    log.info("Found {} results for query: {}", results.size(), query);
    return results;
}
}