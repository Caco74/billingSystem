package com.empresa.springboot.app.controllers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.empresa.springboot.app.models.entity.Client;
import com.empresa.springboot.app.models.service.IClientService;
import com.empresa.springboot.app.models.service.IUploadFileService;
import com.empresa.springboot.app.util.paginator.PageRender;

@Controller
@SessionAttributes("client")
public class ClientController {

	@Autowired
	private IClientService clientService;
	
	@Autowired
	private IUploadFileService uploadFileService;

	

	@GetMapping(value = "/uploads/{filename:.+}")
	public ResponseEntity<Resource> pictureView(@PathVariable String filename) {
		Resource resource = null;
		try {
			resource = uploadFileService.load(filename);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}

	@GetMapping(value = "/view/{id}")
	public String view(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {
//		Client client = clientService.findOne(id);
		Client client = clientService.fetchByIdWithInvoices(id);
		if (client == null) {
			flash.addFlashAttribute("error", "The client does not exist in the database.");
			return "redirect:/list";
		}

		model.put("client", client);
		model.put("title", "Client Detail: " + client.getName() + " " + client.getLastName());

		return "view";
	}

	@RequestMapping(value = {"/list","/"}, method = RequestMethod.GET)
	public String list(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
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
	public String save(@Valid Client client, BindingResult result, Model model,
			@RequestParam("file") MultipartFile picture, RedirectAttributes flash, SessionStatus status) {
		if (result.hasErrors()) {
			model.addAttribute("title", "Client Form");
			return "form";
		}

		if (!picture.isEmpty()) {

			if (client.getId() != null && client.getId() > 0 && client.getPicture() != null
					&& client.getPicture().length() > 0) {

				uploadFileService.delete(client.getPicture());
			}
			
			String uniqueFilename = null;
			try {
				uniqueFilename = uploadFileService.copy(picture);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			

			flash.addFlashAttribute("info", "You have successfully uploaded '" + uniqueFilename + "'");
			client.setPicture(uniqueFilename);
		}
		String messageFlash = (client.getId() != null) ? "Client successfully edit!" : "Client successfully created!";

		clientService.save(client);
		status.setComplete();
		flash.addFlashAttribute("success", messageFlash);

		return "redirect:list";
	}

	@RequestMapping(value = "/form/{id}")
	public String edit(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {
		Client client = null;

		if (id > 0) {
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

		if (id > 0) {
			Client client = clientService.findOne(id);

			clientService.delete(id);
			flash.addFlashAttribute("success", "Client successfully removed.");

			if (uploadFileService.delete(client.getPicture())) {
				flash.addFlashAttribute("info", "Image " + client.getPicture() + " successfully deleted.");
			}
			
		}
		return "redirect:/list";
	}

}
