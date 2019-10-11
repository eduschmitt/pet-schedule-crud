package com.sippulse.pet.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sippulse.pet.entity.Consulta;
import com.sippulse.pet.exception.ServiceException;
import com.sippulse.pet.exception.ServiceException.TipoExcecao;
import com.sippulse.pet.repository.ConsultaRepository;
import com.sippulse.pet.service.ConsultaService;

/**
 * Camada de service para o CRUD de Consultas
 * 
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
		List<Consulta> consultas = (List<Consulta>) repository.findAll();
		if (consultas == null) {
			throw new ServiceException("Nenhum registro encontrado.", TipoExcecao.REGISTRO_NAO_ENCONTRADO);
		}
		return (List<Consulta>) repository.findAll();
	}

	/**
	 * Busca um consulta pelo seu id.
	 * 
	 * @param Id do consulta.
	 * @return Consulta encontrado com o id informado.
	 * @throws ServiceException
	 */
	@Override
	public Consulta findById(Long id) {
		if (id == null) {
			throw new ServiceException("Id do registro não informado.", TipoExcecao.VALIDACAO_CAMPOS);
		}
		Consulta c = repository.findOne(id);
		if (c == null) {
			throw new ServiceException("Registro de id " + id + " não encontrado.",
					TipoExcecao.REGISTRO_NAO_ENCONTRADO);
		}
		return c;
	}

	/**
	 * Salva ou atualiza um consulta.
	 * 
	 * @param Consulta a ser salvo ou atualizado.
	 * @return Consulta salvo ou atualizado.
	 * @throws ServiceException
	 */
	@Override
	public Consulta save(Consulta consulta, Boolean isUpdate) {
//		if (consulta.getCpf() == null || consulta.getNome() == null || consulta.getNome().trim().isEmpty()) {
//			throw new ServiceException("Os atributos CPF e nome são obrigatórios.", TipoExcecao.VALIDACAO_CAMPOS);
//		}
		Boolean consultaExiste = repository.exists(consulta.getId());
		if (isUpdate && !consultaExiste) {
			throw new ServiceException("Registro informado para atualização não existe.",
					TipoExcecao.REGISTRO_NAO_ENCONTRADO);
		}
		if (!isUpdate && consultaExiste) {
			throw new ServiceException("Registro informado para inclusão já existe.", TipoExcecao.VALIDACAO_CAMPOS);
		}

		return repository.save(consulta);
	}

	/**
	 * Realiza a exclusão de um consulta.
	 * 
	 * @param Id do consulta a ser excluído.
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
