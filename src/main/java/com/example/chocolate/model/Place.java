package com.example.chocolate.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;      // e.g. Brussels
    private String country;   // e.g. Belgium

    @Column(length = 1000)
    private String description;

    // One place has many chocolates
    @OneToMany(mappedBy = "place")
    private List<Chocolate> chocolates = new ArrayList<>();

    public Place() {}

    public Place(String name, String country, String description) {
        this.name = name;
        this.country = country;
        this.description = description;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public List<Chocolate> getChocolates() { return chocolates; }
    public void setChocolates(List<Chocolate> chocolates) { this.chocolates = chocolates; }
}
