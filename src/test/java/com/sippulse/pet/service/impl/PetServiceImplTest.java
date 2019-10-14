package com.sippulse.pet.service.impl;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

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

import com.sippulse.pet.entity.Cliente;
import com.sippulse.pet.entity.Pet;
import com.sippulse.pet.exception.ServiceException;
import com.sippulse.pet.repository.PetRepository;

@RunWith(MockitoJUnitRunner.class)
public class PetServiceImplTest {
	
	Long idTesteOk = 1l; 
	String nomeOk = "PET";
	Cliente clienteOk = new Cliente();

	@Mock
	private PetRepository petRepository;
	
	@InjectMocks
	private PetServiceImpl petService;
	
	@Before
	public void setUp() {	
		clienteOk.setCpf(12345678901l);
		petService = new PetServiceImpl(petRepository);
		MockitoAnnotations.initMocks(petService);
	}
	
	
	@Test
	public void findAll_EncontraUmRegistro_RetornoOk() {
		when(petRepository.findAll()).thenReturn(retornaLista(idTesteOk, nomeOk, clienteOk));		
		List<Pet> petsRetornados = petService.findAll();		
		Assert.assertEquals(1, petsRetornados.size());
	}
	
	@Test
	public void findAll_NaoEncontraRegistros_LancaExcecao() {
		when(petRepository.findAll()).thenReturn(null);
		try {
			petService.findAll();			
		} catch (ServiceException e) {
			Assert.assertEquals("Nenhum registro encontrado.", e.getMessage());
			return;
		}
		Assert.fail("Deveria ter lançado exceção.");
	}
	
	@Test
	public void findById_EncontraRegistro_RetornoOk() {
		when(petRepository.findOne(idTesteOk)).thenReturn(retornaPet(idTesteOk, nomeOk, clienteOk));
		Pet pet = petService.findById(idTesteOk);
		Assert.assertEquals(idTesteOk, pet.getId());
	}
	
	@Test
	public void findById_IdInformadoNulo_LancaExcecao() {		
		try {
			petService.findById(null);			
		} catch (ServiceException e) {
			Assert.assertEquals("Id do registro não informado.", e.getMessage());
			return;
		}
		Assert.fail("Deveria ter lançado exceção.");
	}
	
	@Test
	public void findById_PetNaoEncontrado_LancaExcecao() {	
		when(petRepository.findOne(idTesteOk)).thenReturn(null);
		try {
			petService.findById(idTesteOk);			
		} catch (ServiceException e) {
			Assert.assertEquals("Registro de id " + idTesteOk + " não encontrado.", e.getMessage());
			return;
		}
		Assert.fail("Deveria ter lançado exceção.");
	}
	
	@Test
	public void save_SalvandoPet_RetornoOk() {
		when(petRepository.exists(idTesteOk)).thenReturn(false);
		when(petRepository.save(any(Pet.class))).thenReturn(retornaPet(idTesteOk, nomeOk, clienteOk));
		Pet pet = null;
		try {
			pet = petService.save(retornaPet(null, nomeOk, clienteOk), false);			
		} catch (ServiceException e) {
			Assert.fail("Não poderia lançar uma exceção.");
		}
		Assert.assertEquals(idTesteOk, pet.getId());
	}
	
	
	@Test
	public void save_SalvandoComNomeNulo_LancaExcecao() {		
		try {
			petService.save(retornaPet(idTesteOk, null, clienteOk), false);			
		} catch (ServiceException e) {
			Assert.assertEquals("Os atributos cliente e nome são obrigatórios.", e.getMessage());
			return;
		}
		Assert.fail("Deveria ter lançado exceção.");
	}
	
	@Test
	public void save_SalvandoComClienteNulo_LancaExcecao() {		
		try {
			petService.save(retornaPet(idTesteOk, nomeOk, null), false);			
		} catch (ServiceException e) {
			Assert.assertEquals("Os atributos cliente e nome são obrigatórios.", e.getMessage());
			return;
		}
		Assert.fail("Deveria ter lançado exceção.");
	}
	
	@Test
	public void save_SalvandoComCpfClienteNulo_LancaExcecao() {	
		Cliente clienteSemCpf = new Cliente();
		try {
			petService.save(retornaPet(idTesteOk, nomeOk, clienteSemCpf), false);			
		} catch (ServiceException e) {
			Assert.assertEquals("Os atributos cliente e nome são obrigatórios.", e.getMessage());
			return;
		}
		Assert.fail("Deveria ter lançado exceção.");
	}
	
	@Test
	public void save_AtualizandoComIdNulo_LancaExcecao() {		
		try {
			petService.save(retornaPet(null, nomeOk, clienteOk), true);			
		} catch (ServiceException e) {
			Assert.assertEquals("Na atualização, é necessário informar o id de um registro existente.", e.getMessage());
			return;
		}
		Assert.fail("Deveria ter lançado exceção.");
	}
	
	@Test
	public void save_AtualizandoPetInexistente_LancaExcecao() {		
		when(petRepository.exists(idTesteOk)).thenReturn(false);
		try {
			petService.save(retornaPet(null, nomeOk, clienteOk), true);			
		} catch (ServiceException e) {
			Assert.assertEquals("Na atualização, é necessário informar o id de um registro existente.", e.getMessage());
			return;
		}
		Assert.fail("Deveria ter lançado exceção.");
	}
	
	
	@Test
	public void delete_EncontraRegistro_RetornoOk() {
		when(petRepository.exists(idTesteOk)).thenReturn(true);
		try {
			petService.delete(idTesteOk);			
		} catch (Exception e) {
			Assert.fail("Não poderia lançar uma exceção.");
		}
	}
	
	@Test
	public void delete_IdInformadoNulo_LancaExcecao() {		
		try {
			petService.delete(null);			
		} catch (ServiceException e) {
			Assert.assertEquals("Id do registro não informado.", e.getMessage());
			return;
		}
		Assert.fail("Deveria ter lançado exceção.");
	}
	
	@Test
	public void delete_PetNaoEncontrado_LancaExcecao() {	
		when(petRepository.exists(idTesteOk)).thenReturn(false);
		try {
			petService.delete(idTesteOk);			
		} catch (ServiceException e) {
			Assert.assertEquals("Registro informado para exclusão não existe.", e.getMessage());
			return;
		}
		Assert.fail("Deveria ter lançado exceção.");
	}
	
	
	
	
	private List<Pet> retornaLista(Long id, String nome, Cliente cliente) {
		return Arrays.asList(retornaPet(id, nome, cliente));
	}
	
	private Pet retornaPet(Long id, String nome, Cliente cliente) {
		Pet pet = new Pet();
		pet.setId(id);
		pet.setNome(nome);
		pet.setCliente(cliente);
		
		return pet;
	}
}
