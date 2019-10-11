package com.sippulse.pet.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sippulse.pet.entity.Funcionario;
import com.sippulse.pet.exception.NegocioException;
import com.sippulse.pet.exception.NegocioException.TipoExcecao;
import com.sippulse.pet.service.FuncionarioService;

@RestController
public class FuncionarioController {

	private FuncionarioService service;

	@Autowired
	public FuncionarioController(FuncionarioService service) {
		super();
		this.service = service;
	}

	@RequestMapping(value = "/funcionarios", method = RequestMethod.GET)
	List<Funcionario> findAll() {
		return service.findAll();
	}

	@RequestMapping(value = "/funcionario/{id}", method = RequestMethod.GET)
	ResponseEntity<Funcionario> findById(@PathVariable Long id) {
		Funcionario funcionario = null;
		try {
			funcionario = service.findById(id);
		} catch (NegocioException e) {
			return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
		}
		return funcionario != null ? new ResponseEntity<Funcionario>(funcionario, HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@RequestMapping(value = "/funcionario", method = RequestMethod.POST)
	ResponseEntity<Funcionario> save(@Valid @RequestBody Funcionario funcionario) {
		Funcionario funcionarioSalvo = null;
		try {
			funcionarioSalvo = service.save(funcionario, false);		
		} catch (NegocioException e) {
			return retornarStatusCodeCorreto(e);
		}
		return new ResponseEntity<Funcionario>(funcionarioSalvo, HttpStatus.OK);
	}

	@RequestMapping(value = "/funcionario", method = RequestMethod.PUT)
	ResponseEntity<Funcionario> update(@Valid @RequestBody Funcionario funcionario) {
		Funcionario funcionarioAtualizado = null;
		try {
			funcionarioAtualizado = service.save(funcionario, true);
		} catch (NegocioException e) {
			return retornarStatusCodeCorreto(e);	
		}
		return new ResponseEntity<Funcionario>(funcionarioAtualizado, HttpStatus.OK);			
	}
	
    @RequestMapping(value = "/funcionario/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Funcionario> delete(@PathVariable(value = "id") Long id) {
    	try {
			service.delete(id);
		} catch (NegocioException e) {
			return retornarStatusCodeCorreto(e);			
		}
    	
    	return new ResponseEntity<>(HttpStatus.OK);			
    }
    
    /**
     * Método que define o código HTTP de retorno dependendo do tipo de exceção ocorrida.
     * @param e Exceção de negócio ocorrida.
     * @return ResponseEntity com HTTP Status code mais adequado.
     */
    private ResponseEntity<Funcionario> retornarStatusCodeCorreto(NegocioException e) {
		if (e.getTipoExcecao().equals(TipoExcecao.REGISTRO_NAO_ENCONTRADO)) {
			return new ResponseEntity<Funcionario>(HttpStatus.NOT_FOUND);				
		} else if (e.getTipoExcecao().equals(TipoExcecao.VALIDACAO_CAMPOS)) {
			return new ResponseEntity<Funcionario>(HttpStatus.UNPROCESSABLE_ENTITY);
		} else {
			return new ResponseEntity<Funcionario>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}