package com.sippulse.pet.service.impl;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import com.sippulse.pet.entity.Consulta;
import com.sippulse.pet.entity.Funcionario;
import com.sippulse.pet.entity.Pet;
import com.sippulse.pet.entity.enums.TipoFuncionario;
import com.sippulse.pet.exception.ServiceException;
import com.sippulse.pet.repository.ConsultaRepository;
import com.sippulse.pet.repository.FuncionarioRepository;
import com.sippulse.pet.repository.PetRepository;

@RunWith(MockitoJUnitRunner.class)
public class ConsultaServiceImplTest {

	Long idTesteOk = 1l;
	Funcionario veterinarioOk = new Funcionario();
	Pet petOk = new Pet();
	LocalDateTime dataHoraAtual = LocalDateTime.now();
	LocalDate dataAtual = LocalDate.now();

	@Mock
	private ConsultaRepository consultaRepository;

	@Mock
	private PetRepository petRespository;

	@Mock
	private FuncionarioRepository funcionarioRepository;

	@InjectMocks
	private ConsultaServiceImpl consultaService;

	@Before
	public void setUp() {
		veterinarioOk.setCpf(12345678901l);
		veterinarioOk.setTipoFuncionario(TipoFuncionario.VETERINARIO);
		petOk.setId(1l);
		consultaService = new ConsultaServiceImpl(consultaRepository, petRespository, funcionarioRepository);
		MockitoAnnotations.initMocks(consultaService);
	}

	@Test
	public void findAll_EncontraUmRegistro_RetornoOk() {
		when(consultaRepository.findAll()).thenReturn(retornaLista(idTesteOk, veterinarioOk, petOk, dataHoraAtual));
		List<Consulta> consultasRetornados = consultaService.findAll();
		Assert.assertEquals(1, consultasRetornados.size());
	}

	@Test
	public void findAll_NaoEncontraRegistros_LancaExcecao() {
		when(consultaRepository.findAll()).thenReturn(null);
		try {
			consultaService.findAll();
		} catch (ServiceException e) {
			Assert.assertEquals("Nenhum registro encontrado.", e.getMessage());
			return;
		}
		Assert.fail("Deveria ter lançado exceção.");
	}

	@Test
	public void findById_EncontraRegistro_RetornoOk() {
		when(consultaRepository.findOne(idTesteOk))
				.thenReturn(retornaConsulta(idTesteOk, veterinarioOk, petOk, dataHoraAtual));
		Consulta consulta = consultaService.findById(idTesteOk);
		Assert.assertEquals(idTesteOk, consulta.getId());
	}

	@Test
	public void findById_IdInformadoNulo_LancaExcecao() {
		try {
			consultaService.findById(null);
		} catch (ServiceException e) {
			Assert.assertEquals("Id do registro não informado.", e.getMessage());
			return;
		}
		Assert.fail("Deveria ter lançado exceção.");
	}

	@Test
	public void findById_ConsultaNaoEncontrado_LancaExcecao() {
		when(consultaRepository.findOne(idTesteOk)).thenReturn(null);
		try {
			consultaService.findById(idTesteOk);
		} catch (ServiceException e) {
			Assert.assertEquals("Registro de id " + idTesteOk + " não encontrado.", e.getMessage());
			return;
		}
		Assert.fail("Deveria ter lançado exceção.");
	}

	@Test
	public void findByVeterinarioData_ConsultaPorCpfeData_RetornoOk() {
		when(consultaRepository.findAllByVeterinarioCpfAndDataHoraBetween(veterinarioOk.getCpf(),
				dataAtual.atStartOfDay(), dataAtual.plusDays(1).atStartOfDay().minusMinutes(1)))
						.thenReturn(retornaLista(idTesteOk, veterinarioOk, petOk, dataHoraAtual));
		List<Consulta> consultas = consultaService.findByVeterinarioData(veterinarioOk.getCpf(), dataAtual);
		Assert.assertTrue(!consultas.isEmpty());
	}

	@Test
	public void findByVeterinarioData_ConsultaSomentePorCpf_RetornoOk() {
		when(consultaRepository.findAllByVeterinarioCpf(veterinarioOk.getCpf()))
				.thenReturn(retornaLista(idTesteOk, veterinarioOk, petOk, dataHoraAtual));
		List<Consulta> consultas = consultaService.findByVeterinarioData(veterinarioOk.getCpf(), null);
		Assert.assertTrue(!consultas.isEmpty());
	}

