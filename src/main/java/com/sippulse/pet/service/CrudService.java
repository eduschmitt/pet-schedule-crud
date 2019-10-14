package com.sippulse.pet.service;

import java.util.List;

/**
 * Interface para definição dos métodos CRUD padrão que são utilizados por todos os services.
 * @author schmitt
 *
 * @param <T>
 * @param <U>
 */
public interface CrudService<T, U> {
	
	/**
	 * Buscar todos os registros.
	 * @return
	 */
	List<T> findAll();

	/**
	 * Buscar registro por ID.
	 * @param id
	 * @return
	 */
	T findById(U id);
	
	/**
	 * Salvar/Atualizar registro.
	 * @param entity
	 * @param isUpdate
	 * @return
	 */
	T save(T entity, Boolean isUpdate);
	
	/**
	 * Excluir registro.
	 * @param id
	 */
	void delete(U id);
}
