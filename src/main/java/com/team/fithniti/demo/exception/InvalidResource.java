package com.team.fithniti.demo.exception;

import lombok.Data;

import java.util.List;
@Data
public class InvalidResource extends RuntimeException{
    private List<String> errors;
    private String errorCode;
    private String msg;

    public InvalidResource(List<String> errors, String errorCode, String msg) {
        super(msg);
        this.errors = errors;
        this.errorCode = errorCode;
    }
    public InvalidResource(String errorCode, String msg) {
        super(msg);
        this.errorCode = errorCode;
    }
}
