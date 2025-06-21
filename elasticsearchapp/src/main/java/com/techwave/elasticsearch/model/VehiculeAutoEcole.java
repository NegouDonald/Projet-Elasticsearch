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
@Document(indexName = "vehicules_auto_ecole")
public class VehiculeAutoEcole {

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
}