package com.ibm.academia.apirest.restruleta.data;

import com.ibm.academia.apirest.restruleta.entity.Estado;
import com.ibm.academia.apirest.restruleta.entity.Ruleta;

import java.util.List;

public class DummyData {
    public static List<Ruleta> getRuletas() {
        return List.of(
                Ruleta.builder().id(1L).estado(Estado.CERRADO).build(),
                Ruleta.builder().id(2L).estado(Estado.ABIERTO).build(),
                Ruleta.builder().id(3L).estado(Estado.ABIERTO).build(),
                Ruleta.builder().id(4L).estado(Estado.CERRADO).build(),
                Ruleta.builder().id(5L).estado(Estado.ABIERTO).build()
        );
    }
}
