package com.empresa.springboot.app.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.empresa.springboot.app.models.entity.Client;

public interface IClientDao extends CrudRepository<Client, Long> {
	
	
	
}
