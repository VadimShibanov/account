package com.bank.account.utils;

import org.junit.jupiter.api.Test;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class JsonUtilsTest {

    @Test
    void toJson() {
        ObjectToJson objectToJson = new ObjectToJson(22L, "Object", 45);

        String actualResult = JsonUtils.toJson(objectToJson);
        String expectedResult = "{\"id\":22,\"name\":\"Object\",\"age\":45}";

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void errorsToJson() {
        BindingResult bindingResult = mock(BindingResult.class);
        List<FieldError> fieldErrors = List.of(
                new FieldError("", "name", "name is not okay"),
                new FieldError("", "age", "age is not okay"));
        when(bindingResult.getFieldErrors()).thenReturn(fieldErrors);

        String actualResult = JsonUtils.errorsToJson(bindingResult);
        String expectedResult = "{\"name\":\"name is not okay\",\"age\":\"age is not okay\"}";

        assertEquals(expectedResult, actualResult);
    }

    private static class ObjectToJson {
        public Long id;
        public String name;
        public int age;

        public ObjectToJson(Long id, String name, int age) {
            this.id = id;
            this.name = name;
            this.age = age;
        }
    }
}