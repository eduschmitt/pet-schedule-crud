package com.sippulse.pet.service;

import java.util.List;

public interface CrudService<T> {
	
	List<T> findAll();

}
