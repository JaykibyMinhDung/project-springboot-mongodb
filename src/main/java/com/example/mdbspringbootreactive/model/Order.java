package com.example.mdbspringbootreactive.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import java.sql.Date;

public class Order {
    @Id
    private int id;
    private int userId;
    private int roleId;
    private String methodPayment;
    @CreatedDate
    private Date startDate;
    private String totalCart;
    private String status;

    public Order() {}

    public Order(int id, int userId, int roleId, String methodPayment, Date startDate, String totalCart, String status) {
        this.id = id;
        this.userId = userId;
        this.roleId = roleId;
        this.methodPayment = methodPayment;
        this.startDate = startDate;
        this.totalCart = totalCart;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
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
