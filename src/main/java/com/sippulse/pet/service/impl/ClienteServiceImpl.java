package com.sippulse.pet.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sippulse.pet.entity.Cliente;
import com.sippulse.pet.exception.ServiceException;
import com.sippulse.pet.exception.ServiceException.TipoExcecao;
import com.sippulse.pet.repository.ClienteRepository;
import com.sippulse.pet.service.ClienteService;

import lombok.extern.slf4j.Slf4j;

import static com.sippulse.pet.utils.ValidacaoUtil.*;

/**
 * Camada de service para o CRUD de Clientes
 * @author eduardo
 *
 */
@Service
@Slf4j
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
		log.debug("Buscando todos os clientes.");
		List<Cliente> clientes = (List<Cliente>) repository.findAll();
		if (clientes == null) {
			log.error("Nenhum registro encontrado.");
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
		log.debug("Buscando o cliente de id {}", id);
		if (id == null) {
			log.error("Id do registro não informado.");
			throw new ServiceException("Id do registro não informado.", TipoExcecao.VALIDACAO_CAMPOS);
		}
		Cliente c = repository.findOne(id);
		if (c == null) {
			log.error("Registro de id {} não encontrado.", id);
			throw new ServiceException("Registro de id " + id + " não encontrado.", TipoExcecao.REGISTRO_NAO_ENCONTRADO);
		}
		return c;
	}

	/**
	 * Salva ou atualiza um cliente.
	 * 
	 * @param Basic a ser salvo ou atualizado.
	 * @return Cliente salvo ou atualizado.
	 * @throws ServiceException 
	 */
	@Override
	public Cliente save(Cliente cliente, Boolean isUpdate) {
		log.debug("Invocando o método para {}.", isUpdate ? "atualização" : "inclusão");
		if (cliente.getCpf() == null || !isValidString(cliente.getNome()) || !isValidString(cliente.getTelefone())) {
			throw new ServiceException("Os atributos CPF, nome e telefone são obrigatórios.", TipoExcecao.VALIDACAO_CAMPOS);
		}
		if (!isValidCpf(cliente.getCpf().toString())) {
			throw new ServiceException("CPF inválido.", TipoExcecao.VALIDACAO_CAMPOS);
		}
		Boolean clienteExiste = repository.exists(cliente.getCpf()); 
		if (isUpdate && !clienteExiste) {
			log.error("Registro informado para atualização não existe. Id = {}.", cliente.getCpf());
			throw new ServiceException("Registro informado para atualização não existe.", TipoExcecao.REGISTRO_NAO_ENCONTRADO);
		}
		if (!isUpdate && clienteExiste) {
			log.error("Registro informado para inclusão já existe. Id = {}.", cliente.getCpf());
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
