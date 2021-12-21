package com.ibm.academia.apirest.restruleta.service.impl;

import com.ibm.academia.apirest.restruleta.entity.Apuesta;
import com.ibm.academia.apirest.restruleta.entity.Color;
import com.ibm.academia.apirest.restruleta.entity.Estado;
import com.ibm.academia.apirest.restruleta.entity.Ruleta;
import com.ibm.academia.apirest.restruleta.exception.BadRequestException;
import com.ibm.academia.apirest.restruleta.exception.NotFoundException;
import com.ibm.academia.apirest.restruleta.repository.ApuestaRepository;
import com.ibm.academia.apirest.restruleta.repository.RuletaRepository;
import com.ibm.academia.apirest.restruleta.service.ApuestaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ApuestaServiceImplTest {
    private ApuestaService apuestaService;
    private RuletaRepository ruletaRepository;

    @BeforeEach
    void setUp() {
        ApuestaRepository apuestaRepository = mock(ApuestaRepository.class);
        ruletaRepository = mock(RuletaRepository.class);
        apuestaService = new ApuestaServiceImpl(ruletaRepository, apuestaRepository);
    }

    @Test
    @DisplayName("Test: Corroborar BadRequestException al hacer una apuesta en una ruleta cerrada")
    void itShouldThrownBadRequestException() {
        Ruleta ruleta = Ruleta.builder().id(1L).estado(Estado.CERRADO).build();
        Apuesta apuesta = Apuesta.builder()
                .color(Color.NEGRO)
                .dineroApostado(BigDecimal.valueOf(10000))
                .nombreApostador("MARR").build();

        when(ruletaRepository.findById(1L)).thenReturn(Optional.of(ruleta));

        assertThrows(BadRequestException.class,
                () -> apuestaService.apostar(apuesta, 1L));
    }

    @Test
    @DisplayName("Test: Corroborar NotFoundException al hacer una apuesta en una ruleta no existente")
    void itShouldThrownNotFoundException() {
        Apuesta apuesta = Apuesta.builder()
                .color(Color.NEGRO)
                .dineroApostado(BigDecimal.valueOf(10000))
                .nombreApostador("MARR").build();

        when(ruletaRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,
                () -> apuestaService.apostar(apuesta, 1L));
    }
}