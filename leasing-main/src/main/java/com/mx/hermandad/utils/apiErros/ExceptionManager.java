package com.mx.hermandad.utils.apiErros;

import org.springframework.http.HttpStatus;

/**
 *
 * @author Ing. Luis Arturo Aguilar Limas
 */
public class ExceptionManager extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private HttpStatus status;
    private String message;
    private Throwable ex;

    public ExceptionManager(HttpStatus status, String message, Throwable ex) {
        this.status = status;
        this.message = message;
        this.ex = ex;
    }

    public ExceptionManager(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Throwable getEx() {
        return ex;
    }

    public void setEx(Throwable ex) {
        this.ex = ex;
    }

}
