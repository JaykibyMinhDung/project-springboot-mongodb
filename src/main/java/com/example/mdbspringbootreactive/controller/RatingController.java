package com.example.mdbspringbootreactive.controller;

import com.example.mdbspringbootreactive.model.Rating;
import com.example.mdbspringbootreactive.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {
    @Autowired
    private RatingService service;

    private final static Logger LOGGER = LoggerFactory.getLogger(RatingController.class);

    @PostMapping("/test")
    public ResponseEntity<String> testPost() {
        String response = "hello";
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{productId}")
    public Mono<ResponseEntity<List<Rating>>> getRatings(@PathVariable String productId) {
        LOGGER.info("Nhận yêu cầu lấy rating cho sản phẩm ID: {}", productId);
        return service.getRatingsByProductId(productId)
                .collectList()
                .map(ratings -> {
                    LOGGER.debug("Trả về {} rating cho sản phẩm ID: {}", ratings.size(), productId);
                    return ResponseEntity.ok(ratings);
                })
                .doOnError(e -> LOGGER.error("Lỗi khi lấy rating cho sản phẩm ID: {}. Chi tiết: {}", productId, e.getMessage(), e))
                .onErrorResume(e -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(Collections.emptyList())));
    }

    @PostMapping
    public Mono<ResponseEntity<Rating>> addRating(@RequestBody Rating rating) {
        LOGGER.info("Nhận yêu cầu thêm rating: {}", rating);
        return service.addRating(rating)
                .map(savedRating -> {
                    LOGGER.info("Đã lưu rating thành công: {}", savedRating);
                    return new ResponseEntity<>(savedRating, HttpStatus.CREATED);
                })
                .doOnError(error -> {
                    LOGGER.error("Lỗi khi lưu rating: {}. Chi tiết: {}", rating, error.getMessage(), error);
                })
                .onErrorResume(error -> Mono.just(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR)));
    }
}
