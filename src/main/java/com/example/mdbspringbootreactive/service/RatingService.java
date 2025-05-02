package com.example.mdbspringbootreactive.service;

import com.example.mdbspringbootreactive.model.Rating;
import com.example.mdbspringbootreactive.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class RatingService {
    @Autowired
    private RatingRepository repository;

    public List<Rating> getRatingsByProductId(String productId) {
        return repository.findByProductId(productId);
    }

    public Mono<Rating> addRating(Rating rating) {
        return repository.save(rating);
    }
}
