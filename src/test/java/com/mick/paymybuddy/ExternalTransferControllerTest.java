package com.mick.paymybuddy;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mick.paymybuddy.controller.ExternalTransferController;
import com.mick.paymybuddy.dto.BankAccountDto;
import com.mick.paymybuddy.dto.ExternalTransferDto;
import com.mick.paymybuddy.exception.DataAlreadyExistException;
import com.mick.paymybuddy.service.BankAccountService;
import com.mick.paymybuddy.service.TransferService;

import java.sql.SQLException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
class ExternalTransferControllerTest {

	@Autowired
	ExternalTransferController externalTransferController;
	@Mock
	BankAccountService bankAccountService;
	@Mock
	UserDetails userDetails;
	@Mock
	TransferService transferService;
	@Mock
	ExternalTransferDto externalTransferDto;
	@Mock
	Model model;
	@Mock
	BankAccountDto bankAccountDto;

	@Test
	void deleteBankAccount() {
		when(bankAccountService.deleteBankAccount("test")).thenReturn(true);
		String result = externalTransferController.deteleBankAccount("test");
		Assertions.assertThat(result).isEqualTo("redirect:/user/extransfer");
	}

}
