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

import com.fasterxml.jackson.annotation.JsonView;
import com.sippulse.pet.entity.Funcionario;
import com.sippulse.pet.service.FuncionarioService;
import com.sippulse.pet.utils.View;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

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

	@ApiOperation(value = "findAll")
    @ApiResponses(value = { 
            @ApiResponse(code = 200, message = "Success", response = List.class),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure")}) 
	@RequestMapping(method = RequestMethod.GET)
	@JsonView(View.Basic.class)
	List<Funcionario> findAll() {
		return service.findAll();
	}

	@ApiOperation(value = "findById")
    @ApiResponses(value = { 
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure")})
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@JsonView(View.Basic.class)
	ResponseEntity<Funcionario> findById(@PathVariable Long id) {
		return new ResponseEntity<Funcionario>(service.findById(id), HttpStatus.OK);
	}

	@ApiOperation(value = "save")
    @ApiResponses(value = { 
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Failure")}) 
	@RequestMapping(method = RequestMethod.POST)
	@JsonView(View.Basic.class)
	ResponseEntity<Funcionario> save(@Valid @RequestBody Funcionario funcionario) {
		return new ResponseEntity<Funcionario>(service.save(funcionario, false), HttpStatus.CREATED);
	}

	@ApiOperation(value = "update")
    @ApiResponses(value = { 
            @ApiResponse(code = 200, message = "Success", response = List.class),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure")}) 
	@RequestMapping(method = RequestMethod.PUT)
	@JsonView(View.Basic.class)
	ResponseEntity<Funcionario> update(@Valid @RequestBody Funcionario funcionario) {
		return new ResponseEntity<Funcionario>(service.save(funcionario, true), HttpStatus.OK);
	}

	@ApiOperation(value = "delete")
    @ApiResponses(value = { 
            @ApiResponse(code = 200, message = "Success", response = List.class),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure")}) 
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Funcionario> delete(@PathVariable(value = "id") Long id) {
		service.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}