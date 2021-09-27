package com.mick.paymybuddy.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mick.paymybuddy.model.User;

@Repository
public interface UserDao extends JpaRepository<User, Integer>{

	User findByEmail(String email);
}
