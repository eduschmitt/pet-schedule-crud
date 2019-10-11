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

/**
 * Classe controller para as requisições relacionadas a Cliente
 * 
 * @author eduardo
 *
 */
@RestController
@RequestMapping("clientes")
public class ClienteController {

	private ClienteService service;

	@Autowired
	public ClienteController(ClienteService service) {
		super();
		this.service = service;
	}

	@RequestMapping(method = RequestMethod.GET)
	List<Cliente> findAll() {
		return service.findAll();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	ResponseEntity<Cliente> findById(@PathVariable Long id) {
		return new ResponseEntity<Cliente>(service.findById(id), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST)
	ResponseEntity<Cliente> save(@Valid @RequestBody Cliente cliente) {
		return new ResponseEntity<Cliente>(service.save(cliente, false), HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.PUT)
	ResponseEntity<Cliente> update(@Valid @RequestBody Cliente cliente) {
		return new ResponseEntity<Cliente>(service.save(cliente, true), HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Cliente> delete(@PathVariable(value = "id") Long id) {
		service.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}