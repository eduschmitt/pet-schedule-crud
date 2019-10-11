package com.sippulse.pet.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sippulse.pet.entity.Cliente;
import com.sippulse.pet.exception.ServiceException;
import com.sippulse.pet.exception.ServiceException.TipoExcecao;
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
		List<Cliente> clientes = (List<Cliente>) repository.findAll();
		if (clientes == null) {
			throw new ServiceException("Nenhum registro encontrado.", TipoExcecao.REGISTRO_NAO_ENCONTRADO);
		}
		return (List<Cliente>) repository.findAll();
	}

	/**
	 * Busca um cliente pelo seu id.
	 * 
	 * @param Id do cliente.
	 * @return Cliente encontrado com o id informado. 
	 * @throws ServiceException
	 */
	@Override
	public Cliente findById(Long id) {
		if (id == null) {
			throw new ServiceException("Id do registro não informado.", TipoExcecao.VALIDACAO_CAMPOS);
		}
		Cliente c = repository.findOne(id);
		if (c == null) {
			throw new ServiceException("Registro de id " + id + " não encontrado.", TipoExcecao.REGISTRO_NAO_ENCONTRADO);
		}
		return c;
	}

	/**
	 * Salva ou atualiza um cliente.
	 * 
	 * @param Cliente a ser salvo ou atualizado.
	 * @return Cliente salvo ou atualizado.
	 * @throws ServiceException 
	 */
	@Override
	public Cliente save(Cliente cliente, Boolean isUpdate) {
		if (cliente.getCpf() == null || cliente.getNome() == null || cliente.getNome().trim().isEmpty()) {
			throw new ServiceException("Os atributos CPF e nome são obrigatórios.", TipoExcecao.VALIDACAO_CAMPOS);
		}
		Boolean clienteExiste = repository.exists(cliente.getCpf()); 
		if (isUpdate && !clienteExiste) {
			throw new ServiceException("Registro informado para atualização não existe.", TipoExcecao.REGISTRO_NAO_ENCONTRADO);
		}
		if (!isUpdate && clienteExiste) {
			throw new ServiceException("Registro informado para inclusão já existe.", TipoExcecao.VALIDACAO_CAMPOS);
		}
		
		return repository.save(cliente);
	}

	/**
	 * Realiza a exclusão de um cliente.
	 * 
	 * @param Id do cliente a ser excluído.
	 * @throws ServiceException
	 */
	@Override
	public void delete(Long id) {
		if (id == null) {
			throw new ServiceException("Id do registro não informado.", TipoExcecao.VALIDACAO_CAMPOS);
		}
		if (!repository.exists(id)) {
			throw new ServiceException("Registro informado para exclusão não existe.", TipoExcecao.REGISTRO_NAO_ENCONTRADO);
		}
		repository.delete(id);
	}


}
