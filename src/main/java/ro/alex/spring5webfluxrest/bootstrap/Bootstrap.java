package ro.alex.spring5webfluxrest.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;
import ro.alex.spring5webfluxrest.domain.Category;
import ro.alex.spring5webfluxrest.domain.Vendor;
import ro.alex.spring5webfluxrest.repository.CategoryRepository;
import ro.alex.spring5webfluxrest.repository.VendorRepository;

@Controller
public class Bootstrap implements CommandLineRunner {

    private final CategoryRepository categoryRepository;

//    private final CustomerRepository customerRepository;

    private final VendorRepository vendorRepository;

    public Bootstrap(CategoryRepository categoryRepository, VendorRepository vendorRepository) {
        this.categoryRepository = categoryRepository;
//        this.customerRepository = customerRepository;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        if(categoryRepository.count().block() == 0)
            loadCategories();

//        if(customerRepository.count().block() == 0)
//            loadCustomers();

        if(vendorRepository.count().block() == 0)
            loadVendors();
    }

    private void loadCategories(){

        categoryRepository.save(Category.builder()
                .description("Fruit").build());
        categoryRepository.save(Category.builder()
                .description("Nuts").build());
        categoryRepository.save(Category.builder()
                .description("Meats").build());
        categoryRepository.save(Category.builder()
                .description("Eggs").build());
        categoryRepository.save(Category.builder()
                .description("Breads").build());


        System.out.println("Category data loaded = " + categoryRepository.count());
    }

//    private void loadCustomers(){
//        Customer customer1 = new Customer();
//        customer1.setId(1L);
//        customer1.setFirstName("Michael");
//        customer1.setLastName("Weston");
//        customerRepository.save(customer1).block;
//
//        Customer customer2 = new Customer();
//        customer2.setId(2L);
//        customer2.setFirstName("Sam");
//        customer2.setLastName("Axe");
//        customerRepository.save(customer2).block;
//
//        System.out.println("Customer data loaded = " + customerRepository.count());
//    }

    private void loadVendors(){
        Vendor vendor1 = new Vendor();
        //vendor1.setId(1L);
        vendor1.setFirstName("Amazon");
        vendor1.setLastName("Inc");
        vendorRepository.save(vendor1).block();


        Vendor vendor2 = new Vendor();
      //  vendor2.setId(2L);
        vendor2.setFirstName("Virgin");
        vendor2.setLastName("Inc");
        vendorRepository.save(vendor2).block();

        System.out.println("Vendor data loaded = " + vendorRepository.count());
    }
}
