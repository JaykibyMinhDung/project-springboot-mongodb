package com.example.mdbspringbootreactive.repository;

import com.example.mdbspringbootreactive.model.Order;
import org.springframework.data.domain.Limit;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface OrderRepository extends ReactiveMongoRepository<Order, String> {
//    Flux<Order> findAll(Sort sort, Limit limit);

    Flux<Order> findByIdOrderById(int id);
}
