package com.sippulse.pet.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.sippulse.pet.entity.Consulta;

public interface ConsultaRepository extends CrudRepository<Consulta, Long>{
	
	public List<Consulta> findAllByVeterinarioCpf(Long cpfVeterinario);
	
	public List<Consulta> findAllByDataHoraBetween(LocalDateTime dataHoraInicio, LocalDateTime dataHoraFim);
	
	public List<Consulta> findAllByVeterinarioCpfAndDataHoraBetween(Long cpfVeterinario, LocalDateTime dataHoraInicio, LocalDateTime dataHoraFim);

	public List<Consulta> findAllByPetClienteCpf(Long cpf);
	
	public Consulta findOneByVeterinarioCpfAndDataHora(Long cpfVeterinario, LocalDateTime dataHora);
}
