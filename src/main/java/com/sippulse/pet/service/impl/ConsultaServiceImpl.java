package com.sippulse.pet.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sippulse.pet.entity.Consulta;
import com.sippulse.pet.exception.NegocioException;
import com.sippulse.pet.exception.NegocioException.TipoExcecao;
import com.sippulse.pet.repository.ConsultaRepository;
import com.sippulse.pet.service.ConsultaService;

/**
 * Camada de service para o CRUD de Consultas
 * @author eduardo
 *
 */
@Service
public class ConsultaServiceImpl implements ConsultaService {

	private ConsultaRepository repository;

	@Autowired
	public ConsultaServiceImpl(ConsultaRepository repository) {
		super();
		this.repository = repository;
	}

	/**
	 * Busca todos os consultas existentes na base de dados.
	 * 
	 * @return Lista com os consultas encontrados.
	 */
	@Override
	public List<Consulta> findAll() {
		return (List<Consulta>) repository.findAll();
	}

	/**
	 * Busca um consulta pelo seu id.
	 * 
	 * @param Id do consulta.
	 * @return Consulta encontrado com o id informado. 
	 * @throws NegocioException
	 */
	@Override
	public Consulta findById(Long id) throws NegocioException {
		if (id == null) {
			throw new NegocioException("Id do consulta não informado.", TipoExcecao.VALIDACAO_CAMPOS);
		}
		return repository.findOne(id);
	}

	/**
	 * Salva ou atualiza um consulta.
	 * 
	 * @param Consulta a ser salvo ou atualizado.
	 * @return Consulta salvo ou atualizado.
	 * @throws NegocioException 
	 */
	@Override
	public Consulta save(Consulta consulta, Boolean isUpdate) throws NegocioException {
		if (consulta.getId() == null) {
			throw new NegocioException("Os atributos CPF e nome são obrigatórios.", TipoExcecao.VALIDACAO_CAMPOS);
		}
		Boolean consultaExiste = repository.exists(consulta.getId()); 
		if (isUpdate && !consultaExiste) {
			throw new NegocioException("Consulta informado não existe.", TipoExcecao.REGISTRO_NAO_ENCONTRADO);
		}
		if (!isUpdate && consultaExiste) {
			throw new NegocioException("Consulta informado já existe.", TipoExcecao.VALIDACAO_CAMPOS);
		}
		
		return repository.save(consulta);
	}

	/**
	 * Realiza a exclusão de um consulta.
	 * 
	 * @param Id do consulta a ser excluído.
	 * @throws NegocioException
	 */
	@Override
	public void delete(Long id) throws NegocioException {
		if (id == null) {
			throw new NegocioException("Id do consulta não informado.", TipoExcecao.VALIDACAO_CAMPOS);
		}
		if (!repository.exists(id)) {
			throw new NegocioException("Consulta informado não existe.", TipoExcecao.REGISTRO_NAO_ENCONTRADO);
		}
		repository.delete(id);
	}


}
