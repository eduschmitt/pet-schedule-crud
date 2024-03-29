package com.sippulse.pet.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sippulse.pet.entity.Pet;

/**
 * Repository de Pet.
 * @author schmitt
 *
 */
@Repository
public interface PetRepository extends CrudRepository<Pet, Long>{
	
}
