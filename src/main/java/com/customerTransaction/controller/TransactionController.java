package com.customerTransaction.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.customerTransaction.model.Customer;
import com.customerTransaction.model.Transaction;
import com.customerTransaction.service.TransactionService;


@RestController
@RequestMapping("/transactions")
public class TransactionController {
	
	private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<Transaction> createTransaction(@RequestBody Transaction transaction) {
        Transaction createdTransaction = transactionService.createTransaction(transaction);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTransaction);
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable Integer transactionId) {
        Transaction transaction = transactionService.getTransactionById(transactionId);
        if (transactionId == null){
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid input and ");
		}        
        return ResponseEntity.ok(transaction);
        
    }
    
    @GetMapping("/customers/{customerId}/transactions")
    public ResponseEntity<List<Transaction>> getAllTransactionsByCustomerId(@PathVariable Integer customerId) {
        List<Transaction> transactions = transactionService.getAllTransactionsByCustomerId(customerId);
        return ResponseEntity.ok(transactions);
    }


    @GetMapping("/{transactionId}/customer")
    public ResponseEntity<Customer> getTransactionCustomer(@PathVariable Integer transactionId) {
        Customer customer = transactionService.getTransactionCustomer(transactionId);
        return ResponseEntity.ok(customer);
    }
}

