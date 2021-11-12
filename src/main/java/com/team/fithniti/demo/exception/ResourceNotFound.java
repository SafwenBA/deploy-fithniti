package com.team.fithniti.demo.exception;

import java.util.List;

public class ResourceNotFound extends RuntimeException{
    private List<String> errors;
    private String errorCode;
    private String msg;

    public ResourceNotFound(List<String> errors, String errorCode, String msg) {
        super(msg);
        this.errors = errors;
        this.errorCode = errorCode;
    }
    public ResourceNotFound(String errorCode, String msg) {
        super(msg);
        this.errorCode = errorCode;
<<<<<<< HEAD
    }
    public ResourceNotFound(String message) {
        super(message) ;
=======
>>>>>>> origin/dev-hamdi
    }
}