package com.empresa.springboot.app.models.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.empresa.springboot.app.models.entity.Client;
import com.empresa.springboot.app.models.entity.Product;

public interface IClientService {
	
	public List<Client> findAll();
	
	public Page<Client> findAll(Pageable pageable);
	
	public void save(Client client);
	
	public Client findOne(Long id);
	
	public void delete(Long id);
	
	public List<Product> findByName(String term);

}
