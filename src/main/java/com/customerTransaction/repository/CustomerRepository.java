package com.customerTransaction.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.customerTransaction.model.Customer;
import com.customerTransaction.model.Transaction;

//Create a new customer:
//    POST /api/customers
//
//Retrieve a customer by ID:
//    GET /api/customers/{id}
//
//Update a customer by ID:
//    PUT /api/customers/{id}
//
//Delete a customer by ID:
//    DELETE /api/customers/{id}

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Integer> {

	 //no need below as this is already provided by CrudRepository
//	    Customer save(Customer customer);
//	    
//	    Optional<Customer> findById(String id);
//	    
//	    List<Customer> findAll();
//	    
//	    void update(Customer customer);
//	    
//	    void deleteById(String id);
//	    
	
	BigDecimal getBalanceById(Integer customerId);
    List<Transaction> getTransactionsById(Integer customerId);
	}
