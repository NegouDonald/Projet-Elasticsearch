/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.techwave.elasticsearch.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@Document(indexName = "horaires")
public class Horaire {

    @Id
    private String id;

    @Field(type = FieldType.Date, format = DateFormat.basic_date)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date date;

    @Field(type = FieldType.Keyword)
    private String heureDepart;

    @Field(type = FieldType.Keyword)
    private String heureArrivee;

    @Field(type = FieldType.Keyword)
    private String itineraireId;

    @Field(type = FieldType.Keyword)
    private String vehiculeId;

    @Field(type = FieldType.Keyword)
    private String conducteurId;
}