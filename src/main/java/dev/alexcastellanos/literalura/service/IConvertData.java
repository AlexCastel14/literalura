package dev.alexcastellanos.literalura.service;

public interface IConvertData {
    <T> T obtainData(String json, Class<T> clase);
}
