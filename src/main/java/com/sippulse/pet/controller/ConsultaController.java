package com.sippulse.pet.controller;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.sippulse.pet.entity.Consulta;
import com.sippulse.pet.service.ConsultaService;
import com.sippulse.pet.utils.View;

import lombok.extern.slf4j.Slf4j;

/**
 * Classe controller para as requisições relacionadas a Consulta
 * 
 * @author eduardo
 *
 */
@RestController
@RequestMapping("consultas")
@Slf4j
public class ConsultaController {

	private ConsultaService service;

	@Autowired
	public ConsultaController(ConsultaService service) {
		super();
		this.service = service;
	}

	@RequestMapping(method = RequestMethod.GET)
	@JsonView(View.ConsultaComPetVeterinario.class)
	List<Consulta> findAll() {
		return service.findAll();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@JsonView(View.ConsultaComPetVeterinario.class)
	ResponseEntity<Consulta> findById(@PathVariable Long id) {
		return new ResponseEntity<Consulta>(service.findById(id), HttpStatus.OK);
	}

	@RequestMapping(value = "/findByVeterinarioData", method = RequestMethod.GET)
	@JsonView(View.ConsultaComPetVeterinario.class)
	List<Consulta> findByVeterinarioData(@RequestParam(required = false) Long cpfVeterinario,
			@RequestParam(required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate dataConsulta) {
		log.info("Buscando consultas por veterinário e/ou data. cpfVeterinario: {}, dataConsulta: {}", cpfVeterinario, dataConsulta);
		return service.findByVeterinarioData(cpfVeterinario, dataConsulta);
	}
	
	@RequestMapping(value = "/findByCliente", method = RequestMethod.GET)
	@JsonView(View.ConsultaComPetVeterinario.class)
	List<Consulta> findByCliente(@RequestParam(required = true) Long cpfCliente,
			@RequestParam(required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate dataConsulta) {
		log.info("Buscando consultas por cpf do cliente. Cpf informado: {}", cpfCliente);
		return service.findByCliente(cpfCliente);
	}

	@RequestMapping(method = RequestMethod.POST)
	ResponseEntity<Consulta> save(@Valid @RequestBody Consulta consulta) {
		service.save(consulta, false);
		return new ResponseEntity<Consulta>(HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.PUT)
	ResponseEntity<Consulta> update(@Valid @RequestBody Consulta consulta) {
		service.save(consulta, true);
		return new ResponseEntity<Consulta>(HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Consulta> delete(@PathVariable(value = "id") Long id) {
		service.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}