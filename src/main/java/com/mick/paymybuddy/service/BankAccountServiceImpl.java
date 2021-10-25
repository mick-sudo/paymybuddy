package com.mick.paymybuddy.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.mick.paymybuddy.dao.BankAccountDao;
import com.mick.paymybuddy.dao.UserDao;
import com.mick.paymybuddy.dto.BankAccountDto;
import com.mick.paymybuddy.exception.DataAlreadyExistException;
import com.mick.paymybuddy.exception.DataMissingException;
import com.mick.paymybuddy.model.BankAccount;
import com.mick.paymybuddy.model.User;

@Service 
public class BankAccountServiceImpl implements BankAccountService{

	@Autowired
	private BankAccountDao bankAccountDao;
	
	@Autowired
	private UserDao userDao;
	
	@Override
	public List<BankAccount> findBankAccountByUser(String username) {
		
		return bankAccountDao.findBankAccountsByUser_Email(username);
	}
	
	@Override
    public BankAccount addBankAccount(String emailOwner, BankAccountDto bankAccountDto) throws SQLException {
        if (bankAccountDto.getIban().isBlank()) {
            throw new DataMissingException("L'iban ne peut pas être vide !!");
        }

        User user = userDao.findByEmail(emailOwner);

        String iban = bankAccountDto.getIban();
        BankAccount bankAccount = bankAccountDao.findBankAccountByIban(iban);

        if (bankAccount == null) {
            bankAccount = new BankAccount();
            bankAccount.setIban(bankAccountDto.getIban());
            bankAccount.setBic(bankAccountDto.getBic());
            bankAccount.setBankName(bankAccountDto.getBankName());
            bankAccount.setAccountName(bankAccountDto.getAccountName());
            bankAccount.setUser(user);

            try {
                bankAccountDao.save(bankAccount);
            } catch (Exception e) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                throw new SQLException("Probleme save bank");
            }
            return bankAccount;
        } else if (bankAccount.getUser().equals(user)) {
            throw new DataAlreadyExistException("Vous possedez deja ce compte bancaire !");
        } else {
            throw new DataAlreadyExistException("Ce compte bancaire appartient à un autre utilisateur !");
        }
    }

	@Override
	public Boolean deleteBankAccount(String iban) {
        if (bankAccountDao.existsById(iban)) {
            bankAccountDao.deleteById(iban);
            return true;
        }
        return false;
	}
	

}
