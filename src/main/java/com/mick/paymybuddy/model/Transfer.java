package com.mick.paymybuddy.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name = "transfer")
@Inheritance(strategy = InheritanceType.JOINED)//pk partagé avec les 2 filles(internal et external)
public class Transfer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "amount")
	private BigDecimal amount;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "transactionDate")
	private Date transactionDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public Transfer() {
		super();
	}

	public Transfer(Integer id, BigDecimal amount, String description, Date transactionDate) {
		super();
		this.id = id;
		this.amount = amount;
		this.description = description;
		this.transactionDate = transactionDate;
	}

	
}
