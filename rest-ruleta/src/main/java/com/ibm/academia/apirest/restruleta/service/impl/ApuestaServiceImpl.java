package com.ibm.academia.apirest.restruleta.service.impl;

import com.ibm.academia.apirest.restruleta.entities.*;
import com.ibm.academia.apirest.restruleta.exception.BadRequestException;
import com.ibm.academia.apirest.restruleta.exception.NotFoundException;
import com.ibm.academia.apirest.restruleta.repository.ApuestaRepository;
import com.ibm.academia.apirest.restruleta.repository.RuletaRepository;
import com.ibm.academia.apirest.restruleta.service.ApuestaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class ApuestaServiceImpl implements ApuestaService {
    @Autowired
    private RuletaRepository ruletaRepository;

    @Autowired
    private ApuestaRepository apuestaRepository;


    @Override
    @Transactional
    public Apuesta apostar(Apuesta apuesta, Long ruletaId) {
        Ruleta ruleta = ruletaRepository.findById(ruletaId)
                .orElseThrow(() -> new NotFoundException("No se encontró la ruleta"));
        apuesta.setRuleta(ruleta);

        if (!ruleta.getEstado().equals(Estado.ABIERTO))
            throw new BadRequestException("La ruleta no se encuentra abierta");

        Integer num = (int) (Math.random() * 36);

        Color[] colors = Color.values();
        Color color = colors[(Math.random() * 1) > 0.5 ? 1 : 0];

        if (Objects.nonNull(apuesta.getNumero()) && Objects.nonNull(apuesta.getColor())) {
            if (num.equals(apuesta.getNumero()) && color.equals(apuesta.getColor())) {
                apuesta.setResultado(Resultado.GANADOR);
            } else {
                apuesta.setResultado(Resultado.PERDEDOR);
            }
        } else if (Objects.nonNull(apuesta.getColor())) {
            if (color.equals(apuesta.getColor()))
                apuesta.setResultado(Resultado.GANADOR);
            else {
                apuesta.setResultado(Resultado.PERDEDOR);
            }
        } else if (Objects.nonNull(apuesta.getNumero())) {
            if (num.equals(apuesta.getNumero()))
                apuesta.setResultado(Resultado.GANADOR);
            else {
                apuesta.setResultado(Resultado.PERDEDOR);
            }
        }

        return apuestaRepository.save(apuesta);
    }
}
