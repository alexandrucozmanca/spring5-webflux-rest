package ro.alex.spring5webfluxrest.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import ro.alex.spring5webfluxrest.domain.Vendor;

public interface VendorRepository extends ReactiveMongoRepository <Vendor, String> {
}
