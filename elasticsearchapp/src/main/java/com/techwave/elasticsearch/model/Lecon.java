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
@Document(indexName = "lecons")
public class Lecon {

    @Id
    private String id;

    @Field(type = FieldType.Date, format = DateFormat.date) // Changé de basic_date à date
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date dateLecon;

    @Field(type = FieldType.Keyword)
    private String heureLecon;

    @Field(type = FieldType.Integer)
    private Integer dureeLecon;

    @Field(type = FieldType.Keyword)
    private String instructeurId;

    @Field(type = FieldType.Keyword)
    private String eleveId;

    @Field(type = FieldType.Keyword)
    private String vehiculeId;
}