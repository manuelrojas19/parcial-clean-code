package com.ibm.academia.apirest.restruleta.controller;

import com.ibm.academia.apirest.restruleta.dto.RuletaDto;
import com.ibm.academia.apirest.restruleta.entities.Apuesta;
import com.ibm.academia.apirest.restruleta.entities.Ruleta;
import com.ibm.academia.apirest.restruleta.mapper.RuletaMapper;
import com.ibm.academia.apirest.restruleta.service.RuletaService;
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

    @GetMapping
    private ResponseEntity<List<Ruleta>> findAll() {
        List<Ruleta> res = ruletaService.findAll();
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    /**
     * Método que crea una ruleta para ser utilizada en apuestas
     *
     * @param ruleta objeto con los datos de la ruleta a crear.
     * @return response entity que contiene el id de la ruleta creada.
     */
    @PostMapping
    private ResponseEntity<RuletaDto> createRuleta(@Validated @RequestBody Ruleta ruleta) {
        RuletaDto res = RuletaMapper.ruletaToRuletaDto(ruletaService.createRuleta(ruleta));
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/abrir")
    private ResponseEntity<?> abrirRuleta(@PathVariable Long id) {
        Map<String, String> res = new HashMap<>();
        ruletaService.abrirRuleta(id);
        res.put("Mensaje", "Ruleta abierta exitosamente");
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PutMapping("/{id}/cerrar")
    private ResponseEntity<?> cerrarRuleta(@PathVariable Long id) {
        Map<String, Object> res = new HashMap<>();
        List<Apuesta> apuestas = ruletaService.cerrarRuleta(id);
        res.put("Mensaje", "Ruleta cerrada exitosamente");
        res.put("Apuestas", apuestas);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
