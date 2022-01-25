package com.empresa.springboot.app.controllers;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.empresa.springboot.app.Client;
import com.empresa.springboot.app.models.dao.IClientDao;

@Controller
@SessionAttributes("client")
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
	public String save(@Valid Client client, BindingResult result, Model model, SessionStatus status) {
		if (result.hasErrors()) {
			model.addAttribute("title", "Client Form");
			return "form";
		}
		
		clientDao.save(client);
		status.setComplete();
		return "redirect:list";		
	}
	
	@RequestMapping(value = "/form/{id}")
	public String edit(@PathVariable(value = "id") Long id  ,Map<String ,Object> model) {
		Client client = null;
		
		if (id>0) {
			client = clientDao.findOne(id);
		} else {
			return "redirect:/list";			
		}
		model.put("client", client);
		model.put("title", "Edit client");
		return "form";
	}

}
