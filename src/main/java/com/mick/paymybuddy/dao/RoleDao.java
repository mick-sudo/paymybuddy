package com.mick.paymybuddy.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mick.paymybuddy.model.Role;

public interface RoleDao extends JpaRepository<Role, Integer> {

	Role findRoleByName(String user);

}
