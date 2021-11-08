package com.team.fithniti.demo.exception;

import lombok.Data;

import java.util.List;
@Data
public class ResourceExists extends RuntimeException{
    private String errorCode;
    private String msg;

    public ResourceExists(String errorCode, String msg) {
        super(msg);
        this.errorCode = errorCode;
    }
}
