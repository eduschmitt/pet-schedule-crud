package com.sippulse.pet.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sippulse.pet.utils.LocalDateTimeDeserializer;
import com.sippulse.pet.utils.LocalDateTimeSerializer;
import com.sippulse.pet.utils.View;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Classe que representa uma consulta agendada para um pet com um veterinário.
 * @author schmitt
 *
 */
@Data
@EqualsAndHashCode(of = {"veterinario", "pet", "dataHora"})
@Entity
@Table(name="consulta")
public class Consulta implements Serializable {

	private static final long serialVersionUID = 7872512844985238233L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@JsonView(View.Basic.class)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="CPF_VETERINARIO")
	@JsonView(View.VeterinarioDaConsulta.class)
	private Funcionario veterinario;
	
	@ManyToOne
	@JoinColumn(name="ID_PET")
	@JsonView(View.PetDaConsulta.class)
	private Pet pet;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy hh:mi")
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonView(View.Basic.class)
	private LocalDateTime dataHora;
	
	@JsonView(View.Basic.class)
	private String motivoRelatado;
	// private Long cpfCliente; coloca isso?

}
