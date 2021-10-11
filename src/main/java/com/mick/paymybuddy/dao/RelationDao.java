package com.mick.paymybuddy.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mick.paymybuddy.model.Relation;

public interface RelationDao extends JpaRepository<Relation, Integer>{

	List<Relation> findAllByOwner_Email(String emailOwner);
	Relation findByOwner_EmailAndBuddy_Email(String emailSender, String Receiver);

}
