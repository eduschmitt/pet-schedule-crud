package com.sippulse.pet.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sippulse.pet.entity.Consulta;
import com.sippulse.pet.repository.ConsultaRepository;
import com.sippulse.pet.service.ConsultaService;

@Service
public class ConsultaServiceImpl implements ConsultaService {
	
	private ConsultaRepository repository;

	@Autowired
	public ConsultaServiceImpl(ConsultaRepository repository) {
		super();
		this.repository = repository;
	}
	
	@Override
	public List<Consulta> findAll() {
		return (List<Consulta>) repository.findAll();
	}

	@Override
	public Consulta findById(Long id) {
		return repository.findOne(id);
	}

	@Override
	public Consulta save(Consulta consulta) {
		return repository.save(consulta);
	}

	@Override
	public void delete(Consulta entity) {
		repository.delete(entity);
	}
}
