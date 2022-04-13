package com.work.urlshortener.exception;

public class UrlAlreadyExistException extends RuntimeException {

    public UrlAlreadyExistException(String message) {
        super(message);
    }

}
