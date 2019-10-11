package com.sippulse.pet.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sippulse.pet.entity.Pet;
import com.sippulse.pet.exception.NegocioException;
import com.sippulse.pet.exception.NegocioException.TipoExcecao;
import com.sippulse.pet.repository.PetRepository;
import com.sippulse.pet.service.PetService;

/**
 * Camada de service para o CRUD de Pets
 * @author eduardo
 *
 */
@Service
public class PetServiceImpl implements PetService {

	private PetRepository repository;

	@Autowired
	public PetServiceImpl(PetRepository repository) {
		super();
		this.repository = repository;
	}

	/**
	 * Busca todos os pets existentes na base de dados.
	 * 
	 * @return Lista com os pets encontrados.
	 */
	@Override
	public List<Pet> findAll() {
		return (List<Pet>) repository.findAll();
	}

	/**
	 * Busca um pet pelo seu id.
	 * 
	 * @param Id do pet.
	 * @return Pet encontrado com o id informado. 
	 * @throws NegocioException
	 */
	@Override
	public Pet findById(Long id) throws NegocioException {
		if (id == null) {
			throw new NegocioException("Id do pet não informado.", TipoExcecao.VALIDACAO_CAMPOS);
		}
		return repository.findOne(id);
	}

	/**
	 * Salva ou atualiza um pet.
	 * 
	 * @param Pet a ser salvo ou atualizado.
	 * @return Pet salvo ou atualizado.
	 * @throws NegocioException 
	 */
	@Override
	public Pet save(Pet pet, Boolean isUpdate) throws NegocioException {
		if (pet.getId() == null || pet.getNome() == null || pet.getNome().trim().isEmpty()) {
			throw new NegocioException("Os atributos CPF e nome são obrigatórios.", TipoExcecao.VALIDACAO_CAMPOS);
		}
		Boolean petExiste = repository.exists(pet.getId()); 
		if (isUpdate && !petExiste) {
			throw new NegocioException("Pet informado não existe.", TipoExcecao.REGISTRO_NAO_ENCONTRADO);
		}
		if (!isUpdate && petExiste) {
			throw new NegocioException("Pet informado já existe.", TipoExcecao.VALIDACAO_CAMPOS);
		}
		
		return repository.save(pet);
	}

	/**
	 * Realiza a exclusão de um pet.
	 * 
	 * @param Id do pet a ser excluído.
	 * @throws NegocioException
	 */
	@Override
	public void delete(Long id) throws NegocioException {
		if (id == null) {
			throw new NegocioException("Id do pet não informado.", TipoExcecao.VALIDACAO_CAMPOS);
		}
		if (!repository.exists(id)) {
			throw new NegocioException("Pet informado não existe.", TipoExcecao.REGISTRO_NAO_ENCONTRADO);
		}
		repository.delete(id);
	}


}
