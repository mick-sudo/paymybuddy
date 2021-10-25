package com.mick.paymybuddy.servicetest;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.mick.paymybuddy.dao.BankAccountDao;
import com.mick.paymybuddy.dao.ExternalTransferDao;
import com.mick.paymybuddy.dao.InternalTransferDao;
import com.mick.paymybuddy.dao.RelationDao;
import com.mick.paymybuddy.dao.TransferDao;
import com.mick.paymybuddy.dao.UserDao;
import com.mick.paymybuddy.dto.ExternalTransferDto;
import com.mick.paymybuddy.dto.InternalTransferDto;
import com.mick.paymybuddy.dto.UserRegistrationDto;
import com.mick.paymybuddy.exception.DataNotFoundException;
import com.mick.paymybuddy.model.BankAccount;
import com.mick.paymybuddy.model.InternalTransfer;
import com.mick.paymybuddy.model.Relation;
import com.mick.paymybuddy.model.Role;
import com.mick.paymybuddy.model.Transfer;
import com.mick.paymybuddy.model.User;
import com.mick.paymybuddy.service.TransferServiceImpl;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TransferServiceImplTest {

	@Autowired
	TransferServiceImpl transferService;
	@MockBean
	UserDao userDao;
	@MockBean
	RelationDao relationDao;
	@MockBean
	TransferDao transferDao;
	@MockBean
	InternalTransferDao internalTransferDao;
	@MockBean
	ExternalTransferDao externalTransferDao;
	@MockBean
	BankAccountDao bankAccountDao;

	User owner = new User(1, "barack", "obama", "a@a.com", "1234", BigDecimal.ZERO,
			Timestamp.valueOf(LocalDateTime.now()));
	User buddy = new User(2, "george", "bush", "b@b.com", "4321", BigDecimal.ZERO,
			Timestamp.valueOf(LocalDateTime.now()));

	Relation relation = new Relation(owner, buddy);

	Transfer transfer = new Transfer();

	Role role = new Role("USER");
	
	BankAccount bankAccount = new BankAccount("String iban", "String bic", "String bankName"," String accountName", owner);

	InternalTransferDto internalTransferDto = new InternalTransferDto();
	ExternalTransferDto externalTransferDto = new ExternalTransferDto("String ibanUser", BigDecimal.TEN, "String emailUser", "String description",
			BigDecimal.ONE);
	UserRegistrationDto userRegistrationDto = new UserRegistrationDto("barack", "obama", "a@a.com", "1234");
	InternalTransfer internalTransfer = new InternalTransfer();

	@Test
	void doInternalTransfer() throws SQLException {
		try {
			transferService.doInternalTransfer(internalTransferDto);
			verify(transferDao, times(0)).save(transfer);
			verify(userDao, times(0)).save(owner);
			verify(relationDao, times(1)).findByOwner_EmailAndBuddy_Email(any(), any());

		} catch (DataNotFoundException e) {
			assert (e.getMessage().contains("les 2 user ne sont pas amis"));
		}
	}

	@Test
	void doExternalTransfer() throws SQLException {
		when(bankAccountDao.findBankAccountByIbanAndUser_Email(any(), any())).thenReturn(bankAccount);
		transferService.doExternalTransfer(externalTransferDto);
		verify(transferDao, times(0)).save(transfer);
		verify(userDao, times(1)).save(owner);
		verify(relationDao, times(0)).findByOwner_EmailAndBuddy_Email(any(), any());
	}

	@Test
	void findInternalTransferByUser() {
	}

	@Test
	void findExternalTransferByUser() {
	}
}
