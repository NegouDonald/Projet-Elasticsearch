package com.techwave.elasticsearch.controlle;

import com.techwave.elasticsearch.model.*;
import com.techwave.elasticsearch.service.SearchService;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.data.elasticsearch.core.query.StringQuery;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.http.ResponseEntity;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class SearchController {

    @Autowired
    private SearchService searchService;

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    // CORRECTION : Suppression du préfixe /api en double
    @GetMapping("/suggestions")
    public ResponseEntity<List<String>> getSuggestions(@RequestParam String q) {
        try {
            // Validation de la longueur minimale
            if (q == null || q.trim().length() < 2) {
                return ResponseEntity.ok(Collections.emptyList());
            }
            
            List<String> suggestions = searchService.getSuggestions(q.trim());
            return ResponseEntity.ok(suggestions);
        } catch (Exception e) {
            // Log l'erreur pour debugging
            System.err.println("Erreur dans getSuggestions: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.ok(Collections.emptyList());
        }
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<SearchResult>> search(@RequestParam String q) {
        try {
            // Validation de la requête
            if (q == null || q.trim().isEmpty()) {
                return ResponseEntity.ok(Collections.emptyList());
            }
            
            List<SearchResult> results = searchService.searchAcrossAllIndexes(q.trim());
            return ResponseEntity.ok(results);
        } catch (Exception e) {
            System.err.println("Erreur dans search: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.ok(Collections.emptyList());
        }
    }

    @GetMapping("/eleves")
    public List<Eleve> getAllEleves() {
        return getAllDocuments("eleves", Eleve.class);
    }

    @GetMapping("/instructeurs")
    public List<Instructeur> getAllInstructeurs() {
        return getAllDocuments("instructeurs", Instructeur.class);
    }

    @GetMapping("/lecons")
    public List<Lecon> getAllLecons() {
        return getAllDocuments("lecons", Lecon.class);
    }

    @GetMapping("/vehicules_auto_ecole")
    public List<VehiculeAutoEcole> getAllVehiculesAutoEcole() {
        return getAllDocuments("vehicules_auto_ecole", VehiculeAutoEcole.class);
    }

    @GetMapping("/voitures_location")
    public List<VoitureLocation> getAllVoituresLocation() {
        return getAllDocuments("voitures_location", VoitureLocation.class);
    }

    @GetMapping("/clients_location")
    public List<ClientLocation> getAllClientsLocation() {
        return getAllDocuments("clients_location", ClientLocation.class);
    }

    @GetMapping("/reservations_location")
    public List<ReservationLocation> getAllReservationsLocation() {
        return getAllDocuments("reservations_location", ReservationLocation.class);
    }

    @GetMapping("/itineraires")
    public List<Itineraire> getAllItineraires() {
        return getAllDocuments("itineraires", Itineraire.class);
    }

    @GetMapping("/vehicules_transport")
    public List<VehiculeTransport> getAllVehiculesTransport() {
        return getAllDocuments("vehicules_transport", VehiculeTransport.class);
    }

    @GetMapping("/conducteurs")
    public List<Conducteur> getAllConducteurs() {
        return getAllDocuments("conducteurs", Conducteur.class);
    }

    @GetMapping("/horaires")
    public List<Horaire> getAllHoraires() {
        return getAllDocuments("horaires", Horaire.class);
    }

    private <T> List<T> getAllDocuments(String indexName, Class<T> clazz) {
        try {
            Query query = new StringQuery("{\"match_all\": {}}");
            SearchHits<T> searchHits = elasticsearchOperations.search(query, clazz, IndexCoordinates.of(indexName));
            return searchHits.getSearchHits().stream()
                    .map(hit -> hit.getContent())
                    .collect(Collectors.toList());
        } catch (Exception e) {
            System.err.println("Erreur dans getAllDocuments pour " + indexName + ": " + e.getMessage());
            return Collections.emptyList();
        }
    }
}