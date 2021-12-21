package com.ibm.academia.apirest.restruleta.service;

import com.ibm.academia.apirest.restruleta.entity.Apuesta;

public interface ApuestaService {
    Apuesta apostar(Apuesta apuesta, Long ruletaId);
}
