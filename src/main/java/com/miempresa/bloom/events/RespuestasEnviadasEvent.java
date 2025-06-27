package com.miempresa.bloom.events;

import java.util.Map;

public class RespuestasEnviadasEvent implements Event {
    private final Map<String,Integer> respuestas;

    public RespuestasEnviadasEvent(Map<String,Integer> respuestas) {
        this.respuestas = respuestas;
    }

    public Map<String,Integer> getRespuestas() {
        return respuestas;
    }
}
