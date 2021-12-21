package com.ibm.academia.apirest.restruleta.mapper;

import com.ibm.academia.apirest.restruleta.dto.RuletaDto;
import com.ibm.academia.apirest.restruleta.entities.Ruleta;

public class RuletaMapper {
    public static RuletaDto ruletaToRuletaDto(Ruleta ruleta) {
        return RuletaDto.builder().id(ruleta.getId()).build();
    }
}
