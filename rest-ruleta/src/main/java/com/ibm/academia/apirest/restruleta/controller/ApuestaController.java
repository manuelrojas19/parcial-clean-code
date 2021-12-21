package com.ibm.academia.apirest.restruleta.controller;

import com.ibm.academia.apirest.restruleta.entity.Apuesta;
import com.ibm.academia.apirest.restruleta.service.ApuestaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    @Operation(summary = "Realizar una apuesta en una ruleta que se encuentre abierta")
    @ApiResponse(responseCode = "201", description = "Datos de la apuesta realizada y el resultado de esta.",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Apuesta.class)
            )})
    private ResponseEntity<Apuesta> apostar(@RequestBody Apuesta apuesta, @PathVariable Long id) {
        Apuesta res = apuestaService.apostar(apuesta, id);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }
}
