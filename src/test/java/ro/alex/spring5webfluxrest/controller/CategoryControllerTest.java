package ro.alex.spring5webfluxrest.controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ro.alex.spring5webfluxrest.domain.Category;
import ro.alex.spring5webfluxrest.repository.CategoryRepository;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;

public class CategoryControllerTest {

    WebTestClient webTestClient;

    CategoryRepository categoryRepository;

    CategoryController categoryController;

    @Before
    public void setUp() throws Exception{
        categoryRepository = Mockito.mock(CategoryRepository.class);
        categoryController = new CategoryController(categoryRepository);
        webTestClient = WebTestClient.bindToController(categoryController).build();
    }

    @Test
    public void testList(){
        BDDMockito.given(categoryRepository.findAll())
                .willReturn(Flux.just(
                        Category.builder().description("Nuts").build(),
                        Category.builder().description("Fruit").build()
                ));

        webTestClient.get().uri(CategoryController.BASE_URL)
                .exchange()
                .expectBodyList(Category.class)
                .hasSize(2);
    }

    @Test
    public void testGetById(){
        BDDMockito.given(categoryRepository.findById(anyString()))
                .willReturn(Mono.just(
                        Category.builder().description("Nuts").build()
                ));

        webTestClient.get().uri(CategoryController.BASE_URL + "/anyString")
                .exchange()
                .expectBody(Category.class);
                //.value("$.description",equals("Nuts"));

    }

    @Test
    public void getById(){

    }

}