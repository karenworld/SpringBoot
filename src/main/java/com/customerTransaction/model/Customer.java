package com.customerTransaction.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "customer")
@Entity
public class Customer {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	private String ic;
	private List<Transaction> transactions;

	public Customer() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Customer(Integer id, String name, String ic, List<Transaction> transactions) {
		super();
		this.id = id;
		this.name = name;
		this.ic = ic;
		this.transactions = transactions;
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		name = name;
	}

	public String getIc() {
		return ic;
	}

	public void setIc(String ic) {
		ic = ic;
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}

	@Override
	public String toString() {
		return "Customer [Id=" + id + ", Name=" + name + ", Ic=" + ic + ", transactions=" + transactions + "]";
	}
}
