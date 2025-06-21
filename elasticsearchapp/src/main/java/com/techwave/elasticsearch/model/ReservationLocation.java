/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.techwave.elasticsearch.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.util.Date;

@Data
@Document(indexName = "reservations_location")
public class ReservationLocation {

    @Id
    private String id;

    @Field(type = FieldType.Date, format = DateFormat.basic_date)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date dateDebut;

    @Field(type = FieldType.Date, format = DateFormat.basic_date)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date dateFin;

    @Field(type = FieldType.Keyword)
    private String voitureId;

    @Field(type = FieldType.Keyword)
    private String clientId;

    @Field(type = FieldType.Keyword)
    private String statut;
}
