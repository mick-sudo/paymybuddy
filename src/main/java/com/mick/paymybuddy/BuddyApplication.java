package com.mick.paymybuddy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.mick.paymybuddy.dao.RelationDao;
import com.mick.paymybuddy.dao.UserDao;

@SpringBootApplication
public class BuddyApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(BuddyApplication.class, args);
		
		
		// Test la connexion JPA
		
		UserDao userDao = context.getBean(UserDao.class);
		System.out.println(userDao.findAll().get(0).getFirstname());
		
		RelationDao relationDao = context.getBean(RelationDao.class);
		System.out.println(relationDao.findAll().get(0).getBuddy().getFirstname());
		
		
	}

}
