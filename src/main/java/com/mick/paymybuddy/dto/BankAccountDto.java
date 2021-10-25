package com.mick.paymybuddy.dto;

public class BankAccountDto {

	private String iban;
	private String bic;
	private String bankName;
	private String accountName;

	public BankAccountDto() {
	}

	public BankAccountDto(String iban, String bic, String bankName, String accountName) {
		this.iban = iban;
		this.bic = bic;
		this.bankName = bankName;
		this.accountName = accountName;
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
}
