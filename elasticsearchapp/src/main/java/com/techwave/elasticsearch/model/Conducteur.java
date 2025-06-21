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
@Document(indexName = "conducteurs")
public class Conducteur {

    @Id
    private String id;

    @Field(type = FieldType.Text, analyzer = "french")
    private String nom;

    @Field(type = FieldType.Text, analyzer = "french")
    private String prenom;

    @Field(type = FieldType.Text)
    private String adresse;

    @Field(type = FieldType.Keyword)
    private String numeroPermis;
}