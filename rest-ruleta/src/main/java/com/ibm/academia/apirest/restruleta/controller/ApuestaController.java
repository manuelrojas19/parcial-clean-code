package com.ibm.academia.apirest.restruleta.controller;

import com.ibm.academia.apirest.restruleta.dto.RuletaDto;
import com.ibm.academia.apirest.restruleta.entities.Apuesta;
import com.ibm.academia.apirest.restruleta.entities.Ruleta;
import com.ibm.academia.apirest.restruleta.mapper.RuletaMapper;
import com.ibm.academia.apirest.restruleta.service.ApuestaService;
import com.ibm.academia.apirest.restruleta.service.RuletaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class ApuestaController {
    @Autowired
    private ApuestaService apuestaService;

    /**
     * Método para realizar una apuesta en una ruleta que se encuentre abierta
     *
     * @param apuesta RequestBody de un objeto con los datos de la apuesta a crear, se debe incluir un color
     *                a apostar, un número o ambos y el monto a apostar, máximo 10000.
     * @param id      Id de la ruleta donde se realizara la apuesta, esta ruleta debe tener el estado abierto,
     *                en caso contrario la capa de servicio lanzara una excepción.
     * @return response entity que contiene los datos la apuesta creada y el resultado de esta.
     */
    @PostMapping("/ruletas/{id}/apuestas")
    private ResponseEntity<Apuesta> apostar(@RequestBody Apuesta apuesta, @PathVariable Long id) {
        Apuesta res = apuestaService.apostar(apuesta, id);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

}
