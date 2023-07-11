package com.customerTransaction.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.customerTransaction.model.Customer;
import com.customerTransaction.model.Transaction;
import com.customerTransaction.repository.CustomerRepository;
import com.customerTransaction.utility.NotFoundException;

@Service
public class CustomerService {
	
	private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }
    
    public Optional<Customer> findById(Integer customerId) {
        return customerRepository.findById(customerId);
    }
    
    public Optional<Customer> getCustomerById(Integer customerId) {
        return customerRepository.findById(customerId);
    }
    
    public Customer updateCustomer(Customer customer) {
        Optional<Customer> optionalCustomer = customerRepository.findById(customer.getId());
        if (optionalCustomer.isPresent()) {
            Customer existingCustomer = optionalCustomer.get();
            // Update the properties of the existing customer
            existingCustomer.setName(customer.getName());
            existingCustomer.setIc(customer.getIc());

            List<Transaction> existingTransactions = existingCustomer.getTransactions();
            List<Transaction> newTransactions = customer.getTransactions();

            // Update existing transactions or add new transactions
            for (Transaction newTransaction : newTransactions) {
                boolean found = false;
                for (Transaction existingTransaction : existingTransactions) {
                    if (newTransaction.getId() == existingTransaction.getId()) {
                        // Update existing transaction properties
                        existingTransaction.setName(newTransaction.getName());
                        existingTransaction.setAmount(newTransaction.getAmount());
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    // Add new transaction
                    existingTransactions.add(newTransaction);
                }
            }

            // Save the updated customer back to the repository
            return customerRepository.save(existingCustomer);
        } else {
            throw new NotFoundException("Customer not found with ID: " + customer.getId());
        }
    }

    
    public void deleteCustomer(Integer customerId) {
        customerRepository.deleteById(customerId);
    }

    public Iterable<Customer> findAllCustomers() {
        return customerRepository.findAll();
    }
}
