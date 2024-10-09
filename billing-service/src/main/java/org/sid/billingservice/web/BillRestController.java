package org.sid.billingservice.web;

import org.sid.billingservice.entities.Bill;
import org.sid.billingservice.repository.BillRepository;
import org.sid.billingservice.repository.ProductItemRepository;
import org.sid.billingservice.services.CustomerServiceClient;
import org.sid.billingservice.services.InventoryServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BillRestController {

    @Autowired
    private BillRepository billRepository;
    @Autowired
    private ProductItemRepository productItemRepository;
    @Autowired
    private CustomerServiceClient customerServiceClient;
    @Autowired
    private InventoryServiceClient inventoryServiceClient;

    @GetMapping("/bills/full/{id}")
    public Bill getBill(@PathVariable(name="id") Long id){
            Bill bill=billRepository.findById(id).get();
            bill.setCustomer(customerServiceClient.findCustomerById(bill.getCustomerId()));
            bill.getProductItems().forEach(pi->{
                pi.setProduct(inventoryServiceClient.findProductById(pi.getProductId()));
            });
            return bill;
    }
}
