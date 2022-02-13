package com.empresa.springboot.app.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.empresa.springboot.app.models.entity.Client;
import com.empresa.springboot.app.models.service.IClientService;

@RestController
@RequestMapping("/api/clients")
public class ClientRestController {
	
	@Autowired
	private IClientService clientService;
	
	@GetMapping(value = "/list")
	public List<Client> list() {
		return clientService.findAll();
	}

}
