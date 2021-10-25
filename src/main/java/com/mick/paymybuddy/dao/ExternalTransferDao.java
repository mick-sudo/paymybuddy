package com.mick.paymybuddy.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mick.paymybuddy.model.ExternalTransfer;

public interface ExternalTransferDao extends JpaRepository<ExternalTransfer, Integer>{

	ExternalTransfer[] findAllByBankAccount_User_EmailOrderByTransactionDateDesc(String emailOwner);

}
