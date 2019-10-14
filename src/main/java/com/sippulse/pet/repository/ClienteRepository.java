package com.sippulse.pet.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sippulse.pet.entity.Cliente;

/**
 * Repository de Cliente.
 * @author schmitt
 *
 */
@Repository
public interface ClienteRepository extends CrudRepository<Cliente, Long>{

}
