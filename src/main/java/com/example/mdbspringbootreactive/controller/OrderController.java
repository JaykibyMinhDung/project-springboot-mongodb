package com.example.mdbspringbootreactive.controller;

import com.example.mdbspringbootreactive.config.ApiResponse;
import com.example.mdbspringbootreactive.model.Order;
import com.example.mdbspringbootreactive.service.OrderService;
import org.slf4j.ILoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderService service;

    private final static Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

    @GetMapping
    public Mono<ResponseEntity<ApiResponse<List<Order>>>> getAllOrders() {
        return service.findAll()
                .collectList()
                .map(orders -> {
                    ApiResponse<List<Order>> response = new ApiResponse<>("Lấy danh sách đơn hàng thành công", 1, orders);
                    return ResponseEntity.ok().body(response);
                })
                .onErrorResume(error -> {
                    LOGGER.error("Lỗi khi lấy danh sách đơn hàng: " + error.getMessage());
                    ApiResponse<List<Order>> response = new ApiResponse<>("Lấy danh sách đơn hàng thất bại", 0, null);
                    return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response));
                });
    }

    @PostMapping
    public Mono<ResponseEntity<ApiResponse<Order>>> create(@RequestBody Order order) {
        LOGGER.info("Nhận yêu cầu lưu đơn hàng: {}", order);
        return service.saveOrder(order)
                .map(savedOrder -> {
                    ApiResponse<Order> response = new ApiResponse<>("Giao dịch thành công", 1, savedOrder);
                    return ResponseEntity.ok().body(response);
                })
                .onErrorResume(error -> {
                    LOGGER.error("Lỗi khi lưu đơn hàng: ", error);
                    ApiResponse<Order> response = new ApiResponse<>("Giao dịch thất bại vui lòng liên hệ cửa hàng", 0, null);
                    return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response));
                });
    }
}