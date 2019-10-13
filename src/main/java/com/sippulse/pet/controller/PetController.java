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

import com.fasterxml.jackson.annotation.JsonView;
import com.sippulse.pet.entity.Pet;
import com.sippulse.pet.service.PetService;
import com.sippulse.pet.utils.View;

/**
 * Classe controller para as requisições relacionadas a Pet
 * 
 * @author eduardo
 *
 */
@RestController
@RequestMapping("pets")
public class PetController {

	private PetService service;

	@Autowired
	public PetController(PetService service) {
		super();
		this.service = service;
	}

	@RequestMapping(method = RequestMethod.GET)
	@JsonView(View.PetComCliente.class)
	List<Pet> findAll() {
		return service.findAll();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@JsonView(View.PetComCliente.class)
	ResponseEntity<Pet> findById(@PathVariable Long id) {
		return new ResponseEntity<Pet>(service.findById(id), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST)
	@JsonView(View.PetComCliente.class)
	ResponseEntity<Pet> save(@Valid @RequestBody Pet pet) {
		return new ResponseEntity<Pet>(service.save(pet, false), HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.PUT)
	@JsonView(View.PetComCliente.class)
	ResponseEntity<Pet> update(@Valid @RequestBody Pet pet) {
		return new ResponseEntity<Pet>(service.save(pet, true), HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Pet> delete(@PathVariable(value = "id") Long id) {
		service.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}