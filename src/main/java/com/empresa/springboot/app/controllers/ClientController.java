package com.empresa.springboot.app.controllers;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.empresa.springboot.app.models.entity.Client;
import com.empresa.springboot.app.models.service.IClientService;
import com.empresa.springboot.app.util.paginator.PageRender;

@Controller
@SessionAttributes("client")
public class ClientController {
	
	@Autowired
	private IClientService clientService;
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(@RequestParam(name = "page", defaultValue = "0") int page ,Model model) {
		Pageable pageRequest = PageRequest.of(page, 5);
		
		Page<Client> clients = clientService.findAll(pageRequest);
		
		PageRender<Client> pageRender = new PageRender<>("/list", clients);
		
		model.addAttribute("title", "Clients list");
		model.addAttribute("clients", clients);
		model.addAttribute("page", pageRender);
		
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
	public String save(@Valid Client client, BindingResult result, Model model, RedirectAttributes flash,SessionStatus status) {
		if (result.hasErrors()) {
			model.addAttribute("title", "Client Form");
			return "form";
		}
		String messageFlash = (client.getId() != null) ? "Client successfully edit!" : "Client successfully created!";
		
		clientService.save(client);
		status.setComplete();
		flash.addFlashAttribute("success", messageFlash);
		return "redirect:list";		
	}
	
	@RequestMapping(value = "/form/{id}")
	public String edit(@PathVariable(value = "id") Long id  ,Map<String ,Object> model, RedirectAttributes flash) {
		Client client = null;
		
		if (id>0) {
			client = clientService.findOne(id);
			if (client == null) {
				flash.addFlashAttribute("error", "Client ID does not exist in the database.");
				return "redirect:/list";
			}
		} else {
			flash.addFlashAttribute("error", "Client ID cannot be null.");
			return "redirect:/list";
		}
		model.put("client", client);
		model.put("title", "Edit client");
		return "form";
	}
	
	@RequestMapping(value = "/delete/{id}")
	public String delete(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
		if (id>0) {
			clientService.delete(id);
			flash.addFlashAttribute("success", "Client successfully removed.");
		}
		return "redirect:/list";
	}

}
