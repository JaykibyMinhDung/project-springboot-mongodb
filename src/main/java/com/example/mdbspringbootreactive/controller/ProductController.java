package com.example.mdbspringbootreactive.controller;

import com.example.mdbspringbootreactive.model.Product;
import com.example.mdbspringbootreactive.repository.ProductRepository;
import com.example.mdbspringbootreactive.service.TxnService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class ProductController {
    private final static Logger LOGGER = LoggerFactory.getLogger(ProductController.class);
    private final ProductRepository productRepository;
    private final TxnService txnService;

    public ProductController(ProductRepository productRepository, TxnService txnService) {
        this.productRepository = productRepository;
        this.txnService = txnService;
    }

    private static void printLastLineStackTrace(String context) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        LOGGER.info("Stack trace's last line: " + stackTrace[stackTrace.length - 1].toString() + " from " + context);
    }

    @PostMapping("/post-product")
    public Mono<Product> createProduct(@RequestBody Product product) {
        printLastLineStackTrace("POST /product");
        return productRepository.save(product);
    }

    @GetMapping("/get-product")
    public Flux<Product> getProduct(@RequestBody String accountNum) {
        printLastLineStackTrace("GET /product/" + accountNum);
        return productRepository.findAll();
    }
}
