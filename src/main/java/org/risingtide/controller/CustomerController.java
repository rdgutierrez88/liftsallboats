package org.risingtide.controller;

import org.risingtide.dto.customer.AddCustomerResponse;
import org.risingtide.dto.customer.GetCustomerResponse;
import org.risingtide.model.Customer;
import org.risingtide.repository.AccountRepository;
import org.risingtide.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/account")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AccountRepository accountRepository;

    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @PostMapping
    public ResponseEntity<AddCustomerResponse> addCustomer(@RequestBody Customer customer) {
        AddCustomerResponse responseBody = new AddCustomerResponse();

        try {
            Customer createdCustomer = customerRepository.save(customer);

            responseBody.setPhoneNumber(createdCustomer.getPhoneNumber());
            responseBody.setTransactionStatusCode(HttpStatus.CREATED.value());
            responseBody.setTransactionStatusDescription("Customer account created");

            return new ResponseEntity<>(responseBody, HttpStatus.CREATED);

        } catch (Exception e) {
            responseBody.setTransactionStatusCode(HttpStatus.BAD_REQUEST.value());
            responseBody.setTransactionStatusDescription(e.getMessage());

            return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{customerNumber}")
    public ResponseEntity<GetCustomerResponse> getCustomer(@PathVariable("customerNumber") String phoneNumber) {
        Optional<Customer> customer = customerRepository.findByPhoneNumber(phoneNumber);

        if (customer.isPresent()) {
            GetCustomerResponse responseBody = new GetCustomerResponse(customer.get());
            responseBody.setTransactionStatusCode(HttpStatus.FOUND.value());
            responseBody.setTransactionStatusDescription("Customer account found");

            return new ResponseEntity<>(responseBody, HttpStatus.FOUND);
        } else {
            GetCustomerResponse responseBody = new GetCustomerResponse();
            responseBody.setTransactionStatusCode(HttpStatus.NOT_FOUND.value());
            responseBody.setTransactionStatusDescription("Customer not found");

            return new ResponseEntity<>(responseBody, HttpStatus.NOT_FOUND);
        }
    }
}
