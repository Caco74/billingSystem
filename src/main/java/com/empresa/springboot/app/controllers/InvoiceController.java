package com.empresa.springboot.app.controllers;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.empresa.springboot.app.models.entity.Client;
import com.empresa.springboot.app.models.entity.Invoice;
import com.empresa.springboot.app.models.entity.InvoiceItem;
import com.empresa.springboot.app.models.entity.Product;
import com.empresa.springboot.app.models.service.IClientService;

@Controller
@RequestMapping("invoice")
@SessionAttributes("invoice")
public class InvoiceController {
	
	@Autowired
	private IClientService clientService;
	
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	@GetMapping("/form/{clientId}")
	public String create(@PathVariable(value = "clientId") Long clientId, Map<String, Object> model, RedirectAttributes flash) {
		Client client = clientService.findOne(clientId);
		if (client == null) {
			flash.addFlashAttribute("error", "he client does not exist in the database.");
			return "redirect:/list";
		}
		
		Invoice invoice = new Invoice();
		invoice.setClient(client);
		
		model.put("invoice", invoice);
		model.put("title", "Create Invoice");
		
		return "invoice/form";
	}
	
	@GetMapping(value = "/load-products/{term}", produces = {"application/json"})
	public @ResponseBody List<Product> loadProducts(@PathVariable(value = "term") String term) {
		return clientService.findByName(term);
	}
	
	@PostMapping("/form")
	public String save(Invoice invoice, 
			@RequestParam(name = "item_id[]", required = false) Long[] itemId,
			@RequestParam(name = "quantity", required = false) Integer[] quantity,
			RedirectAttributes flash,
			SessionStatus status) {
		
		for(int i=0; i < itemId.length; i++) {
			Product product = clientService.fingProductById(itemId[i]);
			
			InvoiceItem line = new InvoiceItem();
			line.setQuantity(quantity[i]);
			line.setProduct(product);
			invoice.addInvoiceItem(line);
			log.info("ID: " + itemId[i].toString() + " | Quantity: " + quantity[i].toString());
		}
		
		clientService.saveInvoice(invoice);		
		status.setComplete();
		
		flash.addFlashAttribute("success", "Invoice create successful");
		
		return "redirect:/view/" + invoice.getClient().getId();
	}

}
