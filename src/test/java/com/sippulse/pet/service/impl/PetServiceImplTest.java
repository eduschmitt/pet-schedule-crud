package com.sippulse.pet.service.impl;

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

import com.sippulse.pet.entity.Pet;
import com.sippulse.pet.exception.ServiceException;
import com.sippulse.pet.repository.PetRepository;

@RunWith(MockitoJUnitRunner.class)
public class PetServiceImplTest {
	
	Long idTesteOk = 1l; 

	@Mock
	private PetRepository petRepository;
	
	@InjectMocks
	private PetServiceImpl petService;
	
	@Before
	public void setUp() {		
		petService = new PetServiceImpl(petRepository);
		MockitoAnnotations.initMocks(petService);
	}
	
	
	@Test
	public void findAll_EncontraUmRegistro_RetornoOk() {
		when(petRepository.findAll()).thenReturn(retornaLista(idTesteOk));		
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
		when(petRepository.findOne(idTesteOk)).thenReturn(retornaPet(idTesteOk));
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
	
	
	
	
	private List<Pet> retornaLista(Long id) {
		return Arrays.asList(retornaPet(id));
	}
	
	private Pet retornaPet(Long id) {
		Pet pet = new Pet();
		pet.setId(id);
		pet.setNome("Nome do pet");
		
		return pet;
	}
}
