package com.example.globetrotter.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Document(collection = "destinations")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Destination {

    @Id
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<String> getClues() {
        return clues;
    }

    public void setClues(List<String> clues) {
        this.clues = clues;
    }

    public List<String> getFunFacts() {
        return funFacts;
    }

    public void setFunFacts(List<String> funFacts) {
        this.funFacts = funFacts;
    }

    public List<String> getTrivia() {
        return trivia;
    }

    public void setTrivia(List<String> trivia) {
        this.trivia = trivia;
    }

    private String city;
    private String country;
    private List<String> clues;
    @Field("fun_fact")
    private List<String> funFacts;
    private List<String> trivia;





}
