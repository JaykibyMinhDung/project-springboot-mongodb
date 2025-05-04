package com.example.mdbspringbootreactive.repository;

import com.example.mdbspringbootreactive.model.Rating;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.List;

@Repository
public interface RatingRepository extends ReactiveMongoRepository<Rating, String> {
    Flux<Rating> findByProductId(String productId);

    Flux<Rating> findByRatingGreaterThan(int rating);
}
