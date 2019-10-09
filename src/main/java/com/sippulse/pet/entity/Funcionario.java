package com.sippulse.pet.entity;

import com.sippulse.pet.entity.enums.TipoFuncionario;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Especialização da classe pessoa para representar o Funcionário, 
 * seja ele Veterinário ou Atendente.
 * @author schmitt
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Funcionario extends Pessoa {

	private static final long serialVersionUID = 4457033580775848923L;
	
	private String senha;
	private TipoFuncionario tipoFuncionario;

}
