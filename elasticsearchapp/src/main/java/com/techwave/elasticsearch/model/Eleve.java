
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
@Document(indexName = "eleves")
public class Eleve {

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
}