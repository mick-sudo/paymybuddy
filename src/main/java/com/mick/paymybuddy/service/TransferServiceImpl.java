package com.mick.paymybuddy.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mick.paymybuddy.dao.BankAccountDao;
import com.mick.paymybuddy.dao.ExternalTransferDao;
import com.mick.paymybuddy.dao.InternalTransferDao;
import com.mick.paymybuddy.dao.RelationDao;
import com.mick.paymybuddy.dao.TransferDao;
import com.mick.paymybuddy.dao.UserDao;
import com.mick.paymybuddy.dto.ExternalTransferDto;
import com.mick.paymybuddy.dto.InternalTransferDto;
import com.mick.paymybuddy.exception.DataNotExistException;
import com.mick.paymybuddy.exception.DataNotFoundException;
import com.mick.paymybuddy.model.BankAccount;
import com.mick.paymybuddy.model.ExternalTransfer;
import com.mick.paymybuddy.model.InternalTransfer;
import com.mick.paymybuddy.model.Relation;
import com.mick.paymybuddy.model.User;

@Service
public class TransferServiceImpl implements TransferService {

	@Autowired
	private TransferDao transferDao;
	@Autowired
	private InternalTransferDao internalTransferDao;
	@Autowired
	private RelationDao relationDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private ExternalTransferDao externalTransferDao;
	@Autowired
	private BankAccountDao bankAccountDao;

	@Override
	public List<InternalTransferDto> findInternalTransferByUser(String emailOwner) {
		List<InternalTransferDto> internalTransferDtos = new ArrayList<>();
		for (InternalTransfer internalTransfer : internalTransferDao
				.findAllByUserSender_EmailOrderByTransactionDateDesc(emailOwner)) {
			InternalTransferDto dto = new InternalTransferDto();
			dto.setEmailSender(internalTransfer.getUserSender().getEmail());
			dto.setEmailReceiver(internalTransfer.getUserReceiver().getEmail());
			dto.setAmount(internalTransfer.getAmount());
			dto.setId(internalTransfer.getId());
			dto.setDescription(internalTransfer.getDescription());
			internalTransferDtos.add(dto);
		}
		return internalTransferDtos;
	}

	@Override
	public InternalTransferDto doInternalTransfer(InternalTransferDto internalTransferDto) {
		Relation relation = relationDao.findByOwner_EmailAndBuddy_Email(internalTransferDto.getEmailSender(),
				internalTransferDto.getEmailReceiver());
		// verifier leur amitié
		if (relation == null) {
			throw new DataNotFoundException("les 2 user ne sont pas amis");
		}
		// on check si le sender à assez d'argent pour la transaction
		if (internalTransferDto.getAmount().compareTo(relation.getOwner().getBalance()) > 0) {
			throw new DataNotExistException("fonds insuffisants");
		}
		InternalTransfer internalTransfer = new InternalTransfer();
		internalTransfer.setUserSender(relation.getOwner());
		internalTransfer.setUserReceiver(relation.getBuddy());
		internalTransfer.setAmount(internalTransferDto.getAmount());
		internalTransfer.setDescription(internalTransferDto.getDescription());
		internalTransfer.setTransactionDate(Timestamp.valueOf(LocalDateTime.now()));

		transferDao.save(internalTransfer);

		internalTransferDto.setId(internalTransfer.getId());
		// on met a jours les balance des 2 users

		relation.getOwner().setBalance(relation.getOwner().getBalance().subtract(internalTransferDto.getAmount()));
		relation.getBuddy().setBalance(relation.getBuddy().getBalance().add(internalTransferDto.getAmount()));
		userDao.save(relation.getOwner());
		userDao.save(relation.getBuddy());

		return internalTransferDto;
	}

	@Override
	public List<ExternalTransferDto> findExternalTransferByUser(String emailOwner) {
		List<ExternalTransferDto> externalTransferDtos = new ArrayList<>();
		for (ExternalTransfer externalTransfer : externalTransferDao
				.findAllByBankAccount_User_EmailOrderByTransactionDateDesc(emailOwner)) {
			ExternalTransferDto dto = new ExternalTransferDto();
			dto.setIbanUser(externalTransfer.getBankAccount().getIban());
			dto.setDescription(externalTransfer.getDescription());
			dto.setAmountUser(externalTransfer.getAmount());
			dto.setFees(externalTransfer.getFees());
			externalTransferDtos.add(dto);
		}
		return externalTransferDtos;
	}

	@Override
	public ExternalTransferDto doExternalTransfer(ExternalTransferDto externalTransferDto) {
		// récupérer le bank account en fontion de l'iban et de l'email du user
		BankAccount bankAccount = bankAccountDao.findBankAccountByIbanAndUser_Email(externalTransferDto.getIbanUser(),
				externalTransferDto.getEmailUser());
		User user = bankAccount.getUser();
		// On attribut le dernier iban ajouté.
		// Fees
		BigDecimal fee = externalTransferDto.getAmountUser().multiply(BigDecimal.valueOf(0.005));

		ExternalTransfer externalTransfer = new ExternalTransfer();
		externalTransfer.setAmount(externalTransferDto.getAmountUser());
		externalTransfer.setDescription(externalTransferDto.getDescription());
		externalTransfer.setTransactionDate(Timestamp.valueOf(LocalDateTime.now()));
		externalTransfer.setFees(fee);
		externalTransfer.setBankAccount(bankAccount);

		transferDao.save(externalTransfer);

		externalTransferDto.setId(externalTransfer.getId());
		user.setBalance(user.getBalance().add(externalTransfer.getAmount().subtract(fee)));

		userDao.save(user);

		return externalTransferDto;

	}

}
