package com.ibm.academia.apirest.restruleta.service.impl;

import com.ibm.academia.apirest.restruleta.entities.Apuesta;
import com.ibm.academia.apirest.restruleta.entities.Estado;
import com.ibm.academia.apirest.restruleta.entities.Ruleta;
import com.ibm.academia.apirest.restruleta.exception.BadRequestException;
import com.ibm.academia.apirest.restruleta.exception.NotFoundException;
import com.ibm.academia.apirest.restruleta.repository.ApuestaRepository;
import com.ibm.academia.apirest.restruleta.repository.RuletaRepository;
import com.ibm.academia.apirest.restruleta.service.RuletaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RuletaServiceImpl implements RuletaService {
    private static final String NOT_FOUND_ERROR_MSG = "No se encontró la ruleta";
    private static final String ACTUALMENTE_ABIERTA_ERROR_MSG = "La ruleta ya esta abierta";
    private static final String ACTUALMENTE_CERRADA_ERROR_MSG = "La ruleta ya esta cerrada";

    @Autowired
    private RuletaRepository ruletaRepository;
    @Autowired
    private ApuestaRepository apuestaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Ruleta> findAll() {
        List<Ruleta> ruletas = (List<Ruleta>) ruletaRepository.findAll();
        if (ruletas.isEmpty())
            throw new NotFoundException("Actualmente no existen ruletas");
        return ruletas;
    }

    @Override
    @Transactional
    public Ruleta createRuleta(Ruleta ruleta) {
        return ruletaRepository.save(ruleta);
    }

    @Override
    @Transactional
    public void abrirRuleta(Long id) {
        Ruleta ruleta = ruletaRepository.findById(id).orElseThrow(()
                -> new NotFoundException(NOT_FOUND_ERROR_MSG));
        if (ruleta.getEstado().equals(Estado.ABIERTO)) {
            throw new BadRequestException(ACTUALMENTE_ABIERTA_ERROR_MSG);
        }
        ruleta.setEstado(Estado.ABIERTO);
        ruletaRepository.save(ruleta);
    }

    @Override
    @Transactional
    public List<Apuesta> cerrarRuleta(Long id) {
        Ruleta ruleta = ruletaRepository.findById(id).orElseThrow(()
                -> new NotFoundException(NOT_FOUND_ERROR_MSG));
        if (ruleta.getEstado().equals(Estado.CERRADO)) {
            throw new BadRequestException(ACTUALMENTE_CERRADA_ERROR_MSG);
        }
        ruleta.setEstado(Estado.CERRADO);
        ruletaRepository.save(ruleta);

        return apuestaRepository.findApuestasByRuleta(ruleta.getId());
    }

}
