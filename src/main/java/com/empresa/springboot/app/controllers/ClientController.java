package com.empresa.springboot.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.empresa.springboot.app.models.dao.IClientDao;

@Controller
public class ClientController {
	
	@Autowired
	@Qualifier("clientDaoJPA")
	private IClientDao clientDao;
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Model model) {
		model.addAttribute("title", "Clients list");
		model.addAttribute("clients", clientDao.findAll());
		
		return "list";
	}

}
