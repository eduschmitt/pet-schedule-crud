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
import com.sippulse.pet.service.ConsultaService;

/**
 * Classe controller para as requisições relacionadas a Consulta
 * 
 * @author eduardo
 *
 */
@RestController
@RequestMapping("consultas")
public class ConsultaController {

	private ConsultaService service;

	@Autowired
	public ConsultaController(ConsultaService service) {
		super();
		this.service = service;
	}

	@RequestMapping(method = RequestMethod.GET)
	List<Consulta> findAll() {
		return service.findAll();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	ResponseEntity<Consulta> findById(@PathVariable Long id) {
		return new ResponseEntity<Consulta>(service.findById(id), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST)
	ResponseEntity<Consulta> save(@Valid @RequestBody Consulta consulta) {
		return new ResponseEntity<Consulta>(service.save(consulta, false), HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.PUT)
	ResponseEntity<Consulta> update(@Valid @RequestBody Consulta consulta) {
		return new ResponseEntity<Consulta>(service.save(consulta, true), HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Consulta> delete(@PathVariable(value = "id") Long id) {
		service.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}