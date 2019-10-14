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

import com.sippulse.pet.entity.Funcionario;
import com.sippulse.pet.entity.enums.Sexo;
import com.sippulse.pet.entity.enums.TipoFuncionario;
import com.sippulse.pet.exception.ServiceException;
import com.sippulse.pet.repository.FuncionarioRepository;

@RunWith(MockitoJUnitRunner.class)
public class FuncionarioServiceImplTest {

	Long cpfTesteOk = 8421886657l;
	String nomeFuncionarioOk = "Nome Funcionario";
	String senhaOk = "SENHA";

	@Mock
	private FuncionarioRepository funcionarioRepository;

	@InjectMocks
	private FuncionarioServiceImpl funcionarioService;

	@Before
	public void setUp() {
		funcionarioService = new FuncionarioServiceImpl(funcionarioRepository);
		MockitoAnnotations.initMocks(funcionarioService);
	}

	@Test
	public void findAll_EncontraUmRegistro_RetornoOk() {
		when(funcionarioRepository.findAll())
				.thenReturn(retornaLista(cpfTesteOk, nomeFuncionarioOk, TipoFuncionario.ATENDIMENTO, senhaOk));
		List<Funcionario> funcionariosRetornados = funcionarioService.findAll();
		Assert.assertEquals(1, funcionariosRetornados.size());
	}

	@Test
	public void findAll_NaoEncontraRegistros_LancaExcecao() {
		when(funcionarioRepository.findAll()).thenReturn(null);
		try {
			funcionarioService.findAll();
		} catch (ServiceException e) {
			Assert.assertEquals("Nenhum registro encontrado.", e.getMessage());
			return;
		}
		Assert.fail("Deveria ter lançado exceção.");
	}

	@Test
	public void findById_EncontraRegistro_RetornoOk() {
		when(funcionarioRepository.findOne(cpfTesteOk))
				.thenReturn(retornaFuncionario(cpfTesteOk, nomeFuncionarioOk, TipoFuncionario.ATENDIMENTO, senhaOk));
		Funcionario funcionario = funcionarioService.findById(cpfTesteOk);
		Assert.assertEquals(cpfTesteOk, funcionario.getCpf());
	}

	@Test
	public void findById_IdInformadoNulo_LancaExcecao() {
		try {
			funcionarioService.findById(null);
		} catch (ServiceException e) {
			Assert.assertEquals("Id do registro não informado.", e.getMessage());
			return;
		}
		Assert.fail("Deveria ter lançado exceção.");
	}

	@Test
	public void findById_FuncionarioNaoEncontrado_LancaExcecao() {
		when(funcionarioRepository.findOne(cpfTesteOk)).thenReturn(null);
		try {
			funcionarioService.findById(cpfTesteOk);
		} catch (ServiceException e) {
			Assert.assertEquals("Registro de id " + cpfTesteOk + " não encontrado.", e.getMessage());
			return;
		}
		Assert.fail("Deveria ter lançado exceção.");
	}

	@Test
	public void save_SalvandoFuncionario_RetornoOk() {
		when(funcionarioRepository.exists(cpfTesteOk)).thenReturn(false);
		when(funcionarioRepository.save(any(Funcionario.class)))
				.thenReturn(retornaFuncionario(cpfTesteOk, nomeFuncionarioOk, TipoFuncionario.ATENDIMENTO, senhaOk));
		Funcionario funcionario = null;
		try {
			funcionario = funcionarioService.save(
					retornaFuncionario(cpfTesteOk, nomeFuncionarioOk, TipoFuncionario.ATENDIMENTO, senhaOk), false);
		} catch (ServiceException e) {
			Assert.fail("Não poderia lançar uma exceção.");
		}
		Assert.assertEquals(cpfTesteOk, funcionario.getCpf());
	}

	@Test
	public void save_SalvandoComCpfNulo_LancaExcecao() {
		try {
			funcionarioService.save(retornaFuncionario(null, nomeFuncionarioOk, TipoFuncionario.ATENDIMENTO, senhaOk),
					false);
		} catch (ServiceException e) {
			Assert.assertEquals("Os atributos CPF, nome, senha e tipo são obrigatórios.", e.getMessage());
			return;
		}
		Assert.fail("Deveria ter lançado exceção.");
	}

	@Test
	public void save_SalvandoComNomeNulo_LancaExcecao() {
		try {
			funcionarioService.save(retornaFuncionario(cpfTesteOk, null, TipoFuncionario.ATENDIMENTO, senhaOk), false);
		} catch (ServiceException e) {
			Assert.assertEquals("Os atributos CPF, nome, senha e tipo são obrigatórios.", e.getMessage());
			return;
		}
		Assert.fail("Deveria ter lançado exceção.");
	}

