package com.evanaurelrius.seacademy.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", updatable = false)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "imageSrc")
    private String imageSrc;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private String price;

    @Column(name = "timestamp")
    private Timestamp timestamp;

    public Product() {
    }

    public Product(String name, String imageSrc, String description, String price, Timestamp timestamp) {
        this.name = name;
        this.imageSrc = imageSrc;
        this.description = description;
        this.price = price;
        this.timestamp = timestamp;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageSrc() {
        return imageSrc;
    }

    public void setImageSrc(String imgSrc) {
        this.imageSrc = imgSrc;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
