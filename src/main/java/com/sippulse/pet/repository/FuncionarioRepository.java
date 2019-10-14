package com.sippulse.pet.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sippulse.pet.entity.Funcionario;
import com.sippulse.pet.entity.enums.TipoFuncionario;

/**
 * Repository de Funcionário
 * @author schmitt
 *
 */
@Repository
public interface FuncionarioRepository extends CrudRepository<Funcionario, Long>{

	public Funcionario findOneByCpfAndTipoFuncionario(Long cpf, TipoFuncionario tipoFuncionario);
}