	@Test
	public void findByVeterinarioData_ConsultaSomentePorData_RetornoOk() {
		when(consultaRepository.findAllByDataHoraBetween(dataAtual.atStartOfDay(),
				dataAtual.plusDays(1).atStartOfDay().minusMinutes(1)))
						.thenReturn(retornaLista(idTesteOk, veterinarioOk, petOk, dataHoraAtual));
		List<Consulta> consultas = consultaService.findByVeterinarioData(null, dataAtual);
		Assert.assertTrue(!consultas.isEmpty());
	}

	@Test
	public void findByVeterinarioData_ConsultaSemParametros_LancaExcecao() {
		try {
			consultaService.findByVeterinarioData(null, null);
		} catch (ServiceException e) {
			Assert.assertEquals("É necessário informar o veterinário e/ou a data.", e.getMessage());
			return;
		}
		Assert.fail("Deveria ter lançado exceção.");
	}

	@Test
	public void findByCliente_ConsultaComCpf_RetornoOk() {
		when(consultaRepository.findAllByPetClienteCpf(veterinarioOk.getCpf()))
				.thenReturn(retornaLista(idTesteOk, veterinarioOk, petOk, dataHoraAtual));
		List<Consulta> consultas = consultaService.findByCliente(veterinarioOk.getCpf());
		Assert.assertTrue(!consultas.isEmpty());
	}

	@Test
	public void findByCliente_ConsultaSemCpf_LancaExcecao() {
		try {
			consultaService.findByCliente(null);
		} catch (ServiceException e) {
			Assert.assertEquals("Deve ser informado o cpf do cliente.", e.getMessage());
			return;
		}
		Assert.fail("Deveria ter lançado exceção.");
	}

	@Test
	public void findByCliente_ConsultaCpfNaoEncontrado_LancaExcecao() {
		when(consultaRepository.findAllByPetClienteCpf(veterinarioOk.getCpf())).thenReturn(null);
		try {
			consultaService.findByCliente(veterinarioOk.getCpf());
		} catch (ServiceException e) {
			Assert.assertEquals("Nenhum registro encontrado.", e.getMessage());
			return;
		}
		Assert.fail("Deveria ter lançado exceção.");
	}

	@Test
	public void save_SalvandoConsulta_RetornoOk() {
		when(consultaRepository.exists(idTesteOk)).thenReturn(false);
		when(petRespository.exists(petOk.getId())).thenReturn(true);
		when(funcionarioRepository.findOneByCpfAndTipoFuncionario(veterinarioOk.getCpf(),
				veterinarioOk.getTipoFuncionario())).thenReturn(veterinarioOk);
		when(consultaRepository.findOneByVeterinarioCpfAndDataHora(veterinarioOk.getCpf(), dataHoraAtual))
				.thenReturn(null);
		when(consultaRepository.save(any(Consulta.class)))
				.thenReturn(retornaConsulta(idTesteOk, veterinarioOk, petOk, dataHoraAtual));
		Consulta consulta = null;
		try {
			consulta = consultaService.save(retornaConsulta(null, veterinarioOk, petOk, dataHoraAtual), false);
		} catch (ServiceException e) {
			Assert.fail("Não poderia lançar uma exceção.");
		}
		Assert.assertEquals(idTesteOk, consulta.getId());
	}

	@Test
	public void save_SalvandoConsultaSemVeterinario_LancaExcecao() {
		try {
			consultaService.save(retornaConsulta(null, null, petOk, dataHoraAtual), false);
		} catch (ServiceException e) {
			Assert.assertEquals("Os campos veterinário, pet e horário da consulta são obrigatórios.", e.getMessage());
			return;
		}
		Assert.fail("Deveria ter lançado exceção.");
	}

	@Test
	public void save_SalvandoConsultaVeterinarioSemCpf_LancaExcecao() {
		try {
			consultaService.save(retornaConsulta(null, new Funcionario(), petOk, dataHoraAtual), false);
		} catch (ServiceException e) {
			Assert.assertEquals("Os campos veterinário, pet e horário da consulta são obrigatórios.", e.getMessage());
			return;
		}
		Assert.fail("Deveria ter lançado exceção.");
	}

