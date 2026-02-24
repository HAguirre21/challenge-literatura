package com.aluracursos.literalura.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

@Service
public class DataMapperService {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public <T> T mapJsonToClass(String json, Class<T> targetClass) {
        try {
            return objectMapper.readValue(json, targetClass);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error al convertir JSON: " + e.getMessage());
        }
    }
}
