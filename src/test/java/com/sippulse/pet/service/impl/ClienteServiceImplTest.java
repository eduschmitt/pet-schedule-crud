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
import com.sippulse.pet.entity.enums.Sexo;
import com.sippulse.pet.exception.ServiceException;
import com.sippulse.pet.repository.ClienteRepository;

@RunWith(MockitoJUnitRunner.class)
public class ClienteServiceImplTest {
	
	Long cpfTesteOk = 8421886657l; 
	String nomeOk = "Nome do cliente";
	String telefoneOk = "48999996666";

	@Mock
	private ClienteRepository clienteRepository;
	
	@InjectMocks
	private ClienteServiceImpl clienteService;
	
	@Before
	public void setUp() {		
		clienteService = new ClienteServiceImpl(clienteRepository);
		MockitoAnnotations.initMocks(clienteService);
	}
	
	
	@Test
	public void findAll_EncontraUmRegistro_RetornoOk() {
		when(clienteRepository.findAll()).thenReturn(retornaLista(cpfTesteOk, nomeOk, telefoneOk));		
		List<Cliente> clientesRetornados = clienteService.findAll();		
		Assert.assertEquals(1, clientesRetornados.size());
	}
	
	@Test
	public void findAll_NaoEncontraRegistros_LancaExcecao() {
		when(clienteRepository.findAll()).thenReturn(null);
		try {
			clienteService.findAll();			
		} catch (ServiceException e) {
			Assert.assertEquals("Nenhum registro encontrado.", e.getMessage());
			return;
		}
		Assert.fail("Deveria ter lançado exceção.");
	}
	
	@Test
	public void findById_EncontraRegistro_RetornoOk() {
		when(clienteRepository.findOne(cpfTesteOk)).thenReturn(retornaCliente(cpfTesteOk, nomeOk, telefoneOk));
		Cliente cliente = clienteService.findById(cpfTesteOk);
		Assert.assertEquals(cpfTesteOk, cliente.getCpf());
	}
	
	@Test
	public void findById_IdInformadoNulo_LancaExcecao() {		
		try {
			clienteService.findById(null);			
		} catch (ServiceException e) {
			Assert.assertEquals("Id do registro não informado.", e.getMessage());
			return;
		}
		Assert.fail("Deveria ter lançado exceção.");
	}
	
	@Test
	public void findById_ClienteNaoEncontrado_LancaExcecao() {	
		when(clienteRepository.findOne(cpfTesteOk)).thenReturn(null);
		try {
			clienteService.findById(cpfTesteOk);			
		} catch (ServiceException e) {
			Assert.assertEquals("Registro de id " + cpfTesteOk + " não encontrado.", e.getMessage());
			return;
		}
		Assert.fail("Deveria ter lançado exceção.");
	}
	
	
	@Test
	public void save_SalvandoCliente_RetornoOk() {
		when(clienteRepository.exists(cpfTesteOk)).thenReturn(false);
		when(clienteRepository.save(any(Cliente.class))).thenReturn(retornaCliente(cpfTesteOk, nomeOk, telefoneOk));
		Cliente cliente = null;
		try {
			cliente = clienteService.save(retornaCliente(cpfTesteOk, nomeOk, telefoneOk), false);			
		} catch (ServiceException e) {
			Assert.fail("Não poderia lançar uma exceção.");
		}
		Assert.assertEquals(cpfTesteOk, cliente.getCpf());
	}
	
	@Test
	public void save_SalvandoComCpfNulo_LancaExcecao() {		
		try {
			clienteService.save(retornaCliente(null, nomeOk, telefoneOk), false);			
		} catch (ServiceException e) {
			Assert.assertEquals("Os atributos CPF, nome e telefone são obrigatórios.", e.getMessage());
			return;
		}
		Assert.fail("Deveria ter lançado exceção.");
	}
	
	@Test
	public void save_SalvandoComNomeNulo_LancaExcecao() {		
		try {
			clienteService.save(retornaCliente(cpfTesteOk, null, telefoneOk), false);			
		} catch (ServiceException e) {
			Assert.assertEquals("Os atributos CPF, nome e telefone são obrigatórios.", e.getMessage());
			return;
		}
		Assert.fail("Deveria ter lançado exceção.");
	}
	
