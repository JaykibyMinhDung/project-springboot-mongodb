package com.example.mdbspringbootreactive.service;

import com.example.mdbspringbootreactive.model.Category;
import com.example.mdbspringbootreactive.model.Product;
import com.example.mdbspringbootreactive.model.Rating;
import com.example.mdbspringbootreactive.repository.ProductRepository;
import com.example.mdbspringbootreactive.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductService  {
    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private ProductRepository productRepository;

    public Flux<Product> getHighRatedProducts() {
        return ratingRepository.findByRatingGreaterThan(3)
                .map(Rating::getProductId)
                .distinct()
                .collectList()
                .flatMapMany(productIds -> productRepository.findByIdIn(Flux.fromIterable(productIds)));
    }

    public Mono<Product> save(Product product) {
        return productRepository.save(product);
    }

    public Flux<Product> findByNameContainingIgnoreCaseAndDeleteFalse(String name) {
        return productRepository.findByNameContainingIgnoreCaseAndDeleteFalse(name);
    }

    public Flux<Product> findByDeleteFalse(Pageable pageable) {
        return productRepository.findByDeleteFalse(pageable);
    }

    public Mono<Product> findById(String id) {
        return productRepository.findById(id);
    }

    public Flux<Product> findProductByCategory(String category) {
        return productRepository.findProductByCategory(category);
    }

    public Mono<Product> updateProduct(Product product) {
        return productRepository.findById(product.getId())
                .flatMap(existingProduct -> {
                    existingProduct.setTitle(product.getTitle());
                    existingProduct.setDescription(product.getDescription());
                    existingProduct.setPrice(product.getPrice());
                    return productRepository.save(existingProduct);
                })
                .switchIfEmpty(Mono.error(new RuntimeException("Product not found")));
    }
}
