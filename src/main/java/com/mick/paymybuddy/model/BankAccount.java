package com.mick.paymybuddy.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "bank_account")
public class BankAccount {

	@Id
	@Column(name = "iban", length = 34)
	private String iban;

	@Column(name = "bic")
	private String bic;

	@Column(name = "bankName")
	private String bankName;

	@Column(name = "accountName")
	private String accountName;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@OneToMany(mappedBy = "bankAccount", fetch = FetchType.EAGER)
	private List<ExternalTransfer> externalTransfers;

	public BankAccount() {
	}

	public BankAccount(String iban, String bic, String bankName, String accountName) {
		this.iban = iban;
		this.bic = bic;
		this.bankName = bankName;
		this.accountName = accountName;
	}

	public BankAccount(String iban, String bic, String bankName, String accountName, User user) {
		this.iban = iban;
		this.bic = bic;
		this.bankName = bankName;
		this.accountName = accountName;
		this.user = user;
	}

	public String getIban() {
		return iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}

	public String getBic() {
		return bic;
	}

	public void setBic(String bic) {
		this.bic = bic;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<ExternalTransfer> getExternalTransfers() {
		return externalTransfers;
	}

	public void setExternalTransfers(List<ExternalTransfer> externalTransfers) {
		this.externalTransfers = externalTransfers;
	}

}
