package com.example.mdbspringbootreactive.model;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.sql.Date;

@Document(collection = "rating")
public class Rating {
    @Id
    private String id;
    private String userId;
    private String productId;
    private String comment;
    private int rating;
    @CreatedDate
    private Date startDate;

    public Rating() {}

    public Rating(int id, String userId, String productId, String comment, int rating) {
        this.userId = userId;
        this.productId = productId;
        this.comment = comment;
        this.rating = rating;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Override
    public String toString() {
        return "Rating{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", productId='" + productId + '\'' +
                ", comment='" + comment + '\'' +
                ", rating=" + rating +
                ", startDate=" + startDate +
                '}';
    }
}
