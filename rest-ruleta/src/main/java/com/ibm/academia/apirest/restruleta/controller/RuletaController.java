package com.ibm.academia.apirest.restruleta.controller;

import com.ibm.academia.apirest.restruleta.dto.RuletaDto;
import com.ibm.academia.apirest.restruleta.entity.Apuesta;
import com.ibm.academia.apirest.restruleta.entity.Ruleta;
import com.ibm.academia.apirest.restruleta.mapper.RuletaMapper;
import com.ibm.academia.apirest.restruleta.service.RuletaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/ruletas")
public class RuletaController {
    @Autowired
    private RuletaService ruletaService;

    /**
     * Endpoint que devuelve la lista de las ruletas registradas en la bd con sus respectivos estados
     *
     * @return response entity que contiene una lista de objetos ruleta.
     */
    @Operation(summary = "Obtener una lista de las ruletas registradas en la bd")
    @ApiResponse(responseCode = "200", description = "Lista con las ruletas registradas y su estado actual.",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Ruleta.class)
            )})
    @GetMapping
    private ResponseEntity<List<Ruleta>> findAll() {
        List<Ruleta> res = ruletaService.findAll();
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    /**
     * Endpoint que crea una ruleta para ser utilizada en apuestas
     *
     * @param ruleta objeto con los datos de la ruleta a crear.
     * @return response entity que contiene únicamente el identificador de la ruleta creada.
     */
    @Operation(summary = "Crear una ruleta")
    @ApiResponse(responseCode = "200", description = "Id de la ruleta creada",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = RuletaDto.class)
            )})
    @PostMapping
    private ResponseEntity<RuletaDto> createRuleta(@Validated @RequestBody Ruleta ruleta) {
        RuletaDto res = RuletaMapper.ruletaToRuletaDto(ruletaService.createRuleta(ruleta));
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    /**
     * Endpoint que cambia el estado de una ruleta a abierto para poder realizar apuestas
     *
     * @param id de la ruleta a abrir, en caso de que su estado sea abierto o la ruleta no exista
     *           la capa de servicio lanzara una excepción.
     * @return response entity que contiene el estado de la solicitud.
     */
    @Operation(summary = "Abrir una ruleta para permitir apuestas")
    @ApiResponse(responseCode = "200", description = "Estado de la solicitud",
            content = {@Content(mediaType = "application/json"
            )})
    @PutMapping("/{id}/abrir")
    private ResponseEntity<?> abrirRuleta(@PathVariable Long id) {
        Map<String, String> res = new HashMap<>();
        ruletaService.abrirRuleta(id);
        res.put("Mensaje", "Ruleta abierta exitosamente");
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    /**
     * Endpoint que cambia el estado de una ruleta a cerrado esto evita que se puedan realizar más apuestas.
     *
     * @param id de la ruleta a cerrar, en caso de que su estado sea cerrado o la ruleta no exista
     *           la capa de servicio lanzara una excepción.
     * @return response entity que contiene el estado de la solicitud y una lista de las apuestas realizadas en
     * la ruleta.
     */
    @Operation(summary = "Cerrar una ruleta.")
    @ApiResponse(responseCode = "200", description = "Estado de la solicitud y lista de las apuestas realizadas" +
            "en la ruleta",
            content = {@Content(mediaType = "application/json"
            )})
    @PutMapping("/{id}/cerrar")
    private ResponseEntity<?> cerrarRuleta(@PathVariable Long id) {
        Map<String, Object> res = new HashMap<>();
        List<Apuesta> apuestas = ruletaService.cerrarRuleta(id);
        res.put("Mensaje", "Ruleta cerrada exitosamente");
        res.put("Apuestas", apuestas);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
