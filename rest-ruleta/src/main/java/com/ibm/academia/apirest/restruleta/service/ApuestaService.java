package com.ibm.academia.apirest.restruleta.service;

import com.ibm.academia.apirest.restruleta.entities.Apuesta;
import com.ibm.academia.apirest.restruleta.entities.Ruleta;

public interface ApuestaService {
    Apuesta apostar(Apuesta apuesta, Long ruletaId);
}
