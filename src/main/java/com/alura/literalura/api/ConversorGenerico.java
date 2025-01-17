package com.alura.literalura.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConversorGenerico {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public <T> T convertir(String json, Class<T> clase){
        try {
            return objectMapper.readValue(json, clase);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
