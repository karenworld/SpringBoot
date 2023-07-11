package com.customerTransaction.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import com.customerTransaction.model.Customer;
import com.customerTransaction.service.CustomerService;
import com.customerTransaction.utility.NotFoundException;


@RestController
@RequestMapping("/customers")
public class CustomerController {
	
	private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
    	System.out.println("POST ENDPOINT");
    	if (customer == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid input and ");
		}
    	Customer createdCustomer = customerService.createCustomer(customer);
        return new ResponseEntity<>(createdCustomer, HttpStatus.CREATED);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Integer customerId) {
        Optional<Customer> customer = customerService.findById(customerId);
        if (customer.isPresent()) {
            return new ResponseEntity<>(customer.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping
    public ResponseEntity<Iterable<Customer>> getAllCustomers() {
        Iterable<Customer> customers = customerService.findAllCustomers();
        return ResponseEntity.ok(customers);
    }
    
    
    @PutMapping("/{customerId}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Integer customerId, @RequestBody Customer customer) {
    	customer.setId(customerId);
    	try {
            Customer savedCustomer = customerService.updateCustomer(customer);
            return ResponseEntity.ok(savedCustomer);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
       

    @DeleteMapping("/{customerId}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Integer customerId) {
        customerService.deleteCustomer(customerId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
