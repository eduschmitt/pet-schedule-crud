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

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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

	@ApiOperation(value = "findAll")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure") })
	@RequestMapping(method = RequestMethod.GET)
	@JsonView(View.ConsultaComPetVeterinario.class)
	List<Consulta> findAll() {
		return service.findAll();
	}

	@ApiOperation(value = "findById")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 500, message = "Failure") })
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@JsonView(View.ConsultaComPetVeterinario.class)
	ResponseEntity<Consulta> findById(@PathVariable Long id) {
		return new ResponseEntity<Consulta>(service.findById(id), HttpStatus.OK);
	}

	@ApiOperation(value = "Busca consultas pelo veterinário e/ou data informados.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 500, message = "Failure") })
	@RequestMapping(value = "/findByVeterinarioData", method = RequestMethod.GET)
	@JsonView(View.ConsultaComPetVeterinario.class)
	List<Consulta> findByVeterinarioData(@RequestParam(required = false) Long cpfVeterinario,
			@RequestParam(required = false) @ApiParam(value = "Data no formato DD/MM/YYYY") @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate dataConsulta) {
		log.info("Buscando consultas por veterinário e/ou data. cpfVeterinario: {}, dataConsulta: {}", cpfVeterinario,
				dataConsulta);
		return service.findByVeterinarioData(cpfVeterinario, dataConsulta);
	}

	@ApiOperation(value = "Busca consultas pelo cpf do Cliente informado.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 500, message = "Failure") })
	@RequestMapping(value = "/findByCliente", method = RequestMethod.GET)
	@JsonView(View.ConsultaComPetVeterinario.class)
	List<Consulta> findByCliente(@RequestParam(required = true) Long cpfCliente) {
		log.info("Buscando consultas por cpf do cliente. Cpf informado: {}", cpfCliente);
		return service.findByCliente(cpfCliente);
	}

	@ApiOperation(value = "save")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Created"),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 500, message = "Failure") })
	@RequestMapping(method = RequestMethod.POST)
	ResponseEntity<Consulta> save(@Valid @RequestBody Consulta consulta) {
		service.save(consulta, false);
		return new ResponseEntity<Consulta>(HttpStatus.CREATED);
	}

	@ApiOperation(value = "update")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = List.class),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 500, message = "Failure") })
	@RequestMapping(method = RequestMethod.PUT)
	ResponseEntity<Consulta> update(@Valid @RequestBody Consulta consulta) {
		service.save(consulta, true);
		return new ResponseEntity<Consulta>(HttpStatus.OK);
	}

	@ApiOperation(value = "delete")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = List.class),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 500, message = "Failure") })
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Consulta> delete(@PathVariable(value = "id") Long id) {
		service.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}