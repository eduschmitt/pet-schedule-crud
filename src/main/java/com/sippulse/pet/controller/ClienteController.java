package com.sippulse.pet.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sippulse.pet.entity.Cliente;
import com.sippulse.pet.service.ClienteService;

@RestController
public class ClienteController {

	private ClienteService service;

	@Autowired
	public ClienteController(ClienteService service) {
		super();
		this.service = service;
	}

	@RequestMapping(value = "/clientes", method = RequestMethod.GET)
	List<Cliente> findAll() {
		return service.findAll();
	}

	@RequestMapping(value = "/cliente/{id}", method = RequestMethod.GET)
	ResponseEntity<Cliente> findById(@PathVariable Long id) {
		Cliente cliente = service.findById(id);
		return cliente != null ? new ResponseEntity<Cliente>(cliente, HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@RequestMapping(value = "/cliente", method = RequestMethod.POST)
	void save(@Valid @RequestBody Cliente cliente) {
		service.save(cliente);
	}
	
    @RequestMapping(value = "/cliente/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> delete(@PathVariable(value = "id") Long id) {
    	Cliente cliente = service.findById(id);
    	if (cliente != null) {
    		service.delete(cliente);
    		return new ResponseEntity<>(HttpStatus.OK);
    	}
    	return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}