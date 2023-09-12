package com.wda.bookstore.exception;

public class ErrorResponse {
    private int status;
    private String message;

    public ErrorResponse() {
        // Construtor vazio necessário para serialização JSON
    }

    public ErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}