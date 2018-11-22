package ro.alex.spring5webfluxrest.controller;

import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ro.alex.spring5webfluxrest.domain.Vendor;
import ro.alex.spring5webfluxrest.repository.VendorRepository;


@RestController
public class VendorController {

    public static final String BASE_URL = "/api/v1/vendors";

    private final VendorRepository vendorRepository;

    public VendorController(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }

    @GetMapping(BASE_URL)
    Flux<Vendor> listAll() {return vendorRepository.findAll();}

    @GetMapping(BASE_URL + "/{id}")
    Mono<Vendor> getById(@PathVariable String id) {return vendorRepository.findById(id);}


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(BASE_URL)
    Mono<Void> create(@RequestBody Publisher<Vendor> vendorPublisher){
        return vendorRepository.saveAll(vendorPublisher).then();
    }

    @PutMapping(BASE_URL + "/{id}")
    Mono<Vendor> update(@PathVariable String id, @RequestBody Vendor vendor){
        vendor.setId(id);
        return vendorRepository.save(vendor);
    }

    @PatchMapping(BASE_URL + "/{id}")
    Mono<Vendor> patch(@PathVariable String id, @RequestBody Vendor vendor){

        Vendor foundVendor = vendorRepository.findById(id).block();

        if(!(foundVendor.getFirstName().equals(vendor.getFirstName())) ||
            !(foundVendor.getLastName().equals(vendor.getLastName())))
        {
            if(!(foundVendor.getFirstName().equals(vendor.getFirstName())) &&
            vendor.getFirstName() != null)
                foundVendor.setFirstName(vendor.getLastName());

            if(!(foundVendor.getLastName().equals(vendor.getLastName())) &&
                    vendor.getLastName() != null)
                foundVendor.setLastName(vendor.getLastName());

            return vendorRepository.save(foundVendor);
        }

        return Mono.just(foundVendor);
    }
}
