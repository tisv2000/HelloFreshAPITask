package com.hellofresh.challenge.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Response<T> {

    private int statusCode;
    private T responseBodyEntity;
}
