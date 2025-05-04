package com.example.mdbspringbootreactive.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@Document(collection = "product")
public class Product {
    @Id
    private String id;
    private String idRating;
    private String category;
    private String description;
    private String image;
    private Integer price;  // Dùng Integer vì trong MongoDB là Int32
    private String title;
    private Boolean deleted;

    public Product() {}

    public Product(String id, String idRating, String category, String description, String image, Integer price, String title, Boolean deleted) {
        this.id = id;
        this.idRating = idRating;
        this.category = category;
        this.description = description;
        this.image = image;
        this.price = price;
        this.title = title;
        this.deleted = deleted;
    }

    public Product(String id, String category, String description, String image, Integer price, String title, String idRating) {
        this.id = id;
        this.category = category;
        this.description = description;
        this.image = image;
        this.price = price;
        this.title = title;
        this.idRating = idRating;
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

    public void setDelete(boolean b) {
        this.deleted = b;
    }
}
