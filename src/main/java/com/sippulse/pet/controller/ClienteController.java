package com.sippulse.pet.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
}