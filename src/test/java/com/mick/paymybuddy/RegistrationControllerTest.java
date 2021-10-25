package com.mick.paymybuddy;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.sql.SQLException;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mick.paymybuddy.controller.RegistrationController;
import com.mick.paymybuddy.dto.UserRegistrationDto;
import com.mick.paymybuddy.model.User;
import com.mick.paymybuddy.service.UserService;

@SpringBootTest
@AutoConfigureMockMvc
public class RegistrationControllerTest {

	@Autowired
	MockMvc mockMvc;
	@Autowired
	RegistrationController registrationController;
	@MockBean
	UserService userService;
	@Mock
	UserRegistrationDto userRegistrationDto;

	@Mock
	RedirectAttributes redirectAttributes;

	@Test
	void showRegistrationForm() {
		when(userService.deleteBuddy(1)).thenReturn(true);
		String result = registrationController.showRegistrationForm();
		Assertions.assertThat(result).isEqualTo("registration");
	}

	@Test
	void registerUserAccount() throws SQLException {
		when(userService.save(any())).thenReturn(new User());
		String result = registrationController.registerUserAccount(userRegistrationDto);
		Assertions.assertThat(result).isEqualTo("redirect:/registration?success");
	}
}
