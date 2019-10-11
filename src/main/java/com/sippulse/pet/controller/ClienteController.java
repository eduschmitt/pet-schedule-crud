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

import com.sippulse.pet.entity.Cliente;
import com.sippulse.pet.exception.NegocioException;
import com.sippulse.pet.exception.NegocioException.TipoExcecao;
import com.sippulse.pet.service.ClienteService;

/**
 * Classe controller para as requisições relacionadas a Cliente
 * @author eduardo
 *
 */
@RestController
public class ClienteController {

	private ClienteService service;

	@Autowired
	public ClienteController(ClienteService service) {
		super();
		this.service = service;
	}

	@RequestMapping(value = "/clientes", method = RequestMethod.GET)
	List<Cliente> findAll() {
		return service.findAll();
	}

	@RequestMapping(value = "/cliente/{id}", method = RequestMethod.GET)
	ResponseEntity findById(@PathVariable Long id) {
		Cliente cliente = null;
		try {
			cliente = service.findById(id);
		} catch (NegocioException e) {
			return retornarStatusCodeCorreto(e);
		}
		return cliente != null ? new ResponseEntity<Cliente>(cliente, HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@RequestMapping(value = "/cliente", method = RequestMethod.POST)
	ResponseEntity save(@Valid @RequestBody Cliente cliente) {
		Cliente clienteSalvo = null;
		try {
			clienteSalvo = service.save(cliente, false);		
		} catch (NegocioException e) {
			return retornarStatusCodeCorreto(e);
		}
		return new ResponseEntity<Cliente>(clienteSalvo, HttpStatus.OK);
	}

	@RequestMapping(value = "/cliente", method = RequestMethod.PUT)
	ResponseEntity<Cliente> update(@Valid @RequestBody Cliente cliente) {
		Cliente clienteAtualizado = null;
		try {
			clienteAtualizado = service.save(cliente, true);
		} catch (NegocioException e) {
			return retornarStatusCodeCorreto(e);	
		}
		return new ResponseEntity<Cliente>(clienteAtualizado, HttpStatus.OK);			
	}
	
    @RequestMapping(value = "/cliente/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Cliente> delete(@PathVariable(value = "id") Long id) {
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
    private ResponseEntity retornarStatusCodeCorreto(NegocioException e) {
		if (e.getTipoExcecao().equals(TipoExcecao.REGISTRO_NAO_ENCONTRADO)) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());				
		} else if (e.getTipoExcecao().equals(TipoExcecao.VALIDACAO_CAMPOS)) {
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(e.getMessage());
		} else {
			return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}