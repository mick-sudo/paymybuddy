package com.mick.paymybuddy.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.mick.paymybuddy.dto.UserRegistrationDto;
import com.mick.paymybuddy.model.User;

public interface UserService extends UserDetailsService {

	User save(UserRegistrationDto userRegistrationDto);
}
