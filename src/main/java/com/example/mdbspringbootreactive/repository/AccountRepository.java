package com.example.mdbspringbootreactive.repository;

import com.example.mdbspringbootreactive.model.Account;
//import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
//import org.springframework.data.mongodb.repository.Update;
//import reactor.core.publisher.Mono;

@Repository
public interface AccountRepository extends ReactiveMongoRepository<Account, String> {
//    @Query("{accountNum:'?0'}")
//    Mono<Account> findByAccountNum(String accountNum);
//
//    @Update("{'$inc':{'balance': ?1}}")
//    Mono<Long> findAndIncrementBalanceByAccountNum(String accountNum, double increment);

}
