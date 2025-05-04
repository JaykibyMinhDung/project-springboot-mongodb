package com.example.mdbspringbootreactive.repository;

import com.example.mdbspringbootreactive.model.Order;
import org.springframework.data.domain.Limit;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface OrderRepository extends ReactiveMongoRepository<Order, String> {
    @Query("{'status':  waiting}")
    Flux<Order> findAllCartById(String userId);

    @Query("{'status': { $ne: 'waiting' }, 'userId': ?0}")
    Flux<Order> findAllHistoryById(String userId);

    Flux<Order> findByIdOrderById(String id);
}
