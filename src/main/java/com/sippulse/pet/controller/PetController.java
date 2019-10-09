package com.sippulse.pet.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sippulse.pet.entity.Pet;

@RestController
public class PetController {
	
	@RequestMapping(value = "/pets", method = RequestMethod.GET)
	List<Pet> findAll() {
		
		Pet p = new Pet();
		p.setCpfCliente(8421886657l);
		p.setId(1l);
		p.setNome("Pituxo");
		
		List<Pet> pets = new ArrayList<>();
		pets.add(p);
		
		return pets;
	}
}