	@Test
	public void save_SalvandoConsultaSemPet_LancaExcecao() {
		try {
			consultaService.save(retornaConsulta(null, veterinarioOk, null, dataHoraAtual), false);
		} catch (ServiceException e) {
			Assert.assertEquals("Os campos veterinário, pet e horário da consulta são obrigatórios.", e.getMessage());
			return;
		}
		Assert.fail("Deveria ter lançado exceção.");
	}

	@Test
	public void save_SalvandoConsultaPetSemId_LancaExcecao() {
		try {
			consultaService.save(retornaConsulta(null, veterinarioOk, new Pet(), dataHoraAtual), false);
		} catch (ServiceException e) {
			Assert.assertEquals("Os campos veterinário, pet e horário da consulta são obrigatórios.", e.getMessage());
			return;
		}
		Assert.fail("Deveria ter lançado exceção.");
	}

	@Test
	public void save_SalvandoConsultaSemDataHora_LancaExcecao() {
		try {
			consultaService.save(retornaConsulta(null, veterinarioOk, petOk, null), false);
		} catch (ServiceException e) {
			Assert.assertEquals("Os campos veterinário, pet e horário da consulta são obrigatórios.", e.getMessage());
			return;
		}
		Assert.fail("Deveria ter lançado exceção.");
	}

	@Test
	public void save_SalvandoConsultaPetInexistente_LancaExcecao() {
		when(petRespository.exists(petOk.getId())).thenReturn(false);
		try {
			consultaService.save(retornaConsulta(null, veterinarioOk, petOk, dataHoraAtual), false);
		} catch (ServiceException e) {
			Assert.assertEquals("O Pet informado não existe.", e.getMessage());
			return;
		}
		Assert.fail("Deveria ter lançado exceção.");
	}

	@Test
	public void save_SalvandoConsultaVeterinarioInexistente_LancaExcecao() {
		when(petRespository.exists(petOk.getId())).thenReturn(true);
		when(funcionarioRepository.findOneByCpfAndTipoFuncionario(veterinarioOk.getCpf(),
				veterinarioOk.getTipoFuncionario())).thenReturn(null);
		try {
			consultaService.save(retornaConsulta(null, veterinarioOk, petOk, dataHoraAtual), false);
		} catch (ServiceException e) {
			Assert.assertEquals("O veterinário informado não existe.", e.getMessage());
			return;
		}
		Assert.fail("Deveria ter lançado exceção.");
	}

	@Test
	public void save_SalvandoConsultaMesmoVeterinarioMesmoHorario_LancaExcecao() {
		when(petRespository.exists(petOk.getId())).thenReturn(true);
		when(funcionarioRepository.findOneByCpfAndTipoFuncionario(veterinarioOk.getCpf(),
				veterinarioOk.getTipoFuncionario())).thenReturn(veterinarioOk);
		when(consultaRepository.findOneByVeterinarioCpfAndDataHora(veterinarioOk.getCpf(), dataHoraAtual))
				.thenReturn(new Consulta());
		try {
			consultaService.save(retornaConsulta(null, veterinarioOk, petOk, dataHoraAtual), false);
		} catch (ServiceException e) {
			Assert.assertEquals("Não pode ser marcada mais de uma consulta no mesmo horário com o mesmo veterinário.",
					e.getMessage());
			return;
		}
		Assert.fail("Deveria ter lançado exceção.");
	}

	@Test
	public void save_AtualizandoConsulta_RetornoOk() {
		when(consultaRepository.exists(idTesteOk)).thenReturn(true);
		when(petRespository.exists(petOk.getId())).thenReturn(true);
		when(funcionarioRepository.findOneByCpfAndTipoFuncionario(veterinarioOk.getCpf(),
				veterinarioOk.getTipoFuncionario())).thenReturn(veterinarioOk);
		Consulta consultaExistente = new Consulta();
		consultaExistente.setId(idTesteOk);
		when(consultaRepository.findOneByVeterinarioCpfAndDataHora(veterinarioOk.getCpf(), dataHoraAtual))
				.thenReturn(consultaExistente);
		when(consultaRepository.save(any(Consulta.class)))
				.thenReturn(retornaConsulta(idTesteOk, veterinarioOk, petOk, dataHoraAtual));
		Consulta consulta = null;
		try {
			consulta = consultaService.save(retornaConsulta(idTesteOk, veterinarioOk, petOk, dataHoraAtual), true);
		} catch (ServiceException e) {
			Assert.fail("Não poderia lançar uma exceção.");
		}
		Assert.assertEquals(idTesteOk, consulta.getId());
	}

