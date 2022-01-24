package com.empresa.springboot.app.models.dao;

import java.util.List;

import com.empresa.springboot.app.Client;

public interface IClientDao {
	
	public List<Client> findAll();
	
	public void save(Client client);

}
