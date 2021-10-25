package com.mick.paymybuddy.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mick.paymybuddy.model.BankAccount;

public interface BankAccountDao extends JpaRepository<BankAccount, String>{

	List<BankAccount> findBankAccountsByUser_Email(String username);

	BankAccount findBankAccountByIbanAndUser_Email(String ibanUser, String emailUser);

	BankAccount findBankAccountByIban(String iban);
	

}
