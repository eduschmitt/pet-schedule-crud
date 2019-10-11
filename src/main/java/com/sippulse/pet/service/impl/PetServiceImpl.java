package com.sippulse.pet.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sippulse.pet.entity.Pet;
import com.sippulse.pet.exception.ServiceException;
import com.sippulse.pet.exception.ServiceException.TipoExcecao;
import com.sippulse.pet.repository.PetRepository;
import com.sippulse.pet.service.PetService;

/**
 * Camada de service para o CRUD de Pets
 * 
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
		List<Pet> pets = (List<Pet>) repository.findAll();
		if (pets == null) {
			throw new ServiceException("Nenhum registro encontrado.", TipoExcecao.REGISTRO_NAO_ENCONTRADO);
		}
		return (List<Pet>) repository.findAll();
	}

	/**
	 * Busca um pet pelo seu id.
	 * 
	 * @param Id do pet.
	 * @return Pet encontrado com o id informado.
	 * @throws ServiceException
	 */
	@Override
	public Pet findById(Long id) {
		if (id == null) {
			throw new ServiceException("Id do registro não informado.", TipoExcecao.VALIDACAO_CAMPOS);
		}
		Pet c = repository.findOne(id);
		if (c == null) {
			throw new ServiceException("Registro de id " + id + " não encontrado.",
					TipoExcecao.REGISTRO_NAO_ENCONTRADO);
		}
		return c;
	}

	/**
	 * Salva ou atualiza um pet.
	 * 
	 * @param Pet a ser salvo ou atualizado.
	 * @return Pet salvo ou atualizado.
	 * @throws ServiceException
	 */
	@Override
	public Pet save(Pet pet, Boolean isUpdate) {
//		if (pet.getCpf() == null || pet.getNome() == null || pet.getNome().trim().isEmpty()) {
//			throw new ServiceException("Os atributos CPF e nome são obrigatórios.", TipoExcecao.VALIDACAO_CAMPOS);
//		}
		Boolean petExiste = repository.exists(pet.getId());
		if (isUpdate && !petExiste) {
			throw new ServiceException("Registro informado para atualização não existe.",
					TipoExcecao.REGISTRO_NAO_ENCONTRADO);
		}
		if (!isUpdate && petExiste) {
			throw new ServiceException("Registro informado para inclusão já existe.", TipoExcecao.VALIDACAO_CAMPOS);
		}

		return repository.save(pet);
	}

	/**
	 * Realiza a exclusão de um pet.
	 * 
	 * @param Id do pet a ser excluído.
	 * @throws ServiceException
	 */
	@Override
	public void delete(Long id) {
		if (id == null) {
			throw new ServiceException("Id do registro não informado.", TipoExcecao.VALIDACAO_CAMPOS);
		}
		if (!repository.exists(id)) {
			throw new ServiceException("Registro informado para exclusão não existe.",
					TipoExcecao.REGISTRO_NAO_ENCONTRADO);
		}
		repository.delete(id);
	}

}
