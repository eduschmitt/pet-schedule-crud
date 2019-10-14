package com.sippulse.pet.service;

import java.time.LocalDate;
import java.util.List;

import com.sippulse.pet.entity.Consulta;

/**
 * Interface de serviços para o CRUD de consulta
 * @author schmitt
 *
 */
public interface ConsultaService extends CrudService<Consulta, Long>{
	
	/**
	 * Busca de consultas por veterinário e/ou data.
	 * @param cpfVeterinario
	 * @param dataConsulta
	 * @return
	 */
	public List<Consulta> findByVeterinarioData(Long cpfVeterinario, LocalDate dataConsulta);
	
	/**
	 * Busca de consultas por cliente.
	 * @param cpfCliente
	 * @return
	 */
	public List<Consulta> findByCliente(Long cpfCliente);

}
