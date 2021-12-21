package com.ibm.academia.apirest.restruleta.service;

import com.ibm.academia.apirest.restruleta.entity.Apuesta;
import com.ibm.academia.apirest.restruleta.entity.Ruleta;

import java.util.List;

public interface RuletaService {
    List<Ruleta> findAll();
    Ruleta createRuleta(Ruleta ruleta);
    void abrirRuleta(Long id);
    List<Apuesta> cerrarRuleta(Long id);
}
