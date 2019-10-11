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

/**
 * Classe controller para as requisições relacionadas a Funcionario
 * 
 * @author eduardo
 *
 */
@RestController
@RequestMapping("funcionarios")
public class FuncionarioController {

	private FuncionarioService service;

	@Autowired
	public FuncionarioController(FuncionarioService service) {
		super();
		this.service = service;
	}

	@RequestMapping(method = RequestMethod.GET)
	List<Funcionario> findAll() {
		return service.findAll();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	ResponseEntity<Funcionario> findById(@PathVariable Long id) {
		return new ResponseEntity<Funcionario>(service.findById(id), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST)
	ResponseEntity<Funcionario> save(@Valid @RequestBody Funcionario funcionario) {
		return new ResponseEntity<Funcionario>(service.save(funcionario, false), HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.PUT)
	ResponseEntity<Funcionario> update(@Valid @RequestBody Funcionario funcionario) {
		return new ResponseEntity<Funcionario>(service.save(funcionario, true), HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Funcionario> delete(@PathVariable(value = "id") Long id) {
		service.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}