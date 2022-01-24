package com.empresa.springboot.app.controllers;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.empresa.springboot.app.Client;
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
	
	@RequestMapping(value = "/form")
	public String create(Map<String, Object> model) {
		Client client = new Client();
		model.put("client", client);		
		model.put("title", "Client Form");		
		return "form";
	}
	
	@RequestMapping(value = "/form", method = RequestMethod.POST)
	public String save(@Valid Client client, BindingResult result, Model model) {
		if (result.hasErrors()) {
			model.addAttribute("title", "Client Form");
			return "form";
		}
		
		clientDao.save(client);
		return "redirect:list";		
	}
	

}
