package com.sippulse.pet.entity;

import java.io.Serializable;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.fasterxml.jackson.annotation.JsonView;
import com.sippulse.pet.entity.enums.Sexo;
import com.sippulse.pet.utils.View;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Classe abstrata de pessoa, que tem os atributos comuns de Cliente e Funcionario.
 * 
 * @author schmitt
 *
 */

// anotações do lombok para geração automática de código boilerplate
@Data
@EqualsAndHashCode(of = "cpf")
@MappedSuperclass
public abstract class Pessoa implements Serializable {
	
	private static final long serialVersionUID = -6058964677021771555L;
	
	@Id
	@JsonView(View.Basic.class)
	@ApiModelProperty(notes = "CPF", required = true)
	private Long cpf;
	
	@JsonView(View.Basic.class)
	@ApiModelProperty(notes = "Nome")
	private String nome;
	
	@Enumerated(EnumType.STRING)
	@JsonView(View.Basic.class)
	@ApiModelProperty(notes = "Sexo")
	private Sexo sexo;
}
