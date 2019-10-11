package com.sippulse.pet.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sippulse.pet.entity.Pet;
import com.sippulse.pet.repository.PetRepository;
import com.sippulse.pet.service.PetService;

@Service
public class PetServiceImpl implements PetService{

	private PetRepository repository;

	@Autowired
	public PetServiceImpl(PetRepository repository) {
		super();
		this.repository = repository;
	}

	@Override
	public List<Pet> findAll() {
		return (List<Pet>) repository.findAll();
	}

	@Override
	public Pet findById(Long id) {
		return repository.findOne(id);
	}

	@Override
	public Pet save(Pet pet) {
		return repository.save(pet);
	}

	@Override
	public void delete(Pet entity) {
		repository.delete(entity);
	}
	
}
