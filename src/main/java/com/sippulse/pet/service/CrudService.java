package com.sippulse.pet.service;

import java.util.List;

import com.sippulse.pet.exception.NegocioException;

public interface CrudService<T, U> {
	
	List<T> findAll();

	T findById(U id) throws NegocioException;
	
	T save(T entity, Boolean isUpdate) throws NegocioException;
	
	void delete(U id) throws NegocioException;
}
