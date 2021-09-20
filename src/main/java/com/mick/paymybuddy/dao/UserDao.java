package com.mick.paymybuddy.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mick.paymybuddy.model.User;

public interface UserDao extends JpaRepository<User, Integer>{

}
