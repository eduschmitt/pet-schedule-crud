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

import com.sippulse.pet.entity.Consulta;
import com.sippulse.pet.exception.NegocioException;
import com.sippulse.pet.exception.NegocioException.TipoExcecao;
import com.sippulse.pet.service.ConsultaService;

@RestController
public class ConsultaController {

	private ConsultaService service;

	@Autowired
	public ConsultaController(ConsultaService service) {
		super();
		this.service = service;
	}

	@RequestMapping(value = "/consultas", method = RequestMethod.GET)
	List<Consulta> findAll() {
		return service.findAll();
	}

	@RequestMapping(value = "/consulta/{id}", method = RequestMethod.GET)
	ResponseEntity<Consulta> findById(@PathVariable Long id) {
		Consulta consulta = null;
		try {
			service.findById(id);
		} catch (NegocioException e) {
			return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
		}
		return consulta != null ? new ResponseEntity<Consulta>(consulta, HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@RequestMapping(value = "/consulta", method = RequestMethod.POST)
	ResponseEntity<Consulta> save(@Valid @RequestBody Consulta consulta) {
		Consulta consultaSalvo = null;
		try {
			consultaSalvo = service.save(consulta, false);		
		} catch (NegocioException e) {
			return retornarStatusCodeCorreto(e);
		}
		return new ResponseEntity<Consulta>(consultaSalvo, HttpStatus.OK);
	}

	@RequestMapping(value = "/consulta", method = RequestMethod.PUT)
	ResponseEntity<Consulta> update(@Valid @RequestBody Consulta consulta) {
		Consulta consultaAtualizado = null;
		try {
			consultaAtualizado = service.save(consulta, true);
		} catch (NegocioException e) {
			return retornarStatusCodeCorreto(e);	
		}
		return new ResponseEntity<Consulta>(consultaAtualizado, HttpStatus.OK);			
	}
	
    @RequestMapping(value = "/consulta/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Consulta> delete(@PathVariable(value = "id") Long id) {
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
    private ResponseEntity<Consulta> retornarStatusCodeCorreto(NegocioException e) {
		if (e.getTipoExcecao().equals(TipoExcecao.REGISTRO_NAO_ENCONTRADO)) {
			return new ResponseEntity<Consulta>(HttpStatus.NOT_FOUND);				
		} else if (e.getTipoExcecao().equals(TipoExcecao.VALIDACAO_CAMPOS)) {
			return new ResponseEntity<Consulta>(HttpStatus.UNPROCESSABLE_ENTITY);
		} else {
			return new ResponseEntity<Consulta>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}