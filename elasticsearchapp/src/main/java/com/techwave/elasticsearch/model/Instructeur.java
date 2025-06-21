/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.techwave.elasticsearch.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.util.Date;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@Document(indexName = "instructeurs")
public class Instructeur {

    @Id
    private String id;

    @Field(type = FieldType.Text, analyzer = "french")
    private String nom;

    @Field(type = FieldType.Text, analyzer = "french")
    private String prenom;

    @Field(type = FieldType.Date, format = DateFormat.date) // Changé de basic_date à date
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date dateNaissance;

    @Field(type = FieldType.Text)
    private String adresse;

    @Field(type = FieldType.Keyword)
    private String numeroLicence;
}