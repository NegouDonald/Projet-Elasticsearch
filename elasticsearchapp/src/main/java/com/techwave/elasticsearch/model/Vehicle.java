package com.techwave.elasticsearch.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "vehicles")
@Data
public class Vehicle {
    @Id
    private String id;
    private String marque;
    private String modele;
    private int annee;
    private String immatriculation;
    private String type;
    private String statut;

    // Constructeurs, getters et setters
    public Vehicle() {}

    public Vehicle(String id, String marque, String modele, int annee, String immatriculation, String type, String statut) {
        this.id = id;
        this.marque = marque;
        this.modele = modele;
        this.annee = annee;
        this.immatriculation = immatriculation;
        this.type = type;
        this.statut = statut;
    }

    // getters & setters ...
}
