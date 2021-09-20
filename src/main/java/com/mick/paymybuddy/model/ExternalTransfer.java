package com.mick.paymybuddy.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "external_transfer")
@PrimaryKeyJoinColumn(name = "transfer_id") // PK de l'entité mere
public class ExternalTransfer extends Transfer {

	 	@Column(name = "fees")
	    private BigDecimal fees;

	    @ManyToOne
	    @JoinColumn(name = "bank_account_iban")
	    private BankAccount bankAccount;

	    public ExternalTransfer() {
	        super();
	    }

		public BigDecimal getFees() {
			return fees;
		}

		public void setFees(BigDecimal fees) {
			this.fees = fees;
		}

		public BankAccount getBankAccount() {
			return bankAccount;
		}

		public void setBankAccount(BankAccount bankAccount) {
			this.bankAccount = bankAccount;
		}

		public ExternalTransfer(BigDecimal fees, BankAccount bankAccount) {
			super();
			this.fees = fees;
			this.bankAccount = bankAccount;
		}
	
}
