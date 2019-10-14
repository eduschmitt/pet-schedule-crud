package com.sippulse.pet.entity.enums;

/**
 * Enum de Sexo.
 * @author schmitt
 *
 */
public enum Sexo {

	M(1, "Masculino"), F(2, "Feminino");

	public String descricao;

	Sexo(Integer codigo, String descricao) {
		this.descricao = descricao;
	}
}
