package com.sippulse.pet.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Especialização da classe pessoa para representar o Cliente.
 * @author schmitt
 *
 */
@Data
@EqualsAndHashCode(callSuper=true)
@Entity
@Table(name="cliente")
public class Cliente extends Pessoa {
	private static final long serialVersionUID = -5935656909807416000L;
	
	private String telefone;
}
