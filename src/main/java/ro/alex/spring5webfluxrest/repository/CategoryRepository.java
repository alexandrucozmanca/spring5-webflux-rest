package ro.alex.spring5webfluxrest.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import ro.alex.spring5webfluxrest.domain.Category;

public interface CategoryRepository extends ReactiveMongoRepository<Category, String> {
}
