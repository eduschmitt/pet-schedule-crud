package com.sippulse.pet.service.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sippulse.pet.entity.Consulta;
import com.sippulse.pet.entity.enums.TipoFuncionario;
import com.sippulse.pet.exception.ServiceException;
import com.sippulse.pet.exception.ServiceException.TipoExcecao;
import com.sippulse.pet.repository.ConsultaRepository;
import com.sippulse.pet.repository.FuncionarioRepository;
import com.sippulse.pet.repository.PetRepository;
import com.sippulse.pet.service.ConsultaService;

import lombok.extern.slf4j.Slf4j;

/**
 * Camada de service para o CRUD de Consultas
 * 
 * @author eduardo
 *
 */
@Service
@Slf4j
public class ConsultaServiceImpl implements ConsultaService {

	private ConsultaRepository repository;
	
	private PetRepository petRepository;
	
	private FuncionarioRepository funcionarioRepository;

	@Autowired
	public ConsultaServiceImpl(ConsultaRepository repository, PetRepository petRepository, FuncionarioRepository funcionarioRepository) {
		super();
		this.repository = repository;
		this.petRepository = petRepository;
		this.funcionarioRepository = funcionarioRepository;
	}

	/**
	 * Busca todos os consultas existentes na base de dados.
	 * 
	 * @return Lista com os consultas encontrados.
	 */
	@Override
	public List<Consulta> findAll() {
		log.debug("Buscando todos os consultas.");
		List<Consulta> consultas = (List<Consulta>) repository.findAll();
		if (consultas == null) {
			log.error("Nenhum registro encontrado.");
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
		log.debug("Buscando o consulta de id {}", id);
		if (id == null) {
			log.error("Id do registro não informado.");
			throw new ServiceException("Id do registro não informado.", TipoExcecao.VALIDACAO_CAMPOS);
		}
		Consulta c = repository.findOne(id);
		if (c == null) {
			log.error("Registro de id {} não encontrado.", id);
			throw new ServiceException("Registro de id " + id + " não encontrado.",
					TipoExcecao.REGISTRO_NAO_ENCONTRADO);
		}
		return c;
	}

	public List<Consulta> findByVeterinarioData(Long cpfVeterinario, LocalDate dataConsulta) {

		if (cpfVeterinario != null && dataConsulta != null) {
			log.error("Data inicio: {}", dataConsulta.atStartOfDay());
			log.error("Data Fim: {}", dataConsulta.plusDays(1).atStartOfDay().minusMinutes(1));
			return repository.findAllByVeterinarioCpfAndDataHoraBetween(cpfVeterinario, dataConsulta.atStartOfDay(),
					dataConsulta.plusDays(1).atStartOfDay().minusMinutes(1));
		} else if (cpfVeterinario != null) {
			return repository.findAllByVeterinarioCpf(cpfVeterinario);
		} else if (dataConsulta != null) {
			log.error("Data inicio: {}", dataConsulta.atStartOfDay());
			log.error("Data Fim: {}", dataConsulta.plusDays(1).atStartOfDay().minusMinutes(1));
			return repository.findAllByDataHoraBetween(dataConsulta.atStartOfDay(),
					dataConsulta.plusDays(1).atStartOfDay().minusMinutes(1));
		} else {
			throw new ServiceException("É necessário informar o veterinário e/ou a data.",
					TipoExcecao.VALIDACAO_CAMPOS);
		}
	}

	public List<Consulta> findByCliente(Long cpfCliente) {
		return repository.findAllByPetClienteCpf(cpfCliente);
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
		log.debug("Invocando o método para {}.", isUpdate ? "atualização" : "inclusão");
		if (isUpdate && (consulta.getId() == null || !repository.exists(consulta.getId()))) {
			throw new ServiceException("Na atualização, é necessário informar o id de um registro existente.",
					TipoExcecao.VALIDACAO_CAMPOS);
		}

		if (consulta.getVeterinario() == null || consulta.getVeterinario().getCpf() == null || consulta.getPet() == null
				|| consulta.getPet().getId() == null || consulta.getDataHora() == null) {
			throw new ServiceException("Os campos veterinário, pet e horário da consulta são obrigatórios.",
					TipoExcecao.VALIDACAO_CAMPOS);
		}
		
		if (!petRepository.exists(consulta.getPet().getId())) {
			throw new ServiceException("O Pet informado não existe.",
					TipoExcecao.VALIDACAO_CAMPOS);
		}
		
		if (funcionarioRepository.findOneByCpfAndTipoFuncionario(consulta.getVeterinario().getCpf(), TipoFuncionario.VETERINARIO) == null) {
			throw new ServiceException("O veterinário informado não existe.",
					TipoExcecao.VALIDACAO_CAMPOS);
		}

		Consulta consultaMarcada = repository.findOneByVeterinarioCpfAndDataHora(consulta.getVeterinario().getCpf(), consulta.getDataHora());
		if (consultaMarcada != null) {
			if (!isUpdate || (consultaMarcada.getId() != consulta.getId())) {
				throw new ServiceException("Não pode ser marcada mais de uma consulta no mesmo horário com o mesmo veterinário.",
						TipoExcecao.VALIDACAO_CAMPOS);
			}
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
		log.debug("Invocando o método para exclusão.");
		if (id == null) {
			log.error("Id do registro não informado.");
			throw new ServiceException("Id do registro não informado.", TipoExcecao.VALIDACAO_CAMPOS);
		}
		if (!repository.exists(id)) {
			log.error("Registro de id {} não encontrado.", id);
			throw new ServiceException("Registro informado para exclusão não existe.",
					TipoExcecao.REGISTRO_NAO_ENCONTRADO);
		}
		repository.delete(id);
	}

}
