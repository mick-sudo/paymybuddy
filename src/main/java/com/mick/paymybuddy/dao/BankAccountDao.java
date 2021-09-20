package com.mick.paymybuddy.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mick.paymybuddy.model.BankAccount;

public interface BankAccountDao extends JpaRepository<BankAccount, String>{

}
