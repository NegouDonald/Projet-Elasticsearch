package com.techwave.elasticsearch.service;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;
import com.techwave.elasticsearch.model.Vehicle;
import org.springframework.data.elasticsearch.core.query.Query;


import java.util.ArrayList;
import java.util.List;

@Service
public class VehicleSearchService {

    private final ElasticsearchOperations elasticsearchOperations;

    public VehicleSearchService(ElasticsearchOperations elasticsearchOperations) {
        this.elasticsearchOperations = elasticsearchOperations;
    }

    public List<Vehicle> searchVehicles(String marque, String modele, Integer anneeMin, String statut, String type) {
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();

        if (marque != null && !marque.isEmpty()) {
            boolQuery.must(QueryBuilders.matchQuery("marque", marque));
        }
        if (modele != null && !modele.isEmpty()) {
            boolQuery.must(QueryBuilders.matchQuery("modele", modele));
        }
        if (anneeMin != null) {
            boolQuery.must(QueryBuilders.rangeQuery("annee").gte(anneeMin));
        }
        if (statut != null && !statut.isEmpty()) {
            boolQuery.must(QueryBuilders.termQuery("statut.keyword", statut));
        }
        if (type != null && !type.isEmpty()) {
            boolQuery.must(QueryBuilders.termQuery("type.keyword", type));
        }

        // Création de la requête avec la nouvelle API Spring Data Elasticsearch
        Query searchQuery = org.springframework.data.elasticsearch.core.query.Query.findByQuery(boolQuery);

        // Exécution de la recherche
        SearchHits<Vehicle> searchHits = elasticsearchOperations.search(searchQuery, Vehicle.class);

        // Récupération des résultats
        List<Vehicle> results = new ArrayList<>();
        for (SearchHit<Vehicle> hit : searchHits) {
            results.add(hit.getContent());
        }

        return results;
    }
}
