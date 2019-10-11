package com.sippulse.pet.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sippulse.pet.entity.Cliente;
import com.sippulse.pet.exception.NegocioException;
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

	@Override
	public Cliente findById(Long id) throws NegocioException {
		if (id == null) {
			throw new NegocioException("Id do cliente não informado.");
		}
		return repository.findOne(id);
	}

	@Override
	public Cliente save(Cliente cliente) {
		return repository.save(cliente);
	}

	@Override
	public Cliente update(Cliente cliente) throws NegocioException {
		if (cliente.getCpf() == null) {
			throw new NegocioException("Id do cliente não informado.");
		}		
		return repository.save(cliente);
	}
	
	@Override
	public void delete(Long id) throws NegocioException {
		if (id == null) {
			throw new NegocioException("Id do cliente não informado.");
		}
		repository.delete(id);
	}


}
