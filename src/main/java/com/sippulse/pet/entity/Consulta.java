package com.sippulse.pet.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.sippulse.pet.entity.enums.StatusConsulta;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = {"cpfVeterinario", "idPet", "dataHora"})
public class Consulta implements Serializable {

	private static final long serialVersionUID = 7872512844985238233L;
	
	private Long id;
	private Long cpfVeterinario;
	private Long idPet;
	private LocalDateTime dataHora;
	private String motivoRelatado;
	private StatusConsulta status;
	// private Long cpfCliente; coloca isso?

}
