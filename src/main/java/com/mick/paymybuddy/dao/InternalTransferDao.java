package com.mick.paymybuddy.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mick.paymybuddy.model.InternalTransfer;

public interface InternalTransferDao extends JpaRepository<InternalTransfer, Integer>{

}
