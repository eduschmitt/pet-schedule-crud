package com.sippulse.pet.entity;

import java.io.Serializable;

import com.sippulse.pet.entity.enums.TipoPet;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = {"cpfCliente", "nome"})
public class Pet implements Serializable {

	private static final long serialVersionUID = -784982880627220710L;

	private Long id;
	private Long cpfCliente;
	private String nome;
	private TipoPet tipo;
	private String raça;
	private Integer idade;
	private String observacoes;

}
