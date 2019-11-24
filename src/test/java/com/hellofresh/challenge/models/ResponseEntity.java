package com.hellofresh.challenge.models;

import lombok.Data;

@Data
public class ResponseEntity {

    private Object body;
    private String statusCode;
    private int statusCodeValue;
}
