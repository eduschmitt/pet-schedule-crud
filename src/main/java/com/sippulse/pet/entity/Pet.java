package com.sippulse.pet.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.sippulse.pet.entity.enums.TipoPet;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = {"cliente", "nome"})
@Entity
@Table(name="pet")
public class Pet implements Serializable {

	private static final long serialVersionUID = -784982880627220710L;

	@Id
	private Long id;
	private Cliente cliente;
	private String nome;
	private TipoPet tipo;
	private String raça;
	private Integer idade;
	private String observacoes;

}
