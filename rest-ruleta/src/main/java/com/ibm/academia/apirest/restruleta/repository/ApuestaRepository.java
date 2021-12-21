package com.ibm.academia.apirest.restruleta.repository;

import com.ibm.academia.apirest.restruleta.entity.Apuesta;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApuestaRepository extends CrudRepository<Apuesta, Long> {

    @Query("SELECT r.apuestas FROM Ruleta r")
    public List<Apuesta> findApuestasByRuleta(Long idRuleta);
}
