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
     * Método que crea una ruleta para ser utilizada en apuestas
     *
     * @param apuesta objeto con los datos de la apuesta a crear.
     * @return response entity que contiene la apuesta creada y el estado de esta.
     */
    @PostMapping("/ruletas/{id}/apuestas")
    private ResponseEntity<Apuesta> apostar(@RequestBody Apuesta apuesta, @PathVariable Long id) {
        Apuesta res = apuestaService.apostar(apuesta, id);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

}
