package org.sid.billingservice.web;

import org.sid.billingservice.entities.Bill;
import org.sid.billingservice.feign.CustomerRestClient;
import org.sid.billingservice.feign.ProductItemRestClient;
import org.sid.billingservice.model.Customer;
import org.sid.billingservice.model.Product;
import org.sid.billingservice.repository.BillRepository;
import org.sid.billingservice.repository.ProductItemRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BillingRestController {
    private BillRepository billRepository;
    private ProductItemRepository productItemRepository;
    private CustomerRestClient customerRestClient;
    private ProductItemRestClient productItemRestClient;
    public BillingRestController(BillRepository billRepository, ProductItemRepository productItemRepository, CustomerRestClient customerRestClient, ProductItemRestClient productItemRestClient) {
        this.billRepository = billRepository;
        this.productItemRepository = productItemRepository;
        this.customerRestClient = customerRestClient;
        this.productItemRestClient = productItemRestClient;
    }

    @GetMapping(path = "/fullBill/{id}")
    public Bill getBill(@PathVariable(name = "id") Long id){
        Bill bill = billRepository.findById(id).get();
        Customer customer=customerRestClient.getCustomerById(bill.getCustomerId());
        bill.setCustomer(customer);
        bill.getProductItems().forEach(pi->{
            Product product=productItemRestClient.getProductsById(pi.getProductID());
            pi.setProduct(product);
        });
        return  bill;
    }

    // Nouvel endpoint pour récupérer les factures d'un client spécifique
    @GetMapping(path = "/bills/customer/{customerId}")
    public List<Bill> getBillsByCustomerId(@PathVariable(name = "customerId") Long customerId) {
        List<Bill> bills = billRepository.findByCustomerId(customerId);

        // Enrichir chaque facture avec les informations du client et des produits
        bills.forEach(bill -> {
            Customer customer = customerRestClient.getCustomerById(bill.getCustomerId());
            bill.setCustomer(customer);
            bill.getProductItems().forEach(pi -> {
                Product product = productItemRestClient.getProductsById(pi.getProductID());
                pi.setProduct(product);
            });
        });

        return bills;
    }

}
