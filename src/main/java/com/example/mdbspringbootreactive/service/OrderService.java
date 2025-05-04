package com.example.mdbspringbootreactive.service;

import com.example.mdbspringbootreactive.model.Order;
import com.example.mdbspringbootreactive.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository repository;

    public Flux<Order> findAll() {
        return repository.findAll();
    }

    public Flux<Order> findOrderById(Integer id) {
        return repository.findByIdOrderById(id);
    }

    public Mono<Order> saveOrder(Order order) {
        return repository.save(order);
    }
}
