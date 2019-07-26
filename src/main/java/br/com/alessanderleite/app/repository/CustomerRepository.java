package br.com.alessanderleite.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.alessanderleite.app.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long>{

}
