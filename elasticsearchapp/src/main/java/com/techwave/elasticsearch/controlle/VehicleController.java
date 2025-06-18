package com.techwave.elasticsearch.controlle;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.json.JsonData;
import com.techwave.elasticsearch.model.Vehicle;
import com.techwave.elasticsearch.service.VehicleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;
    private final ElasticsearchClient elasticsearchClient;

    public VehicleController(VehicleService vehicleService, ElasticsearchClient elasticsearchClient) {
        this.vehicleService = vehicleService;
        this.elasticsearchClient = elasticsearchClient;
    }

    @GetMapping
    public List<Vehicle> getAllVehicles() {
        return vehicleService.getAllVehicles();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vehicle> getVehicleById(@PathVariable String id) {
        Optional<Vehicle> vehicle = vehicleService.getVehicleById(id);
        return vehicle.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Vehicle> addVehicle(@RequestBody Vehicle vehicle) throws IOException {
        Vehicle saved = vehicleService.addVehicle(vehicle);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vehicle> updateVehicle(@PathVariable String id, @RequestBody Vehicle vehicle) throws IOException {
        Vehicle updated = vehicleService.updateVehicle(id, vehicle);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable String id) throws IOException {
        vehicleService.deleteVehicle(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public List<Vehicle> searchVehicles(
            @RequestParam String q,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String statut,
            @RequestParam(required = false) Integer anneeMin,
            @RequestParam(required = false) Integer anneeMax
    ) throws IOException {

        SearchResponse<Vehicle> response = elasticsearchClient.search(s -> s
                        .index("vehicles")
                        .query(query -> query.bool(b -> {
                            List<co.elastic.clients.elasticsearch._types.query_dsl.Query> shouldClauses = new ArrayList<>();

                            if (q != null && !q.isEmpty()) {
                                shouldClauses.add(co.elastic.clients.elasticsearch._types.query_dsl.Query.of(mq ->
                                        mq.multiMatch(mm -> mm
                                                .query(q)
                                                .fields("marque", "modele")
                                        )
                                ));
                            }

                            if (type != null && !type.isEmpty()) {
                                shouldClauses.add(co.elastic.clients.elasticsearch._types.query_dsl.Query.of(term ->
                                        term.term(t -> t.field("type").value(type))
                                ));
                            }

                            if (statut != null && !statut.isEmpty()) {
                                shouldClauses.add(co.elastic.clients.elasticsearch._types.query_dsl.Query.of(term ->
                                        term.term(t -> t.field("statut").value(statut))
                                ));
                            }

                            if (anneeMin != null) {
                                shouldClauses.add(co.elastic.clients.elasticsearch._types.query_dsl.Query.of(range ->
                                        range.range(r -> r.field("annee").gte(JsonData.of(anneeMin)))
                                ));
                            }

                            if (anneeMax != null) {
                                shouldClauses.add(co.elastic.clients.elasticsearch._types.query_dsl.Query.of(range ->
                                        range.range(r -> r.field("annee").lte(JsonData.of(anneeMax)))
                                ));
                            }

                            return b.should(shouldClauses)
                                    .minimumShouldMatch("1"); // Au moins un critère doit matcher
                        })),
                Vehicle.class
        );


        List<Vehicle> results = new ArrayList<>();
        for (Hit<Vehicle> hit : response.hits().hits()) {
            results.add(hit.source());
        }
        return results;
    }
}
