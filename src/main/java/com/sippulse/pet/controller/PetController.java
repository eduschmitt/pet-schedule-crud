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

import com.sippulse.pet.entity.Pet;
import com.sippulse.pet.service.PetService;

@RestController
public class PetController {
	
	private PetService service;

	@Autowired
	public PetController(PetService service) {
		super();
		this.service = service;
	}

	@RequestMapping(value = "/pets", method = RequestMethod.GET)
	List<Pet> findAll() {
		return service.findAll();
	}

	@RequestMapping(value = "/pet/{id}", method = RequestMethod.GET)
	ResponseEntity<Pet> findById(@PathVariable Long id) {
		Pet pet = service.findById(id);
		return pet != null ? new ResponseEntity<Pet>(pet, HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@RequestMapping(value = "/pet", method = RequestMethod.POST)
	void save(@Valid @RequestBody Pet pet) {
		service.save(pet);
	}
	
    @RequestMapping(value = "/pet/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> delete(@PathVariable(value = "id") Long id) {
    	Pet pet = service.findById(id);
    	if (pet != null) {
    		service.delete(pet);
    		return new ResponseEntity<>(HttpStatus.OK);
    	}
    	return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}