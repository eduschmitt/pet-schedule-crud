package com.sippulse.pet.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sippulse.pet.entity.Funcionario;

@Repository
public interface FuncionarioRepository extends CrudRepository<Funcionario, Long>{

}
