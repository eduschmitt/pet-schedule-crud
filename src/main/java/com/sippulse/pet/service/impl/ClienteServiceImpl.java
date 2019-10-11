package com.sippulse.pet.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sippulse.pet.entity.Cliente;
import com.sippulse.pet.exception.NegocioException;
import com.sippulse.pet.exception.NegocioException.TipoExcecao;
import com.sippulse.pet.repository.ClienteRepository;
import com.sippulse.pet.service.ClienteService;

/**
 * Camada de service para o CRUD de Clientes
 * @author eduardo
 *
 */
@Service
public class ClienteServiceImpl implements ClienteService {

	private ClienteRepository repository;

	@Autowired
	public ClienteServiceImpl(ClienteRepository repository) {
		super();
		this.repository = repository;
	}

	/**
	 * Busca todos os clientes existentes na base de dados.
	 * 
	 * @return Lista com os clientes encontrados.
	 */
	@Override
	public List<Cliente> findAll() {
		return (List<Cliente>) repository.findAll();
	}

	/**
	 * Busca um cliente pelo seu id.
	 * 
	 * @param Id do cliente.
	 * @return Cliente encontrado com o id informado. 
	 * @throws NegocioException
	 */
	@Override
	public Cliente findById(Long id) throws NegocioException {
		if (id == null) {
			throw new NegocioException("Id do cliente não informado.", TipoExcecao.VALIDACAO_CAMPOS);
		}
		return repository.findOne(id);
	}

	/**
	 * Salva ou atualiza um cliente.
	 * 
	 * @param Cliente a ser salvo ou atualizado.
	 * @return Cliente salvo ou atualizado.
	 * @throws NegocioException 
	 */
	@Override
	public Cliente save(Cliente cliente, Boolean isUpdate) throws NegocioException {
		if (cliente.getCpf() == null || cliente.getNome() == null || cliente.getNome().trim().isEmpty()) {
			throw new NegocioException("Os atributos CPF e nome são obrigatórios.", TipoExcecao.VALIDACAO_CAMPOS);
		}
		Boolean clienteExiste = repository.exists(cliente.getCpf()); 
		if (isUpdate && !clienteExiste) {
			throw new NegocioException("Cliente informado não existe.", TipoExcecao.REGISTRO_NAO_ENCONTRADO);
		}
		if (!isUpdate && clienteExiste) {
			throw new NegocioException("Cliente informado já existe.", TipoExcecao.VALIDACAO_CAMPOS);
		}
		
		return repository.save(cliente);
	}

	/**
	 * Realiza a exclusão de um cliente.
	 * 
	 * @param Id do cliente a ser excluído.
	 * @throws NegocioException
	 */
	@Override
	public void delete(Long id) throws NegocioException {
		if (id == null) {
			throw new NegocioException("Id do cliente não informado.", TipoExcecao.VALIDACAO_CAMPOS);
		}
		if (!repository.exists(id)) {
			throw new NegocioException("Cliente informado não existe.", TipoExcecao.REGISTRO_NAO_ENCONTRADO);
		}
		repository.delete(id);
	}


}
