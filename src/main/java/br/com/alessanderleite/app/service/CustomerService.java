package br.com.alessanderleite.app.service;

import java.io.ByteArrayInputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.alessanderleite.app.model.Customer;
import br.com.alessanderleite.app.repository.CustomerRepository;
import br.com.alessanderleite.app.util.Utils;

@Service
public class CustomerService {

	@Autowired
	CustomerRepository customerRepository;
	
	// Store file data to database
	public void store(MultipartFile file) {
		try {
			List<Customer> listCustomers = Utils.parseExcelFile(file.getInputStream());
			// Save Customers to Database
			customerRepository.saveAll(listCustomers);
		} catch (Exception e) {
			throw new RuntimeException("FAIL! -> message = " + e.getMessage());
		}
	}
	
	// Load Data to Excel File
	public ByteArrayInputStream loadFile() {
		List<Customer> customers = (List<Customer>) customerRepository.findAll();
		
		try {
			ByteArrayInputStream input = Utils.customerToExcel(customers);
			return input;
		} catch (Exception e) {}
		
		return null;
	}
}
