package br.com.alessanderleite.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import br.com.alessanderleite.app.service.CustomerService;

@Controller
public class UploadFileController {
	
	@Autowired
	CustomerService customerServices;
	
	@GetMapping("/")
	public String index() {
		return "multipartfile/uploadform.html";
	}
	
	@PostMapping("/")
	public String uploadMultipartFile(@RequestParam("uploadfile") MultipartFile file, Model model) {
		try {
			customerServices.store(file);
			model.addAttribute("message", "File uploaded successfully!");
		} catch (Exception e) {
			model.addAttribute("message", "FAIL! -> upload filename: " + file.getOriginalFilename());
		}
		return "multipartfile/uploadform.html";
	}
}
