package com.example.mdbspringbootreactive.repository;

import com.example.mdbspringbootreactive.model.Account;
import com.example.mdbspringbootreactive.model.Product;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.springframework.data.domain.Pageable;

@Repository
public interface ProductRepository extends ReactiveMongoRepository<Product, String> {
    @Query("SELECT o FROM Order o")
    Flux<Product> findAll(SpringDataWebProperties.Pageable pageable);

    @Query("{ 'deleted': false }")
    Flux<Product> findByDeleteFalse(Pageable pageable);

    @Query("{ 'title': { $regex: ?0, $options: 'i' }, 'deleted': false }")
    Flux<Product> findByNameContainingIgnoreCaseAndDeleteFalse(String name);

    @Query("{ 'category': ?0 }")
    Flux<Product> findProductByCategory(String category);

    Flux<Product> findByIdIn(Flux<String> ids);
}
