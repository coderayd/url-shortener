package com.work.urlshortener.exception;

import lombok.*;

import java.util.Date;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorObject {

    protected int statusCode;
    protected Date timestamp;
    protected String message;
    protected String description;
}

@Getter
class ApiError extends ErrorObject{

    private final Map<String, String> errors;

    public ApiError(int statusCode, Date timestamp, String message, String description, Map<String, String> errors) {
        super(statusCode,timestamp,message,description);
        this.errors = errors;
    }
}