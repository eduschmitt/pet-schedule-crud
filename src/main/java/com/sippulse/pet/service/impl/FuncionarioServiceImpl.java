package com.sippulse.pet.service.impl;

import static com.sippulse.pet.utils.ValidacaoUtil.isValidCpf;
import static com.sippulse.pet.utils.ValidacaoUtil.isValidString;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sippulse.pet.entity.Funcionario;
import com.sippulse.pet.exception.ServiceException;
import com.sippulse.pet.exception.ServiceException.TipoExcecao;
import com.sippulse.pet.repository.FuncionarioRepository;
import com.sippulse.pet.service.FuncionarioService;

import lombok.extern.slf4j.Slf4j;

/**
 * Camada de service para o CRUD de Funcionarios
 * @author eduardo
 *
 */
@Service
@Slf4j
public class FuncionarioServiceImpl implements FuncionarioService {

	private FuncionarioRepository repository;

	@Autowired
	public FuncionarioServiceImpl(FuncionarioRepository repository) {
		super();
		this.repository = repository;
	}

	/**
	 * Busca todos os funcionarios existentes na base de dados.
	 * 
	 * @return Lista com os funcionarios encontrados.
	 */
	@Override
	public List<Funcionario> findAll() {
		log.debug("Buscando todos os funcionarios.");
		List<Funcionario> funcionarios = (List<Funcionario>) repository.findAll();
		if (funcionarios == null) {
			log.error("Nenhum registro encontrado.");
			throw new ServiceException("Nenhum registro encontrado.", TipoExcecao.REGISTRO_NAO_ENCONTRADO);
		}
		return (List<Funcionario>) repository.findAll();
	}

	/**
	 * Busca um funcionario pelo seu id.
	 * 
	 * @param Id do funcionario.
	 * @return Funcionario encontrado com o id informado. 
	 * @throws ServiceException
	 */
	@Override
	public Funcionario findById(Long id) {
		log.debug("Buscando o funcionario de id {}", id);
		if (id == null) {
			log.error("Id do registro não informado.");
			throw new ServiceException("Id do registro não informado.", TipoExcecao.VALIDACAO_CAMPOS);
		}
		Funcionario c = repository.findOne(id);
		if (c == null) {
			log.error("Registro de id {} não encontrado.", id);
			throw new ServiceException("Registro de id " + id + " não encontrado.", TipoExcecao.REGISTRO_NAO_ENCONTRADO);
		}
		return c;
	}

	/**
	 * Salva ou atualiza um funcionario.
	 * 
	 * @param Funcionario a ser salvo ou atualizado.
	 * @return Funcionario salvo ou atualizado.
	 * @throws ServiceException 
	 */
	@Override
	public Funcionario save(Funcionario funcionario, Boolean isUpdate) {
		log.debug("Invocando o método para {}.", isUpdate ? "atualização" : "inclusão");
		if (funcionario.getCpf() == null || !isValidString(funcionario.getNome()) || funcionario.getTipoFuncionario() == null) {
			throw new ServiceException("Os atributos CPF, nome e tipo são obrigatórios.", TipoExcecao.VALIDACAO_CAMPOS);
		}
		if (!isValidCpf(funcionario.getCpf().toString())) {
			throw new ServiceException("CPF inválido.", TipoExcecao.VALIDACAO_CAMPOS);
		}
		Boolean funcionarioExiste = repository.exists(funcionario.getCpf()); 
		if (isUpdate && !funcionarioExiste) {
			log.error("Registro informado para atualização não existe. Id = {}.", funcionario.getCpf());
			throw new ServiceException("Registro informado para atualização não existe.", TipoExcecao.REGISTRO_NAO_ENCONTRADO);
		}
		if (!isUpdate && funcionarioExiste) {
			log.error("Registro informado para inclusão já existe. Id = {}.", funcionario.getCpf());
			throw new ServiceException("Registro informado para inclusão já existe.", TipoExcecao.VALIDACAO_CAMPOS);
		}
		
		return repository.save(funcionario);
	}

	/**
	 * Realiza a exclusão de um funcionario.
	 * 
	 * @param Id do funcionario a ser excluído.
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
