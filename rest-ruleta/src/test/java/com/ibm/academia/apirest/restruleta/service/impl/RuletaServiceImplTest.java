package com.ibm.academia.apirest.restruleta.service.impl;

import com.ibm.academia.apirest.restruleta.data.DummyData;
import com.ibm.academia.apirest.restruleta.entity.Estado;
import com.ibm.academia.apirest.restruleta.entity.Ruleta;
import com.ibm.academia.apirest.restruleta.exception.BadRequestException;
import com.ibm.academia.apirest.restruleta.repository.ApuestaRepository;
import com.ibm.academia.apirest.restruleta.repository.RuletaRepository;
import com.ibm.academia.apirest.restruleta.service.RuletaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


public class RuletaServiceImplTest {
    private RuletaService ruletaService;
    private RuletaRepository ruletaRepository;

    @BeforeEach
    void setUp() {
        ApuestaRepository apuestaRepository = mock(ApuestaRepository.class);
        ruletaRepository = mock(RuletaRepository.class);
        ruletaService = new RuletaServiceImpl(ruletaRepository, apuestaRepository);
    }

    @Test
    void findAll() {
        when(ruletaRepository.findAll()).thenReturn(DummyData.getRuletas());
        List<Ruleta> expected = ruletaService.findAll();
        assertThat(expected).isEqualTo(DummyData.getRuletas());
    }

    @Test
    void createRuleta() {
        Ruleta ruleta = new Ruleta();
        when(ruletaRepository.save(ruleta)).thenReturn(ruleta);
        Ruleta expected = ruletaService.createRuleta(ruleta);
        assertThat(expected).isInstanceOf(Ruleta.class);
    }

    @Test
    @DisplayName("Test: Corroborar BadRequestException al intentar abrir una ruleta abierta")
    void itShouldThrownBadRequestOpenException() {
        Ruleta ruleta = Ruleta.builder().id(1L).estado(Estado.ABIERTO).build();

        when(ruletaRepository.findById(1L)).thenReturn(Optional.of(ruleta));

        assertThrows(BadRequestException.class,
                () ->  ruletaService.abrirRuleta(1L));
    }

    @Test
    @DisplayName("Test: Corroborar BadRequestException al intentar cerrar una ruleta cerrada")
    void itShouldThrownBadRequestCloseException() {
        Ruleta ruleta = Ruleta.builder().id(1L).estado(Estado.CERRADO).build();

        when(ruletaRepository.findById(1L)).thenReturn(Optional.of(ruleta));

        assertThrows(BadRequestException.class,
                () ->  ruletaService.cerrarRuleta(1L));
    }

}