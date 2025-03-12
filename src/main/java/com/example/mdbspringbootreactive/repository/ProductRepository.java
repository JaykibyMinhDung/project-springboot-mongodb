package com.example.mdbspringbootreactive.repository;

import com.example.mdbspringbootreactive.model.Account;
import com.example.mdbspringbootreactive.model.Product;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Update;
import reactor.core.publisher.Mono;

public interface ProductRepository extends ReactiveMongoRepository<Product, String> {
//    @Query("{accountNum:'?0'}")
//    Mono<Product> findByAccountNum(String accountNum);

//    @Update("{'$inc':{'balance': ?1}}")
//    Mono<Long> findAndIncrementBalanceByAccountNum(String accountNum, double increment);

}
