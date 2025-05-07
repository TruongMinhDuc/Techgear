package com.techgear.techgear_be.exceptions;

import lombok.Value;

import java.time.Instant;

@Value
public class ErrorMessage {
    int statusCode;
    Instant timestamp;
    String message;
    String description;
}
