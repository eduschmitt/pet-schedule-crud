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
		Funcionario funcionario = service.findById(id);
		return funcionario != null ? new ResponseEntity<Funcionario>(funcionario, HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@RequestMapping(value = "/funcionario", method = RequestMethod.POST)
	void save(@Valid @RequestBody Funcionario funcionario) {
		service.save(funcionario);
	}
	
    @RequestMapping(value = "/funcionario/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> delete(@PathVariable(value = "id") Long id) {
    	Funcionario funcionario = service.findById(id);
    	if (funcionario != null) {
    		service.delete(funcionario);
    		return new ResponseEntity<>(HttpStatus.OK);
    	}
    	return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}