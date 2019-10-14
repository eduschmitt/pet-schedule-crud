package com.sippulse.pet.service.impl;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

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
import com.sippulse.pet.exception.ServiceException;
import com.sippulse.pet.repository.ConsultaRepository;
import com.sippulse.pet.repository.FuncionarioRepository;
import com.sippulse.pet.repository.PetRepository;

@RunWith(MockitoJUnitRunner.class)
public class ConsultaServiceImplTest {
	
	Long idTesteOk = 1l; 
	Funcionario veterinarioOk = new Funcionario();
	Pet petOk = new Pet();
	LocalDateTime ldtAgora = LocalDateTime.now();

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
		petOk.setId(1l);
		consultaService = new ConsultaServiceImpl(consultaRepository, petRespository, funcionarioRepository);
		MockitoAnnotations.initMocks(consultaService);
	}
	
	
	@Test
	public void findAll_EncontraUmRegistro_RetornoOk() {
		when(consultaRepository.findAll()).thenReturn(retornaLista(idTesteOk, veterinarioOk, petOk, ldtAgora));		
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
		when(consultaRepository.findOne(idTesteOk)).thenReturn(retornaConsulta(idTesteOk, veterinarioOk, petOk, ldtAgora));
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
	
	
//	@Test
//	public void save_SalvandoConsulta_RetornoOk() {
//		
//		when(consultaRepository.exists(idTesteOk)).thenReturn(false);
//		when(consultaRepository.save(any(Consulta.class))).thenReturn(retornaConsulta(idTesteOk, veterinarioOk, petOk, ldtAgora));
//		Consulta consulta = null;
//		try {
//			consulta = consultaService.save(retornaConsulta(null, veterinarioOk, petOk, ldtAgora), false);			
//		} catch (ServiceException e) {			
//			Assert.fail("Não poderia lançar uma exceção.");
//		}
//		Assert.assertEquals(idTesteOk, consulta.getId());
//	}
	
	
	
	
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
