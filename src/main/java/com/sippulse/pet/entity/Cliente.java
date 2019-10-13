package com.sippulse.pet.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;
import com.sippulse.pet.utils.View;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Especialização da classe pessoa para representar o Cliente.
 * @author schmitt
 *
 */
@Data
@EqualsAndHashCode(callSuper=true)
@Entity
@Table(name="cliente")
@ToString(exclude="pets")
public class Cliente extends Pessoa {
	private static final long serialVersionUID = -5935656909807416000L;
	
	@JsonView(View.Basic.class)
	private String telefone;
	
	@OneToMany(mappedBy = "cliente")
	@JsonView(View.ListaPet.class)
	private List<Pet> pets;
}
