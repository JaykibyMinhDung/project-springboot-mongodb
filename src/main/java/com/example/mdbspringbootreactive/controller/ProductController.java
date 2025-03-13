package com.example.mdbspringbootreactive.controller;

import com.example.mdbspringbootreactive.config.ApiResponse;
import com.example.mdbspringbootreactive.model.Product;
import com.example.mdbspringbootreactive.repository.ProductRepository;
//import com.example.mdbspringbootreactive.service.TxnService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class ProductController {
    private final static Logger LOGGER = LoggerFactory.getLogger(ProductController.class);
    private final ProductRepository productRepository;
//    private final TxnService txnService;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
//        this.txnService = txnService;
    }

    private static void printLastLineStackTrace(String context) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        LOGGER.info("Stack trace's last line: " + stackTrace[stackTrace.length - 1].toString() + " from " + context);
    }

    @PostMapping("/products")
    public Mono<Product> createProduct(@RequestBody Product product) {
        printLastLineStackTrace("POST /product");
        return productRepository.save(product);
    }

    @GetMapping("/products")
    public Flux<Product> getProduct() {
        printLastLineStackTrace("GET /product/");
        return productRepository.findAll();
    }

    @GetMapping("/products/{id}")
    public Mono<Product> getDetailProduct(@PathVariable String id) {
        printLastLineStackTrace("GET /product/" + id);
//        return accountRepository.findByAccountNum(id).switchIfEmpty(Mono.error(new AccountNotFoundException()));
        return productRepository.findById(id);
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<ApiResponse>> updateProduct(@PathVariable String id, @RequestBody Product newProduct) {
        return productRepository.findById(id)
                .flatMap(existingProduct -> {
                    existingProduct.setTitle(newProduct.getTitle());
                    existingProduct.setPrice(newProduct.getPrice());

                    return productRepository.save(existingProduct)
                            .map(updatedProduct -> ResponseEntity.ok().body(
                                    new ApiResponse(HttpStatus.OK.value(), "Product updated successfully", updatedProduct)
                            ));
                })
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ApiResponse(HttpStatus.NOT_FOUND.value(), "Product not found", null)
                ));
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteProduct(@PathVariable String id) {
        printLastLineStackTrace("DELETE /product/" + id);
        return productRepository.deleteById(id);
    }
}
