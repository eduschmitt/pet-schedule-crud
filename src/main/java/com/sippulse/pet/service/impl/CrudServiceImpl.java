package com.sippulse.pet.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.sippulse.pet.service.CrudService;

public class CrudServiceImpl<T,U extends Serializable> implements CrudService<T, U> {
	
	private CrudRepository<T, U> repository;
	
	public CrudServiceImpl(CrudRepository<T, U> repository) {
		this.repository = repository;
	}

	@Override
	public List<T> findAll() {
		return (List<T>) repository.findAll();
	}

	@Override
	public T findById(U id) {
		return repository.findOne(id);
	}

	@Override
	public T save(T entity) {
		return repository.save(entity);
	}

	@Override
	public void delete(T entity) {
		repository.delete(entity);
	}
	
	
//	@Override
//	public T save(T entity, U id, Boolean isUpdate) {
//		if (isUpdate && (id == null || !repository.exists(id))) {
//			throw new RuntimeException("O registro informado para atualização não existe.");
//		}		
//		return repository.save(entity);
//	}
//
//	@Override
//	public void delete(U id) {
//		if (!repository.exists(id)) {
//			throw new RuntimeException("O registro informado para exclusão não existe.");
//		}
//		repository.delete(id);
//	}

}
