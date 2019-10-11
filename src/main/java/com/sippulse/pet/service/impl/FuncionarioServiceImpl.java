package com.sippulse.pet.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sippulse.pet.entity.Funcionario;
import com.sippulse.pet.repository.FuncionarioRepository;
import com.sippulse.pet.service.FuncionarioService;

@Service
public class FuncionarioServiceImpl implements FuncionarioService {

	private FuncionarioRepository repository;

	@Autowired
	public FuncionarioServiceImpl(FuncionarioRepository repository) {
		super();
		this.repository = repository;
	}

	@Override
	public List<Funcionario> findAll() {
		return (List<Funcionario>) repository.findAll();
	}

	@Override
	public Funcionario findById(Long id) {
		return repository.findOne(id);
	}

	@Override
	public Funcionario save(Funcionario funcionario) {
		return repository.save(funcionario);
	}

	@Override
	public void delete(Funcionario entity) {
		repository.delete(entity);
	}

}
