package com.sippulse.pet.service;

import java.time.LocalDate;
import java.util.List;

import com.sippulse.pet.entity.Consulta;

public interface ConsultaService extends CrudService<Consulta, Long>{
	
	public List<Consulta> findByVeterinarioData(Long cpfVeterinario, LocalDate dataConsulta);
	
	public List<Consulta> findByCliente(Long cpfCliente);

}
