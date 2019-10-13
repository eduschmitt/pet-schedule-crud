package com.sippulse.pet.service.impl;

import static com.sippulse.pet.utils.ValidacaoUtil.isValidString;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sippulse.pet.entity.Pet;
import com.sippulse.pet.exception.ServiceException;
import com.sippulse.pet.exception.ServiceException.TipoExcecao;
import com.sippulse.pet.repository.PetRepository;
import com.sippulse.pet.service.PetService;

import lombok.extern.slf4j.Slf4j;


/**
 * Camada de service para o CRUD de Pets
 * @author eduardo
 *
 */
@Service
@Slf4j
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
		log.debug("Buscando todos os pets.");
		List<Pet> pets = (List<Pet>) repository.findAll();
		if (pets == null) {
			log.error("Nenhum registro encontrado.");
			throw new ServiceException("Nenhum registro encontrado.", TipoExcecao.REGISTRO_NAO_ENCONTRADO);
		}
		return (List<Pet>) pets;
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
		log.debug("Buscando o pet de id {}", id);
		if (id == null) {
			log.error("Id do registro não informado.");
			throw new ServiceException("Id do registro não informado.", TipoExcecao.VALIDACAO_CAMPOS);
		}
		Pet c = repository.findOne(id);
		if (c == null) {
			log.error("Registro de id {} não encontrado.", id);
			throw new ServiceException("Registro de id " + id + " não encontrado.", TipoExcecao.REGISTRO_NAO_ENCONTRADO);
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
		log.debug("Invocando o método para {}.", isUpdate ? "atualização" : "inclusão");
		if (isUpdate && (pet.getId() == null || !repository.exists(pet.getId()))) {
			throw new ServiceException("Na atualização, é necessário informar o id de um registro existente.", TipoExcecao.VALIDACAO_CAMPOS);
		}
		if (pet.getCliente() == null || pet.getCliente().getCpf() == null || !isValidString(pet.getNome())) {
			throw new ServiceException("Os atributos cliente e nome são obrigatórios.", TipoExcecao.VALIDACAO_CAMPOS);
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
		log.debug("Invocando o método para exclusão.");
		if (id == null) {
			log.error("Id do registro não informado.");
			throw new ServiceException("Id do registro não informado.", TipoExcecao.VALIDACAO_CAMPOS);
		}
		if (!repository.exists(id)) {
			log.error("Registro de id {} não encontrado.", id);
			throw new ServiceException("Registro informado para exclusão não existe.", TipoExcecao.REGISTRO_NAO_ENCONTRADO);
		}
		repository.delete(id);
	}


}
