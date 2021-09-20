package com.mick.paymybuddy.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "internal_transfer")
@PrimaryKeyJoinColumn(name = "transfer_id") // PK de l'entité mere
public class InternalTransfer extends Transfer{
	
	@JoinColumn(name = "sender")
	@ManyToOne
    private User userSender;
	
	@JoinColumn(name = "receiver")
	@ManyToOne
    private User userReceiver;

	public InternalTransfer(User userSender, User userReceiver) {
		super();
		this.userSender = userSender;
		this.userReceiver = userReceiver;
	}

	public InternalTransfer() {
		super();
	}

	public User getUserSender() {
		return userSender;
	}

	public void setUserSender(User userSender) {
		this.userSender = userSender;
	}

	public User getUserReceiver() {
		return userReceiver;
	}

	public void setUserReceiver(User userReceiver) {
		this.userReceiver = userReceiver;
	}
	
	
}
