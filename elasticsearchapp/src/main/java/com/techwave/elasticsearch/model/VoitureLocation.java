/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.techwave.elasticsearch.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import lombok.Data;

@Data
@Document(indexName = "voitures_location")
public class VoitureLocation {

    @Id
    private String id;

    @Field(type = FieldType.Keyword)
    private String marque;

    @Field(type = FieldType.Keyword)
    private String modele;

    @Field(type = FieldType.Integer)
    private Integer annee;

    @Field(type = FieldType.Keyword)
    private String plaqueImmatriculation;

    @Field(type = FieldType.Float)
    private Float tarifJournalier;

    @Field(type = FieldType.Boolean)
    private Boolean disponible;
}
