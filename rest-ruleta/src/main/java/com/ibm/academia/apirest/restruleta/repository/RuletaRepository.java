package com.ibm.academia.apirest.restruleta.repository;

import com.ibm.academia.apirest.restruleta.entities.Ruleta;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RuletaRepository extends CrudRepository<Ruleta, Long> {
}
