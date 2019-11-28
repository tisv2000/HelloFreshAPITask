package com.hellofresh.challenge.data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hellofresh.challenge.models.Response;

public class JsonMapper {

    public static <T> Response<T> parseResponse(io.restassured.response.Response response,
                                                 int expectedStatus,
                                                 Class<T> clazz) {
        T gottenObject = null;
        if (response.statusCode() == expectedStatus) {
            gottenObject = readObjectFromJson(response.body().asString(), clazz);
        }
        return new Response<T>(response.statusCode(), gottenObject);
    }

    private static <T> T readObjectFromJson(String json, Class<T> clazz) {
        try {
            return new ObjectMapper().readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Can't read object. Class: " + clazz.getName() + ". JSON: " + json);
        }
    }

    public static String writeObjectToJson(Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Can't write object to json. Class: " + object.getClass().getName());
        }
    }
}
