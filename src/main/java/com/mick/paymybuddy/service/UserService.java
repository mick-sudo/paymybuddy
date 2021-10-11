package com.mick.paymybuddy.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.mick.paymybuddy.dto.BuddyFormDto;
import com.mick.paymybuddy.dto.UserRegistrationDto;
import com.mick.paymybuddy.model.Relation;
import com.mick.paymybuddy.model.User;

public interface UserService extends UserDetailsService {

	User save(UserRegistrationDto userRegistrationDto);
	UserDetails loadUserByUsername(String email);
	void addBuddy(BuddyFormDto buddyFormDto);
	List<Relation> listEmailRelation(String username);
	Boolean deleteBuddy(Integer id);
}
