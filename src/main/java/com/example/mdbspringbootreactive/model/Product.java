package com.example.mdbspringbootreactive.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Document(collection = "product")
public class Product {
//    @Id
//    private String id;
//    private String title;
//    private String category_id;
//    private String description;
//    private Double price;
//    private LocalDateTime created_at;
//    private LocalDateTime updated_at;

    @Id
    private String id;
    private String idRating;
    private String category;
    private String description;
    private String image;
    private Integer price;  // Dùng Integer vì trong MongoDB là Int32
    private String title;

    public Product() {}

    public Product(String id, String category, String description, String image, Integer price, String title, String idRating) {
        this.id = id;
        this.category = category;
        this.description = description;
        this.image = image;
        this.price = price;
        this.title = title;
        this.idRating = idRating;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public String getIdRating() {
        return idRating;
    }

    public void setIdRating(String idRating) {
        this.idRating = idRating;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", category='" + category + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", price=" + price +
                ", title='" + title + '\'' +
                '}';
    }
}
