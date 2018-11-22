package ro.alex.spring5webfluxrest.controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.reactivestreams.Publisher;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ro.alex.spring5webfluxrest.domain.Vendor;
import ro.alex.spring5webfluxrest.repository.VendorRepository;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

public class VendorControllerTest {

    WebTestClient webTestClient;

    VendorRepository vendorRepository;

    VendorController vendorController;

    @Before
    public void setUp() throws Exception{
        vendorRepository = Mockito.mock(VendorRepository.class);
        vendorController = new VendorController(vendorRepository);
        webTestClient = WebTestClient.bindToController(vendorController).build();
    }

    @Test
    public void testListAll(){
        BDDMockito.given(vendorRepository.findAll())
                .willReturn(Flux.just(
                        Vendor.builder()
                                .firstName("Amazon")
                                .lastName("Inc")
                        .build(),
                        Vendor.builder()
                                .firstName("Virgin")
                                .lastName("LTD")
                        .build()
                ));

        webTestClient.get().uri(VendorController.BASE_URL)
                .exchange()
                .expectBodyList(Vendor.class)
                .hasSize(2);
    }

    @Test
    public void testGetById(){
        BDDMockito.given(vendorRepository.findById(anyString()))
                .willReturn(Mono.just(
                        Vendor.builder()
                                .firstName("Amazon")
                                .lastName("Inc")
                                .build()
                ));

        webTestClient.get().uri(VendorController.BASE_URL + "/anyString")
                .exchange()
                .expectBody(Vendor.class);

    }

    @Test
    public void testCreate(){
        BDDMockito.given(vendorRepository.saveAll(any(Publisher.class)))
                .willReturn(Flux.just(Vendor.builder().build()));

        Mono<Vendor> vendorToSaveMono =
                Mono.just(Vendor.builder()
                        .firstName("Amazon")
                        .lastName("Inc").build());

        webTestClient.post().uri(VendorController.BASE_URL)
                .body(vendorToSaveMono, Vendor.class)
                .exchange()
                .expectStatus()
                .isCreated();
    }

    @Test
    public void testUpdate(){
        BDDMockito.given(vendorRepository.save(any(Vendor.class)))
                .willReturn(Mono.just(Vendor.builder().build()));

        Mono<Vendor> vendorToSaveMono =
                Mono.just(Vendor.builder()
                        .firstName("Amazon")
                        .lastName("Inc").build());

        webTestClient.put().uri(VendorController.BASE_URL + "/id")
                .body(vendorToSaveMono, Vendor.class)
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    public void testPatchWithChanges(){
        BDDMockito.given(vendorRepository.findById(anyString()))
                .willReturn(Mono.just(Vendor.builder().lastName("Virgin").firstName("Inc").build()));

        BDDMockito.given(vendorRepository.save(any(Vendor.class)))
                .willReturn(Mono.just(Vendor.builder().build()));

        Mono<Vendor> categoryToSaveMono = Mono.just(Vendor.builder().lastName("Amazon").firstName("Ltd").build());

        webTestClient.patch().uri(VendorController.BASE_URL + "/id")
                .body(categoryToSaveMono, Vendor.class)
                .exchange()
                .expectStatus()
                .isOk();

        BDDMockito.verify(vendorRepository).save(any());
    }

    @Test
    public void testPatchWithNoChanges(){
        BDDMockito.given(vendorRepository.findById(anyString()))
                .willReturn(Mono.just(Vendor.builder().lastName("Virgin").firstName("Inc").build()));

        BDDMockito.given(vendorRepository.save(any(Vendor.class)))
                .willReturn(Mono.just(Vendor.builder().build()));

        Mono<Vendor> categoryToSaveMono = Mono.just(Vendor.builder().lastName("Virgin").firstName("Inc").build());

        webTestClient.patch().uri(VendorController.BASE_URL + "/id")
                .body(categoryToSaveMono, Vendor.class)
                .exchange()
                .expectStatus()
                .isOk();

        BDDMockito.verify(vendorRepository, Mockito.never()).save(any());
    }


}