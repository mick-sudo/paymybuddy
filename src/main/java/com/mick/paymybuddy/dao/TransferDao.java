package com.mick.paymybuddy.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mick.paymybuddy.model.Transfer;

public interface TransferDao extends JpaRepository<Transfer, Integer>{

}
