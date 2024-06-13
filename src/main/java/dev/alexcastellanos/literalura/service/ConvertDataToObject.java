package dev.alexcastellanos.literalura.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConvertDataToObject implements IConvertData {
    private ObjectMapper mapper = new ObjectMapper();
    @Override
    public <T> T obtainData(String json, Class<T> clase) {
        try {
            return mapper.readValue(json,clase);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
