package com.empresa.springboot.app.models.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.empresa.springboot.app.models.entity.Client;

@Repository("clientDaoJPA")
public class ClientDaoImpl implements IClientDao {
	
	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	
	@Override
	public List<Client> findAll() {
		return em.createQuery("from Client").getResultList();
	}

	@Override
	
	public Client findOne(Long id) {
		return em.find(Client.class, id);
	}

	@Override
	public void save(Client client) {
		if(client.getId() != null && client.getId() > 0) {
			em.merge(client);
		} else {
			em.persist(client);
		}
	}

	@Override
	public void delete(Long id) {
		em.remove(findOne(id));		
	}

}
