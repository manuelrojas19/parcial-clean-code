package com.ibm.academia.apirest.restruleta.service.impl;

import com.ibm.academia.apirest.restruleta.entity.*;
import com.ibm.academia.apirest.restruleta.exception.BadRequestException;
import com.ibm.academia.apirest.restruleta.exception.NotFoundException;
import com.ibm.academia.apirest.restruleta.repository.ApuestaRepository;
import com.ibm.academia.apirest.restruleta.repository.RuletaRepository;
import com.ibm.academia.apirest.restruleta.service.ApuestaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@Slf4j
public class ApuestaServiceImpl implements ApuestaService {
    private static final String RULETA_NOT_FOUND_ERROR_MSG = "No se encontró la ruleta para realizar apuestas";
    private static final String RULETA_CERRADA_ERROR_MSG = "La ruleta se encuentra cerrada, no se pueden realizar apuestas";

    private final RuletaRepository ruletaRepository;
    private final ApuestaRepository apuestaRepository;

    @Autowired
    public ApuestaServiceImpl(RuletaRepository ruletaRepository, ApuestaRepository apuestaRepository) {
        this.ruletaRepository = ruletaRepository;
        this.apuestaRepository = apuestaRepository;
    }

    @Override
    @Transactional
    public Apuesta apostar(Apuesta apuesta, Long ruletaId) {
        Ruleta ruleta = ruletaRepository.findById(ruletaId)
                .orElseThrow(() -> new NotFoundException(RULETA_NOT_FOUND_ERROR_MSG));
        apuesta.setRuleta(ruleta);

        if (!ruleta.getEstado().equals(Estado.ABIERTO))
            throw new BadRequestException(RULETA_CERRADA_ERROR_MSG);

        if (evaluarApuesta(apuesta)) {
            apuesta.setResultado(Resultado.GANADOR);
        } else {
            apuesta.setResultado(Resultado.PERDEDOR);
        }

        return apuestaRepository.save(apuesta);
    }

    private boolean evaluarApuesta(Apuesta apuesta) {
        boolean res = false;

        Integer num = (int) (Math.random() * 36);
        Color[] colors = Color.values();
        Color color = colors[(Math.random() * 1) > 0.5 ? 1 : 0];

        if (Objects.nonNull(apuesta.getNumero()) && Objects.nonNull(apuesta.getColor())) {
            if (num.equals(apuesta.getNumero()) && color.equals(apuesta.getColor()))
                res = true;
        } else if (Objects.nonNull(apuesta.getColor())) {
            if (color.equals(apuesta.getColor()))
                res = true;
        } else if (Objects.nonNull(apuesta.getNumero())) {
            if (num.equals(apuesta.getNumero()))
                res = true;
        }
        return res;
    }

}
