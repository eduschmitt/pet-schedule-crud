package com.sippulse.pet.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.sippulse.pet.entity.Consulta;

/**
 * Repository de Consulta
 * @author schmitt
 *
 */
public interface ConsultaRepository extends CrudRepository<Consulta, Long>{
	
	/**
	 * Busca consultas pelo veterinário informado.
	 * @param cpfVeterinario
	 * @return
	 */
	public List<Consulta> findAllByVeterinarioCpf(Long cpfVeterinario);
	
	/**
	 * Busca consultas pela data informada, indo das 0h até as 23h50 do dia informado.
	 * @param dataHoraInicio
	 * @param dataHoraFim
	 * @return
	 */
	public List<Consulta> findAllByDataHoraBetween(LocalDateTime dataHoraInicio, LocalDateTime dataHoraFim);
	
	/**
	 * Busca consultas pelo veterinário e dia informados.
	 * @param cpfVeterinario
	 * @param dataHoraInicio
	 * @param dataHoraFim
	 * @return
	 */
	public List<Consulta> findAllByVeterinarioCpfAndDataHoraBetween(Long cpfVeterinario, LocalDateTime dataHoraInicio, LocalDateTime dataHoraFim);

	/**
	 * Busca todas as consultas de um determinado Cliente
	 * @param cpf do cliente
	 * @return
	 */
	public List<Consulta> findAllByPetClienteCpf(Long cpf);
	
	/**
	 * Busca uma consulta para um veterinário e horário específico.
	 * @param cpfVeterinario
	 * @param dataHora
	 * @return
	 */
	public Consulta findOneByVeterinarioCpfAndDataHora(Long cpfVeterinario, LocalDateTime dataHora);
}
