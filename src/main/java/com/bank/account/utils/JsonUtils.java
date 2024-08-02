package com.bank.account.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

public class JsonUtils {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Превращает заданный объект в JSON
     * @return JSON, в случае неудачи - пустую строку
     */
    @SneakyThrows(JsonProcessingException.class)
    public static String toJson(Object object) {
        return objectMapper.writeValueAsString(object);
    }

    public static String errorsToJson(BindingResult bindingResult) {
        Map<String, String> errors = new HashMap<>();

        for (FieldError error : bindingResult.getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }

        return toJson(errors);
    }
}
