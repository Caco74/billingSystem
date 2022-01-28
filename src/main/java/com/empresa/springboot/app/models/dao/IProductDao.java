package com.empresa.springboot.app.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.empresa.springboot.app.models.entity.Product;

public interface IProductDao extends CrudRepository<Product, Long>  {

	@Query("SELECT p FROM Product p WHERE p.name LIKE %?1%")
	public List<Product> findByName(String term);
	
	public List<Product> findByNameLikeIgnoreCase(String term);
	
}
