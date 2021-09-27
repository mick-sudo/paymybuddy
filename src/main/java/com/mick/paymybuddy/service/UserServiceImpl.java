package com.mick.paymybuddy.service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.mick.paymybuddy.dao.RoleDao;
import com.mick.paymybuddy.dao.UserDao;
import com.mick.paymybuddy.dto.UserRegistrationDto;
import com.mick.paymybuddy.model.Role;
import com.mick.paymybuddy.model.User;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;
	@Autowired
	private RoleDao roleDao;
	@Autowired
	static BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
	

	@Override
	public User save(UserRegistrationDto userRegistrationDto) {
		Role role = roleDao.findRoleByName("USER");
		User user = new User(userRegistrationDto.getFirstname(), userRegistrationDto.getLastname(),
				userRegistrationDto.getEmail(), encoder.encode(userRegistrationDto.getPassword()), BigDecimal.ZERO,
				new Date(), Arrays.asList(role));
		return userDao.save(user);
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userDao.findByEmail(email);
        if(user == null){
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
	}
	
	// On map nos roles avec ceux de spring et on leur attribut l'autorité correspondantes
    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

}
