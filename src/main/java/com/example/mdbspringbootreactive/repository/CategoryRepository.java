package com.example.mdbspringbootreactive.repository;

import com.example.mdbspringbootreactive.model.Category;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends ReactiveMongoRepository<Category, String> {

}
