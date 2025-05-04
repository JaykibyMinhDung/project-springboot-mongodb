package com.example.mdbspringbootreactive.controller;

import com.example.mdbspringbootreactive.config.ApiResponse;
import com.example.mdbspringbootreactive.model.Category;
import com.example.mdbspringbootreactive.model.Product;
import com.example.mdbspringbootreactive.repository.CategoryRepository;
import com.example.mdbspringbootreactive.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    @Autowired
    private final static Logger LOGGER = LoggerFactory.getLogger(ProductController.class);
    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    private static void printLastLineStackTrace(String context) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        LOGGER.info("Stack trace's last line: " + stackTrace[stackTrace.length - 1].toString() + " from " + context);
    }

    @GetMapping
    public Mono<ResponseEntity<ApiResponse<List<Category>>>> findAll() {
        return categoryRepository.findAll().collectList()
                .map(categories -> {
                    ApiResponse<List<Category>> response = new ApiResponse<>("Lấy danh mục sách thành công", 1, categories);
                    return ResponseEntity.ok().body(response);
                })
                .onErrorResume(error -> {
                    LOGGER.error("Lỗi khi lấy danh mục sách: " + error.getMessage());
                    ApiResponse<List<Category>> response = new ApiResponse<>("Lấy danh mục sách thất bại", 0, null);
                    return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response));
                });
    }
}
