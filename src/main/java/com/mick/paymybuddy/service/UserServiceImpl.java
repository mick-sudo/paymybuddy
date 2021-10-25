package com.mick.paymybuddy.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.mick.paymybuddy.dao.RelationDao;
import com.mick.paymybuddy.dao.RoleDao;
import com.mick.paymybuddy.dao.UserDao;
import com.mick.paymybuddy.dto.BuddyFormDto;
import com.mick.paymybuddy.dto.UserRegistrationDto;
import com.mick.paymybuddy.exception.DataAlreadyExistException;
import com.mick.paymybuddy.model.Relation;
import com.mick.paymybuddy.model.Role;
import com.mick.paymybuddy.model.User;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private RelationDao relationDao;
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
	@Transactional
	public User addBuddy(BuddyFormDto buddyFormDto) {
		User owner = userDao.findByEmail(buddyFormDto.getOwner());
		
		// Si le owner a des relations
		if(owner.getRelations() != null && !owner.getRelations().isEmpty()) {
			// alors verifier si la relation existe deja
			if(owner.getRelations().stream().anyMatch(relation -> relation.getBuddy().getEmail().equals(buddyFormDto.getBuddy()))) {
				// Alors envoyer exception
				throw new DataAlreadyExistException("buddy already exist");
			}
		}
		
		User buddy = userDao.findByEmail(buddyFormDto.getBuddy());
		Relation relation = new Relation();
		relation.setOwner(owner);
		relation.setBuddy(buddy);
		
		if(owner.getRelations() == null) {
			owner.setRelations(new ArrayList<>());
		}
		
		owner.getRelations().add(relation);
		return userDao.save(owner);
		
	}

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userDao.findByEmail(email);
        if(user == null){
            throw new UsernameNotFoundException("Invalid username or password.");
        }

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
	}
	
	// On map nos roles avec ceux de spring et on leur attribut l'autorité correspondantes
    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){
        System.out.println(roles);
    	return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

	@Override
	public List<Relation> listEmailRelation(String emailOwner) {
		return relationDao.findAllByOwner_Email(emailOwner);
	}

	@Override
	public Boolean deleteBuddy(Integer id) {
		if(relationDao.existsById(id)) {
			relationDao.deleteById(id);
			return true;
		}
		return false;
	}

}
