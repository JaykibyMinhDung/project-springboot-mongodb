package com.example.mdbspringbootreactive.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Document(collection = "product")
public class Product {
    @Id
    private String id;
    private List<String> idRating;
    private String category;
    private String description;
    private String image;
    private Integer price;  // Dùng Integer vì trong MongoDB là Int32
    private String title;
    private Integer quantity;
    private Boolean deleted;

    @CreatedDate
    private LocalDateTime createdDate;
    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    public Product() {
        this.idRating = new ArrayList<>();  // Initialize as an empty list
    }

    public Product(String id, List<String> idRating, String category, String description, String image, Integer price, String title, Boolean deleted, Integer quantity, LocalDateTime createdDate, LocalDateTime lastModifiedDate) {
        this.id = id;
        this.idRating = idRating != null ? idRating : new ArrayList<>();
        this.category = category;
        this.description = description;
        this.image = image;
        this.price = price;
        this.title = title;
        this.deleted = deleted;
        this.quantity = quantity;
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
    }

    public Product(String id, String category, String description, String image, Integer price, String title, List<String> idRating) {
        this.id = id;
        this.category = category;
        this.description = description;
        this.image = image;
        this.price = price;
        this.title = title;
        this.idRating = idRating != null ? idRating : new ArrayList<>();
    }

    public List<String> getIdRating() {
        return idRating;
    }

    public void setIdRating(List<String> idRating) {
        this.idRating = idRating;
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

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
