package com.sippulse.pet.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

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
@Entity
@Table(name="funcionario")
public class Funcionario extends Pessoa {

	private static final long serialVersionUID = 4457033580775848923L;
	
	private String senha;
	
	@Enumerated(EnumType.STRING)
	private TipoFuncionario tipoFuncionario;

}
