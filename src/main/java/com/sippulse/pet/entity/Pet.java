package com.sippulse.pet.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonView;
import com.sippulse.pet.entity.enums.TipoPet;
import com.sippulse.pet.utils.View;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = {"cliente", "nome"})
@Entity
@Table(name="pet")
public class Pet implements Serializable {

	private static final long serialVersionUID = -784982880627220710L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@JsonView(View.Basic.class)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="CPF_CLIENTE")
	@JsonView(View.ClienteDoPet.class)
	private Cliente cliente;
	
	@NotNull
	@JsonView(View.Basic.class)
	private String nome;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	@JsonView(View.Basic.class)
	private TipoPet tipo;
	@JsonView(View.Basic.class)
	private String raca;
	@JsonView(View.Basic.class)
	private Integer idade;
	@JsonView(View.Basic.class)
	private String observacoes;

}
