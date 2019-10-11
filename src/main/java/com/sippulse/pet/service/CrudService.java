package com.sippulse.pet.service;

import java.util.List;

import com.sippulse.pet.exception.ServiceException;

public interface CrudService<T, U> {
	
	List<T> findAll();

	T findById(U id);
	
	T save(T entity, Boolean isUpdate);
	
	void delete(U id);
}
