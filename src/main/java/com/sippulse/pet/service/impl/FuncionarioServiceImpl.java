package com.sippulse.pet.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sippulse.pet.entity.Funcionario;
import com.sippulse.pet.exception.ServiceException;
import com.sippulse.pet.exception.ServiceException.TipoExcecao;
import com.sippulse.pet.repository.FuncionarioRepository;
import com.sippulse.pet.service.FuncionarioService;

/**
 * Camada de service para o CRUD de Funcionarios
 * @author eduardo
 *
 */
@Service
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
		List<Funcionario> funcionarios = (List<Funcionario>) repository.findAll();
		if (funcionarios == null) {
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
		if (id == null) {
			throw new ServiceException("Id do registro não informado.", TipoExcecao.VALIDACAO_CAMPOS);
		}
		Funcionario c = repository.findOne(id);
		if (c == null) {
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
		if (funcionario.getCpf() == null || funcionario.getNome() == null || funcionario.getNome().trim().isEmpty()) {
			throw new ServiceException("Os atributos CPF e nome são obrigatórios.", TipoExcecao.VALIDACAO_CAMPOS);
		}
		Boolean funcionarioExiste = repository.exists(funcionario.getCpf()); 
		if (isUpdate && !funcionarioExiste) {
			throw new ServiceException("Registro informado para atualização não existe.", TipoExcecao.REGISTRO_NAO_ENCONTRADO);
		}
		if (!isUpdate && funcionarioExiste) {
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
		if (id == null) {
			throw new ServiceException("Id do registro não informado.", TipoExcecao.VALIDACAO_CAMPOS);
		}
		if (!repository.exists(id)) {
			throw new ServiceException("Registro informado para exclusão não existe.", TipoExcecao.REGISTRO_NAO_ENCONTRADO);
		}
		repository.delete(id);
	}


}