	@Test
	public void save_AtualizandoConsultaDuplicandoHorario_LancaExcecao() {
		when(consultaRepository.exists(idTesteOk)).thenReturn(true);
		when(petRespository.exists(petOk.getId())).thenReturn(true);
		when(funcionarioRepository.findOneByCpfAndTipoFuncionario(veterinarioOk.getCpf(),
				veterinarioOk.getTipoFuncionario())).thenReturn(veterinarioOk);
		Consulta consultaExistente = new Consulta();
		consultaExistente.setId(idTesteOk + 1);
		when(consultaRepository.findOneByVeterinarioCpfAndDataHora(veterinarioOk.getCpf(), dataHoraAtual))
				.thenReturn(consultaExistente);
		when(consultaRepository.save(any(Consulta.class)))
				.thenReturn(retornaConsulta(idTesteOk, veterinarioOk, petOk, dataHoraAtual));
		try {
			consultaService.save(retornaConsulta(idTesteOk, veterinarioOk, petOk, dataHoraAtual), true);
		} catch (ServiceException e) {
			Assert.assertEquals("Não pode ser marcada mais de uma consulta no mesmo horário com o mesmo veterinário.",
					e.getMessage());
			return;
		}
		Assert.fail("Deveria ter lançado exceção.");
	}

	@Test
	public void save_AtualizandoConsultaSemInformarId_LancaExcecao() {
		try {
			consultaService.save(retornaConsulta(null, veterinarioOk, petOk, dataHoraAtual), true);
		} catch (ServiceException e) {
			Assert.assertEquals("Na atualização, é necessário informar o id de um registro existente.", e.getMessage());
			return;
		}
		Assert.fail("Deveria ter lançado exceção.");
	}

	@Test
	public void save_AtualizandoConsultaIdInexistente_LancaExcecao() {
		when(consultaRepository.exists(idTesteOk)).thenReturn(false);
		try {
			consultaService.save(retornaConsulta(idTesteOk, veterinarioOk, petOk, dataHoraAtual), true);
		} catch (ServiceException e) {
			Assert.assertEquals("Na atualização, é necessário informar o id de um registro existente.", e.getMessage());
			return;
		}
		Assert.fail("Deveria ter lançado exceção.");
	}

	@Test
	public void delete_EncontraRegistro_RetornoOk() {
		when(consultaRepository.exists(idTesteOk)).thenReturn(true);
		try {
			consultaService.delete(idTesteOk);
		} catch (Exception e) {
			Assert.fail("Não poderia lançar uma exceção.");
		}
	}

	@Test
	public void delete_IdInformadoNulo_LancaExcecao() {
		try {
			consultaService.delete(null);
		} catch (ServiceException e) {
			Assert.assertEquals("Id do registro não informado.", e.getMessage());
			return;
		}
		Assert.fail("Deveria ter lançado exceção.");
	}

	@Test
	public void delete_ConsultaNaoEncontrado_LancaExcecao() {
		when(consultaRepository.exists(idTesteOk)).thenReturn(false);
		try {
			consultaService.delete(idTesteOk);
		} catch (ServiceException e) {
			Assert.assertEquals("Registro informado para exclusão não existe.", e.getMessage());
			return;
		}
		Assert.fail("Deveria ter lançado exceção.");
	}

	private List<Consulta> retornaLista(Long id, Funcionario veterinario, Pet pet, LocalDateTime dataHora) {
		return Arrays.asList(retornaConsulta(id, veterinario, pet, dataHora));
	}

	private Consulta retornaConsulta(Long id, Funcionario veterinario, Pet pet, LocalDateTime dataHora) {
		Consulta consulta = new Consulta();
		consulta.setId(id);
		consulta.setVeterinario(veterinario);
		consulta.setPet(pet);
		consulta.setDataHora(dataHora);

		return consulta;
	}
}
