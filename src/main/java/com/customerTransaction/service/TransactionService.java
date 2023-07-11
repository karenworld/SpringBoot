package com.customerTransaction.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.customerTransaction.model.Customer;
import com.customerTransaction.model.Transaction;
import com.customerTransaction.repository.CustomerRepository;
import com.customerTransaction.repository.TransactionRepository;
import com.customerTransaction.utility.*;

@Service
public class TransactionService {
	private final TransactionRepository transactionRepository;
	private final CustomerRepository customerRepository;

	public TransactionService(TransactionRepository transactionRepository, CustomerRepository customerRepository) {
        this.transactionRepository = transactionRepository;
        this.customerRepository = customerRepository;
    }
    

    public Transaction createTransaction(Transaction transaction) {    	
        return transactionRepository.save(transaction);
    }
    
    public Transaction getTransactionById(Integer transactionId) {
        Optional<Transaction> optionalTransaction = transactionRepository.findById(transactionId);
        
        if (optionalTransaction.isPresent()) {
            return optionalTransaction.get();
        } else {
            throw new NotFoundException("Transaction not found with ID: " + transactionId);
        }
    }
    
    public List<Transaction> getAllTransactionsByCustomerId(Integer customerId) {
        return transactionRepository.findAllByCustid(customerId);
    }
    
    
    public Customer getTransactionCustomer(Integer transactionId) {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new NotFoundException("Transaction not found with ID: " + transactionId));
        
        Integer customerId = transaction.getCustId();
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new NotFoundException("Customer not found with ID: " + customerId));
    }


	
	
}

