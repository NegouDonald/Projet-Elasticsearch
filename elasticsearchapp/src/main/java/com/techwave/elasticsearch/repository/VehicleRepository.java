package com.techwave.elasticsearch.repository;


import com.techwave.elasticsearch.model.Vehicle;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepository extends ElasticsearchRepository<Vehicle, String> {
    List<Vehicle> findByMarqueContainingIgnoreCase(String marque);
    List<Vehicle> findByModeleContainingIgnoreCase(String modele);
// ajout des filtres
    List<Vehicle> findByMarqueContainingAndModeleContainingAndAnneeGreaterThanEqualAndStatutAndType(
            String marque, String modele, int annee, String statut, String type);

}
