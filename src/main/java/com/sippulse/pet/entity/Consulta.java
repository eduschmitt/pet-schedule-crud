package com.sippulse.pet.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = {"funcionario", "pet", "dataHora"})
@Entity
@Table(name="consulta")
public class Consulta implements Serializable {

	private static final long serialVersionUID = 7872512844985238233L;
	
	@Id
	private Long id;
	private Funcionario funcionario;
	private Pet pet;
	private LocalDateTime dataHora;
	private String motivoRelatado;
	// private Long cpfCliente; coloca isso?

}
