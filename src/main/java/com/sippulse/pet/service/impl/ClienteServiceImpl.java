package com.sippulse.pet.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sippulse.pet.entity.Cliente;
import com.sippulse.pet.repository.ClienteRepository;
import com.sippulse.pet.service.ClienteService;

@Service
public class ClienteServiceImpl implements ClienteService {
	
	private ClienteRepository repository;
	
	@Autowired
	public ClienteServiceImpl(ClienteRepository repository) {
		super();
		this.repository = repository;
	}

	@Override
	public List<Cliente> findAll() {
		return (List<Cliente>) repository.findAll();
	}

}
