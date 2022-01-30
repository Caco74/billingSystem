package com.empresa.springboot.app.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.empresa.springboot.app.models.entity.Invoice;

public interface IInvoiceDao extends CrudRepository<Invoice, Long> {
	
	@Query("SELECT i FROM Invoice i JOIN FETCH i.client c JOIN FETCH i.items l JOIN FETCH l.product WHERE i.id=?1")
	public Invoice fetchByIdWithClientWithInvoiceItemWithProduct(Long id);
	

}
