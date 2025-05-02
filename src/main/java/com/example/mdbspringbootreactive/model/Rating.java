package com.example.mdbspringbootreactive.model;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.sql.Date;

@Setter
@Getter
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
