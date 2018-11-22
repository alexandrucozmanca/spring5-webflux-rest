package ro.alex.spring5webfluxrest.controller;

import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ro.alex.spring5webfluxrest.domain.Category;
import ro.alex.spring5webfluxrest.repository.CategoryRepository;

@RestController
public class CategoryController {

    public static final String BASE_URL = "/api/v1/categories";

    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    @GetMapping(BASE_URL)
    Flux<Category> listAll() {
        return categoryRepository.findAll();
    }

    @GetMapping(BASE_URL + "/{id}")
    Mono<Category> getById(@PathVariable String id){
        return categoryRepository.findById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(BASE_URL)
    Mono<Void> create(@RequestBody  Publisher<Category> categoryPublisher){
        return categoryRepository.saveAll(categoryPublisher).then();
    }

    @PutMapping(BASE_URL + "/{id}")
    Mono<Category> update(@PathVariable String id, @RequestBody Category category){
        category.setId(id);
        return categoryRepository.save(category);
    }

    @PatchMapping(BASE_URL + "/{id}")
    Mono<Category> patch(@PathVariable String id, @RequestBody Category category){

        Category foundCategory = categoryRepository.findById(id).block();

        if(!(foundCategory.getDescription().equals(category.getDescription()))){
            foundCategory.setDescription(category.getDescription());
            return  categoryRepository.save(foundCategory);
        }

        return Mono.just(foundCategory);
    }
}
