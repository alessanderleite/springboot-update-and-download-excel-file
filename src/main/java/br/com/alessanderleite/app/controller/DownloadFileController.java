package br.com.alessanderleite.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import br.com.alessanderleite.app.service.CustomerService;

@Controller
public class DownloadFileController {
	
	@Autowired
	CustomerService customerService;
	
	/*
	 * Download Files 
	 */
	@GetMapping("/file")
	public ResponseEntity<InputStreamResource> downloadFile() {
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Dispositon", "attachment; filename=customers.xlsx");
		
		return ResponseEntity
				.ok()
				.headers(headers)
				.body(new InputStreamResource(customerService.loadFile()));	
	}
}
