package com.customerTransaction.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "transaction")
@Entity
public class Transaction {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;	
	private int custId;
	private String name;
	private int amount;
	
	public Transaction() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Transaction(int id, int custid, String name, int amount) {		
		this.id = id;
		this.custId = custid;
		this.name = name;
		this.amount = amount;
	}

	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}	
	
	public int getCustId() {
		return custId;
	}

	public void setCustId(int custId) {
		this.custId = custId;
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		name = name;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		amount = amount;
	}
	

	@Override
	public String toString() {
		return "Transaction [Id=" + id + ", Custid=" + custId + ", Name=" + name + ", Amount=" + amount + "]";
	}
	
}


