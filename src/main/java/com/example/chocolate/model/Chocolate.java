package com.example.chocolate.model;

import jakarta.persistence.*;

@Entity
public class Chocolate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String type;      // Dark, Milk, White or Ruby

    @Column(length = 1000)
    private String description;

    private double price;     // price per 100 g, in rupees

    @Column(length = 500)
    private String imageUrl;

    // Many chocolates belong to one place
    @ManyToOne
    @JoinColumn(name = "place_id")
    private Place place;

    public Chocolate() {}

    public Chocolate(String name, String type, String description, double price, Place place) {
        this(name, type, description, price, place, null);
    }

    public Chocolate(String name, String type, String description, double price, Place place, String imageUrl) {
        this.name = name;
        this.type = type;
        this.description = description;
        this.price = price;
        this.place = place;
        this.imageUrl = imageUrl;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public Place getPlace() { return place; }
    public void setPlace(Place place) { this.place = place; }
}
