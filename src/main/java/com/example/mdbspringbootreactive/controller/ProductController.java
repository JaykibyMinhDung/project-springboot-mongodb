package com.example.mdbspringbootreactive.controller;

import com.example.mdbspringbootreactive.config.ApiResponse;
import com.example.mdbspringbootreactive.model.Product;
import com.example.mdbspringbootreactive.repository.ProductRepository;
//import com.example.mdbspringbootreactive.service.TxnService;
import com.example.mdbspringbootreactive.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final static Logger LOGGER = LoggerFactory.getLogger(ProductController.class);
//    private final ProductRepository productRepository;
//
//    public ProductController(ProductRepository productRepository) {
//        this.productRepository = productRepository;
//    }

    @Autowired
    private ProductService productService;

    private static void printLastLineStackTrace(String context) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        LOGGER.info("Stack trace's last line: " + stackTrace[stackTrace.length - 1].toString() + " from " + context);
    }

    @GetMapping("/high-rated")
    public Mono<ResponseEntity<ApiResponse<List<Product>>>> getHighRatedProducts() {
        return productService.getHighRatedProducts().collectList()
                .map(products ->
                        ResponseEntity.ok(
                                new ApiResponse<>(
                                        "Lấy danh sách thành công",
                                        HttpStatus.OK.value(),
                                        products
                                )
                        )
                )
                .onErrorResume(error -> {
                    LOGGER.error("Lỗi khi lấy danh sách: {}", error.getMessage());
                    // Trả về Mono<ResponseEntity<...>>
                    ApiResponse<List<Product>> res = new ApiResponse<>(
                            "Lỗi khi lấy danh sách",
                            HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            null
                    );
                    return Mono.just(
                            ResponseEntity
                                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                                    .body(res)
                    );
                });
    }

    @GetMapping("/category")
    public Mono<ResponseEntity<ApiResponse<List<Product>>>> getCategoryProducts(
            @RequestParam String category) {

        return productService.findProductByCategory(category) // Flux<Product>
                .collectList()                                   // Mono<List<Product>>
                .map(products ->
                        // Tạo ApiResponse có data = danh sách products
                        ResponseEntity.ok(
                                new ApiResponse<>(
                                        "Lấy danh sách thu mục thành công",
                                        HttpStatus.OK.value(),
                                        products
                                )
                        )
                )
                .onErrorResume(error -> {
                    LOGGER.error("Lỗi khi lấy sản phẩm: {}", error.getMessage());
                    // Trả về Mono<ResponseEntity<...>>
                    ApiResponse<List<Product>> res = new ApiResponse<>(
                            "Lỗi khi lấy sản phẩm",
                            HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            null
                    );
                    return Mono.just(
                            ResponseEntity
                                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                                    .body(res)
                    );
                });
    }

    @PostMapping
    public Mono<ResponseEntity<ApiResponse>> createProduct(@RequestBody Product product) {
        printLastLineStackTrace("POST /product");
        product.setCreatedDate(LocalDateTime.now());
//        product.setQuantity();
        return productService.save(product).then(Mono.just(ResponseEntity.ok().body(
                new ApiResponse("Thêm sản phẩm thành công", HttpStatus.OK.value())
        ))).onErrorResume(error -> {
            LOGGER.error("Lỗi khi thêm sản phẩm " + error.getMessage());
            ApiResponse<List<Product>> response = new ApiResponse<>("Thêm sản phẩm thất bại", 0, null);
            return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response));
        });
    }

    @GetMapping
    public Mono<ResponseEntity<ApiResponse<List<Product>>>> getProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String search) {
        printLastLineStackTrace("GET /products/");
        Pageable pageable = PageRequest.of(page, size);
        Flux<Product> productFlux;
        if (search != null && !search.isEmpty()) {
            productFlux = productService.findByNameContainingIgnoreCaseAndDeleteFalse(search).skip((long) page * size)
                    .take(size);
        } else {
            productFlux = productService.findByDeleteFalse(pageable);
        }
        return productFlux
                .collectList()
                .map(products -> {
                    ApiResponse<List<Product>> response = new ApiResponse<>("Lấy danh sách sản phẩm thành công", 1, products);
                    return ResponseEntity.ok().body(response);
                })
                .onErrorResume(error -> {
                    LOGGER.error("Lỗi khi lấy danh sách sản phẩm: " + error.getMessage());
                    ApiResponse<List<Product>> response = new ApiResponse<>("Lấy danh sách sản phẩm thất bại", 0, null);
                    return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response));
                });
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<ApiResponse<Product>>> getDetailProduct(@PathVariable String id) {
        printLastLineStackTrace("GET /product/" + id);
        return productService.findById(id)
                .map(orders -> {
                    ApiResponse<Product> response = new ApiResponse<>("Lấy sản phẩm thành công", 1, orders);
                    return ResponseEntity.ok().body(response);
                }).onErrorResume(error -> {
                    LOGGER.error("Lỗi khi lấy chi tiết sản phẩm: " + error.getMessage());
                    ApiResponse<Product> response = new ApiResponse<>("Lấy đơn hàng thất bại", 0, null);
                    return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response));
                });
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<ApiResponse>> updateProduct(@PathVariable String id, @RequestBody Product newProduct) {
        return productService.findById(id)
                .flatMap(existingProduct -> {
                    existingProduct.setTitle(newProduct.getTitle());
                    existingProduct.setPrice(newProduct.getPrice());
                    existingProduct.setCategory(newProduct.getCategory());
                    existingProduct.setDescription(newProduct.getDescription());
                    existingProduct.setImage(newProduct.getImage());
                    existingProduct.setLastModifiedDate(LocalDateTime.now());
                    if (newProduct.getIdRating() != null) {
                        existingProduct.getIdRating().addAll(newProduct.getIdRating());
                    }

                    return productService.save(existingProduct)
                            .map(updatedProduct -> ResponseEntity.ok().body(
                                    new ApiResponse("Sản phẩm cập nhật thành công", HttpStatus.OK.value(), updatedProduct)
                            ));
                })
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ApiResponse("Không tìm thấy sản phẩm", HttpStatus.NOT_FOUND.value(), null)
                ));
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<ApiResponse>> deleteProduct(@PathVariable String id) {
        printLastLineStackTrace("DELETE /product/" + id);
        return productService.findById(id)
                .flatMap(product -> {
                    product.setDelete(true);
                    return productService.save(product)
                            .then(Mono.just(ResponseEntity.ok().body(
                                    new ApiResponse("Sản phẩm đã xóa thành công", HttpStatus.OK.value())
                            )));
                })
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }
}
