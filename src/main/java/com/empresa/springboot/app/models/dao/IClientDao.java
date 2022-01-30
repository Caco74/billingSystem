package com.empresa.springboot.app.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.empresa.springboot.app.models.entity.Client;

public interface IClientDao extends PagingAndSortingRepository<Client, Long> {
	
	@Query("SELECT c FROM Client c LEFT JOIN FETCH c.invoices i WHERE c.id=?1")
	public Client fetchByIdWithInvoices(Long id);
	
}
