package com.customerTransaction.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.customerTransaction.model.Customer;
import com.customerTransaction.model.Transaction;


@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Integer> {

	List<Transaction> findAllByCustid(Integer customerId);

}
