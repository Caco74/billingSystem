package com.empresa.springboot.app.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.empresa.springboot.app.models.entity.Client;
import com.empresa.springboot.app.models.dao.IClientDao;

@Service
public class ClientServiceImpl implements IClientService {
	
	@Autowired
	private IClientDao clientDao;

	@Override
	@Transactional(readOnly = true)
	public List<Client> findAll() {
		return clientDao.findAll();
	}
	
	@Override
	@Transactional(readOnly = true)
	public Client findOne(Long id) {
		return clientDao.findOne(id);
	}

	@Override
	@Transactional
	public void save(Client client) {
		clientDao.save(client);		
	}


	@Override
	@Transactional
	public void delete(Long id) {
		clientDao.delete(id);		
	}

}
