package com.empresa.springboot.app.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.empresa.springboot.app.models.entity.AppUser;

public interface IUserDao extends CrudRepository<AppUser, Long> {
	
	public AppUser findByUsername(String username);

}