	@Test
	public void save_SalvandoComTelefoneNulo_LancaExcecao() {		
		try {
			clienteService.save(retornaCliente(cpfTesteOk, nomeOk, null), false);			
		} catch (ServiceException e) {
			Assert.assertEquals("Os atributos CPF, nome e telefone são obrigatórios.", e.getMessage());
			return;
		}
		Assert.fail("Deveria ter lançado exceção.");
	}
	
	@Test
	public void save_SalvandoComCpfInvalido_LancaExcecao() {		
		try {
			clienteService.save(retornaCliente(12456658l, nomeOk, telefoneOk), false);			
		} catch (ServiceException e) {
			Assert.assertTrue(e.getMessage().contains("Erros na validação do CPF informado."));
			return;
		}
		Assert.fail("Deveria ter lançado exceção.");
	}
	
	@Test
	public void save_TentandoSalvarClienteExistente_LancaExcecao() {
		when(clienteRepository.exists(cpfTesteOk)).thenReturn(true);
		try {
			clienteService.save(retornaCliente(cpfTesteOk, nomeOk, telefoneOk), false);			
		} catch (ServiceException e) {
			Assert.assertTrue(e.getMessage().contains("Registro informado para inclusão já existe."));
			return;
		}
		Assert.fail("Deveria ter lançado exceção.");
	}
	
	
	
	
	@Test
	public void save_AtualizandoCliente_RetornoOk() {
		when(clienteRepository.exists(cpfTesteOk)).thenReturn(true);
		when(clienteRepository.save(any(Cliente.class))).thenReturn(retornaCliente(cpfTesteOk, nomeOk, telefoneOk));
		Cliente cliente = null;
		try {
			cliente = clienteService.save(retornaCliente(cpfTesteOk, nomeOk, telefoneOk), true);			
		} catch (ServiceException e) {
			Assert.fail("Não poderia lançar uma exceção.");
		}
		Assert.assertEquals(cpfTesteOk, cliente.getCpf());
	}
	
	
	@Test
	public void save_TentandoAtualizarClienteQueNaoExiste_LancaExcecao() {
		when(clienteRepository.exists(cpfTesteOk)).thenReturn(false);
		try {
			clienteService.save(retornaCliente(cpfTesteOk, nomeOk, telefoneOk), true);			
		} catch (ServiceException e) {
			Assert.assertTrue(e.getMessage().contains("Registro informado para atualização não existe."));
			return;
		}
		Assert.fail("Deveria ter lançado exceção.");
	}
	
	
	@Test
	public void delete_EncontraRegistro_RetornoOk() {
		when(clienteRepository.exists(cpfTesteOk)).thenReturn(true);
		try {
			clienteService.delete(cpfTesteOk);			
		} catch (Exception e) {
			Assert.fail("Não poderia lançar uma exceção.");
		}
	}
	
	@Test
	public void delete_IdInformadoNulo_LancaExcecao() {		
		try {
			clienteService.delete(null);			
		} catch (ServiceException e) {
			Assert.assertEquals("Id do registro não informado.", e.getMessage());
			return;
		}
		Assert.fail("Deveria ter lançado exceção.");
	}
	
	@Test
	public void delete_ClienteNaoEncontrado_LancaExcecao() {	
		when(clienteRepository.exists(cpfTesteOk)).thenReturn(false);
		try {
			clienteService.delete(cpfTesteOk);			
		} catch (ServiceException e) {
			Assert.assertEquals("Registro informado para exclusão não existe.", e.getMessage());
			return;
		}
		Assert.fail("Deveria ter lançado exceção.");
	}
	
	private List<Cliente> retornaLista(Long cpf, String nome, String telefone) {
		return Arrays.asList(retornaCliente(cpf, nome, telefone));
	}
	
	private Cliente retornaCliente(Long cpf, String nome, String telefone) {
		Cliente cliente = new Cliente();
		cliente.setCpf(cpf);
		cliente.setNome(nome);
		cliente.setPets(null);
		cliente.setSexo(Sexo.M);
		cliente.setTelefone(telefone);
		
		return cliente;
	}
}
