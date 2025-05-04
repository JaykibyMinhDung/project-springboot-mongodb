package com.example.mdbspringbootreactive.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.sql.Date;

@Setter
@Getter
@Document("order")
public class Order {
    @Id
    private String id;
    private String userId;
    private String roleId;
    private String methodPayment;
    @CreatedDate
    private Date startDate;
    private String totalCart;
    private String status;

    public Order() {}

    public Order(String id, String userId, String roleId, String methodPayment, Date startDate, String totalCart, String status) {
        this.id = id;
        this.userId = userId;
        this.roleId = roleId;
        this.methodPayment = methodPayment;
        this.startDate = startDate;
        this.totalCart = totalCart;
        this.status = status;
    }

}
