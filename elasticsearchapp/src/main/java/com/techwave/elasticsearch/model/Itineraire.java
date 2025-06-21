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
@Document(indexName = "itineraires")
public class Itineraire {

    @Id
    private String id;

    @Field(type = FieldType.Keyword)
    private String depart;

    @Field(type = FieldType.Keyword)
    private String arrivee;

    @Field(type = FieldType.Float)
    private Float distance;

    @Field(type = FieldType.Integer)
    private Integer dureeEstimee;
}