	@Test
	public void save_SalvandoComTipoFuncionarioNulo_LancaExcecao() {
		try {
			funcionarioService.save(retornaFuncionario(cpfTesteOk, nomeFuncionarioOk, null, senhaOk), false);
		} catch (ServiceException e) {
			Assert.assertEquals("Os atributos CPF, nome, senha e tipo são obrigatórios.", e.getMessage());
			return;
		}
		Assert.fail("Deveria ter lançado exceção.");
	}

	@Test
	public void save_SalvandoComSenhaNula_LancaExcecao() {
		try {
			funcionarioService
					.save(retornaFuncionario(cpfTesteOk, nomeFuncionarioOk, TipoFuncionario.ATENDIMENTO, null), false);
		} catch (ServiceException e) {
			Assert.assertEquals("Os atributos CPF, nome, senha e tipo são obrigatórios.", e.getMessage());
			return;
		}
		Assert.fail("Deveria ter lançado exceção.");
	}

	@Test
	public void save_SalvandoComCpfInvalido_LancaExcecao() {
		try {
			funcionarioService.save(
					retornaFuncionario(12456658l, nomeFuncionarioOk, TipoFuncionario.ATENDIMENTO, senhaOk), false);
		} catch (ServiceException e) {
			Assert.assertTrue(e.getMessage().contains("Erros na validação do CPF informado."));
			return;
		}
		Assert.fail("Deveria ter lançado exceção.");
	}

	@Test
	public void save_TentandoSalvarFuncionarioExistente_LancaExcecao() {
		when(funcionarioRepository.exists(cpfTesteOk)).thenReturn(true);
		try {
			funcionarioService.save(
					retornaFuncionario(cpfTesteOk, nomeFuncionarioOk, TipoFuncionario.ATENDIMENTO, senhaOk), false);
		} catch (ServiceException e) {
			Assert.assertTrue(e.getMessage().contains("Registro informado para inclusão já existe."));
			return;
		}
		Assert.fail("Deveria ter lançado exceção.");
	}

	@Test
	public void save_AtualizandoFuncionario_RetornoOk() {
		when(funcionarioRepository.exists(cpfTesteOk)).thenReturn(true);
		when(funcionarioRepository.save(any(Funcionario.class)))
				.thenReturn(retornaFuncionario(cpfTesteOk, nomeFuncionarioOk, TipoFuncionario.ATENDIMENTO, senhaOk));
		Funcionario funcionario = null;
		try {
			funcionario = funcionarioService.save(
					retornaFuncionario(cpfTesteOk, nomeFuncionarioOk, TipoFuncionario.ATENDIMENTO, senhaOk), true);
		} catch (ServiceException e) {
			Assert.fail("Não poderia lançar uma exceção.");
		}
		Assert.assertEquals(cpfTesteOk, funcionario.getCpf());
	}

	@Test
	public void save_TentandoAtualizarFuncionarioQueNaoExiste_LancaExcecao() {
		when(funcionarioRepository.exists(cpfTesteOk)).thenReturn(false);
		try {
			funcionarioService.save(
					retornaFuncionario(cpfTesteOk, nomeFuncionarioOk, TipoFuncionario.ATENDIMENTO, senhaOk), true);
		} catch (ServiceException e) {
			Assert.assertTrue(e.getMessage().contains("Registro informado para atualização não existe."));
			return;
		}
		Assert.fail("Deveria ter lançado exceção.");
	}

	@Test
	public void delete_EncontraRegistro_RetornoOk() {
		when(funcionarioRepository.exists(cpfTesteOk)).thenReturn(true);
		try {
			funcionarioService.delete(cpfTesteOk);
		} catch (Exception e) {
			Assert.fail("Não poderia lançar uma exceção.");
		}
	}

	@Test
	public void delete_IdInformadoNulo_LancaExcecao() {
		try {
			funcionarioService.delete(null);
		} catch (ServiceException e) {
			Assert.assertEquals("Id do registro não informado.", e.getMessage());
			return;
		}
		Assert.fail("Deveria ter lançado exceção.");
	}

	@Test
	public void delete_FuncionarioNaoEncontrado_LancaExcecao() {
		when(funcionarioRepository.exists(cpfTesteOk)).thenReturn(false);
		try {
			funcionarioService.delete(cpfTesteOk);
		} catch (ServiceException e) {
			Assert.assertEquals("Registro informado para exclusão não existe.", e.getMessage());
			return;
		}
		Assert.fail("Deveria ter lançado exceção.");
	}

	private List<Funcionario> retornaLista(Long cpf, String nome, TipoFuncionario tipoFuncionario, String senha) {
		return Arrays.asList(retornaFuncionario(cpf, nome, tipoFuncionario, senha));
	}

	private Funcionario retornaFuncionario(Long cpf, String nome, TipoFuncionario tipoFuncionario, String senha) {
		Funcionario funcionario = new Funcionario();
		funcionario.setCpf(cpf);
		funcionario.setNome(nome);
		funcionario.setSexo(Sexo.M);
		funcionario.setSenha(senha);
		funcionario.setTipoFuncionario(tipoFuncionario);

		return funcionario;
	}
}
