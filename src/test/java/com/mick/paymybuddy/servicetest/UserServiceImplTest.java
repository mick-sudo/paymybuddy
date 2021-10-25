package com.mick.paymybuddy.servicetest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.mick.paymybuddy.dao.RelationDao;
import com.mick.paymybuddy.dao.RoleDao;
import com.mick.paymybuddy.dao.UserDao;
import com.mick.paymybuddy.dto.BuddyFormDto;
import com.mick.paymybuddy.dto.UserRegistrationDto;
import com.mick.paymybuddy.exception.DataNotFoundException;
import com.mick.paymybuddy.model.Relation;
import com.mick.paymybuddy.model.Role;
import com.mick.paymybuddy.model.User;
import com.mick.paymybuddy.service.UserServiceImpl;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserServiceImplTest {

	@Autowired
	UserServiceImpl userService;
	@MockBean
	UserDao userDao;
	@MockBean
	RelationDao relationDao;
	@MockBean
	RoleDao roleDao;

	User owner = new User(1, "barack", "obama", "a@a.com", "1234", BigDecimal.ZERO,
			Timestamp.valueOf(LocalDateTime.now()));
	User buddy = new User(2, "george", "bush", "b@b.com", "4321", BigDecimal.ZERO,
			Timestamp.valueOf(LocalDateTime.now()));

	Relation relation = new Relation(owner, buddy);
	Role role = new Role("USER");

	UserRegistrationDto userRegistrationDto = new UserRegistrationDto("barack", "obama", "a@a.com", "1234");

	@Test
	void listEmailRelation() {
		when(relationDao.findAllByOwner_Email(any())).thenReturn(Arrays.asList(relation));
		userService.listEmailRelation(any());
		verify(relationDao, times(1)).findAllByOwner_Email(any());
	}

	@Test
	void addBuddy() {
		BuddyFormDto buddyFormDto = new BuddyFormDto();
		buddyFormDto.setBuddy("mail1");
		buddyFormDto.setOwner("email2");
		try {
			when(userDao.findByEmail(buddyFormDto.getOwner())).thenReturn(owner);
			when(userDao.findByEmail(buddyFormDto.getBuddy())).thenReturn(buddy);
			when(userDao.save(owner)).thenReturn(owner);
			
			userService.addBuddy(buddyFormDto);
			verify(userDao, times(2)).findByEmail(any());
			verify(relationDao, times(0)).save(any());
			verify(userDao, times(1)).save(any());

		} catch (DataNotFoundException e) {
			assert (e.getMessage().contains("Cette personne n'existe pas"));
		}
	}

	@Test
	void deleteRelation() {
		when(relationDao.existsById(any())).thenReturn(true);
		userService.deleteBuddy(owner.getId());
		verify(relationDao, times(1)).deleteById(any());
	}

	@Test
	void save() throws SQLException {
		when(roleDao.findRoleByName("USER")).thenReturn(role);
		userService.save(userRegistrationDto);
		verify(roleDao, times(1)).findRoleByName(any());
	}
}
