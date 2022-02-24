package com.example.spring.webflux.movies.service.exception;

public class ReviewsServerException extends RuntimeException{
    private String message;

    public ReviewsServerException(String message) {
        super(message);
        this.message = message;
    }
}
