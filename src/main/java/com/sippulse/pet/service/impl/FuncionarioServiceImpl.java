package com.sippulse.pet.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sippulse.pet.entity.Funcionario;
import com.sippulse.pet.exception.NegocioException;
import com.sippulse.pet.exception.NegocioException.TipoExcecao;
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
		return (List<Funcionario>) repository.findAll();
	}

	/**
	 * Busca um funcionario pelo seu id.
	 * 
	 * @param Id do funcionario.
	 * @return Funcionario encontrado com o id informado. 
	 * @throws NegocioException
	 */
	@Override
	public Funcionario findById(Long id) throws NegocioException {
		if (id == null) {
			throw new NegocioException("Id do funcionario não informado.", TipoExcecao.VALIDACAO_CAMPOS);
		}
		return repository.findOne(id);
	}

	/**
	 * Salva ou atualiza um funcionario.
	 * 
	 * @param Funcionario a ser salvo ou atualizado.
	 * @return Funcionario salvo ou atualizado.
	 * @throws NegocioException 
	 */
	@Override
	public Funcionario save(Funcionario funcionario, Boolean isUpdate) throws NegocioException {
		if (funcionario.getCpf() == null || funcionario.getNome() == null || funcionario.getNome().trim().isEmpty()) {
			throw new NegocioException("Os atributos CPF e nome são obrigatórios.", TipoExcecao.VALIDACAO_CAMPOS);
		}
		Boolean funcionarioExiste = repository.exists(funcionario.getCpf()); 
		if (isUpdate && !funcionarioExiste) {
			throw new NegocioException("Funcionario informado não existe.", TipoExcecao.REGISTRO_NAO_ENCONTRADO);
		}
		if (!isUpdate && funcionarioExiste) {
			throw new NegocioException("Funcionario informado já existe.", TipoExcecao.VALIDACAO_CAMPOS);
		}
		
		return repository.save(funcionario);
	}

	/**
	 * Realiza a exclusão de um funcionario.
	 * 
	 * @param Id do funcionario a ser excluído.
	 * @throws NegocioException
	 */
	@Override
	public void delete(Long id) throws NegocioException {
		if (id == null) {
			throw new NegocioException("Id do funcionario não informado.", TipoExcecao.VALIDACAO_CAMPOS);
		}
		if (!repository.exists(id)) {
			throw new NegocioException("Funcionario informado não existe.", TipoExcecao.REGISTRO_NAO_ENCONTRADO);
		}
		repository.delete(id);
	}


}
