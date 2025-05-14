package com.example.mdbspringbootreactive.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document("order")
public class Order {
    @Id
    private String id;
    private String userId;
    private String roleId;
    private String methodPayment;
    @CreatedDate
    private Date startDate;
    @LastModifiedDate
    private Date updateDate;
    private String totalCart;
    private String status;

    public Order() {}

    public Order(String id, String userId, String roleId, String methodPayment, Date startDate, Date updateDate, String totalCart, String status) {
        this.id = id;
        this.userId = userId;
        this.roleId = roleId;
        this.methodPayment = methodPayment;
        this.startDate = startDate;
        this.updateDate = updateDate;
        this.totalCart = totalCart;
        this.status = status;
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

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getMethodPayment() {
        return methodPayment;
    }

    public void setMethodPayment(String methodPayment) {
        this.methodPayment = methodPayment;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getTotalCart() {
        return totalCart;
    }

    public void setTotalCart(String totalCart) {
        this.totalCart = totalCart;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
