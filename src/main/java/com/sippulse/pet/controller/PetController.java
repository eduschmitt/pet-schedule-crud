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

import com.sippulse.pet.entity.Pet;
import com.sippulse.pet.exception.NegocioException;
import com.sippulse.pet.exception.NegocioException.TipoExcecao;
import com.sippulse.pet.service.PetService;

@RestController
public class PetController {

	private PetService service;

	@Autowired
	public PetController(PetService service) {
		super();
		this.service = service;
	}

	@RequestMapping(value = "/pets", method = RequestMethod.GET)
	List<Pet> findAll() {
		return service.findAll();
	}

	@RequestMapping(value = "/pet/{id}", method = RequestMethod.GET)
	ResponseEntity<Pet> findById(@PathVariable Long id) {
		Pet pet = null;
		try {
			pet = service.findById(id);
		} catch (NegocioException e) {
			return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
		}
		return pet != null ? new ResponseEntity<Pet>(pet, HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@RequestMapping(value = "/pet", method = RequestMethod.POST)
	ResponseEntity<Pet> save(@Valid @RequestBody Pet pet) {
		Pet petSalvo = null;
		try {
			petSalvo = service.save(pet, false);		
		} catch (NegocioException e) {
			return retornarStatusCodeCorreto(e);
		}
		return new ResponseEntity<Pet>(petSalvo, HttpStatus.OK);
	}

	@RequestMapping(value = "/pet", method = RequestMethod.PUT)
	ResponseEntity<Pet> update(@Valid @RequestBody Pet pet) {
		Pet petAtualizado = null;
		try {
			petAtualizado = service.save(pet, true);
		} catch (NegocioException e) {
			return retornarStatusCodeCorreto(e);	
		}
		return new ResponseEntity<Pet>(petAtualizado, HttpStatus.OK);			
	}
	
    @RequestMapping(value = "/pet/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Pet> delete(@PathVariable(value = "id") Long id) {
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
    private ResponseEntity<Pet> retornarStatusCodeCorreto(NegocioException e) {
		if (e.getTipoExcecao().equals(TipoExcecao.REGISTRO_NAO_ENCONTRADO)) {
			return new ResponseEntity<Pet>(HttpStatus.NOT_FOUND);				
		} else if (e.getTipoExcecao().equals(TipoExcecao.VALIDACAO_CAMPOS)) {
			return new ResponseEntity<Pet>(HttpStatus.UNPROCESSABLE_ENTITY);
		} else {
			return new ResponseEntity<Pet>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}