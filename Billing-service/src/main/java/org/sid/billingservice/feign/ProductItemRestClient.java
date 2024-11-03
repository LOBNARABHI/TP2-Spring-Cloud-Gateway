package org.sid.billingservice.feign;

import org.sid.billingservice.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.PagedModel;
import org.springframework.data.web.config.SpringDataJacksonConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "IVENTORY-SERVICE")
public interface ProductItemRestClient {
    @GetMapping(path = "/products?page=0&size=10")
    PagedModel<Product> pageProducts();

    @GetMapping(path = "/products/{id}")
    Product getProductsById(@PathVariable Long id);
}
