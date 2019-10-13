package com.sippulse.pet.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sippulse.pet.entity.Usuario;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * Classe criada apenas para forçar o swagger a exibir um método de login.
 * @author schmitt
 *
 */
@RestController
public class LoginController {
	
	/**
	 * Método criado apenas para forçar o swagger a mostrar a opção de login para o usuário.
	 * @param usuario
	 */
	@ApiOperation("Token JWT é retornado no Header do response. Não é necessário informar Header no Request.")
	@RequestMapping(value="/login", method = RequestMethod.POST)
	public void fakeLogin(@ApiParam("username") @RequestBody Usuario usuario) {
	    throw new IllegalStateException("Esse método nunca será chamado, pois é implementado pelos filtros do Spring Security.");
	}

}
