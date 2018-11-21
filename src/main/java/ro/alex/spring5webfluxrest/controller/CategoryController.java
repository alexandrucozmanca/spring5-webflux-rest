package ro.alex.spring5webfluxrest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ro.alex.spring5webfluxrest.domain.Category;
import ro.alex.spring5webfluxrest.repository.CategoryRepository;

import java.util.Map;

@RestController
public class CategoryController {

    public static final String BASE_URL = "/api/v1/categories";

    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    @GetMapping(BASE_URL)
    Flux<Category> list() {
        return categoryRepository.findAll();
    }

    @GetMapping(BASE_URL + "/{id}")
    Mono<Category> getById(@PathVariable String id){
        return categoryRepository.findById(id);
    }

}
