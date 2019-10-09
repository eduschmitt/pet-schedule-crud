package com.sippulse.pet.service;

import java.util.List;

public interface CrudService<T, U> {
	
	List<T> findAll();

	T findById(U id);
	
	T save(T entity);
	
	void delete(T entity);
	

}
