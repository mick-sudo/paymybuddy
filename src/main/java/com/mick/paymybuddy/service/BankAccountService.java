package com.mick.paymybuddy.service;

import java.sql.SQLException;
import java.util.List;

import com.mick.paymybuddy.dto.BankAccountDto;
import com.mick.paymybuddy.model.BankAccount;

public interface BankAccountService {

	List<BankAccount> findBankAccountByUser(String username);

	BankAccount addBankAccount(String emailOwner, BankAccountDto bankAccountDto) throws SQLException;

	Boolean deleteBankAccount(String iban);

}
