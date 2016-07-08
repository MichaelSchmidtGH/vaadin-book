package com.example.myproject.exceptions;
import java.io.Serializable;

/**
 * Exception for problems in DataAccessService.
 * 
 * @author schmidt
 * @version 1.0.00
 */
public class DataAccessException extends Exception implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String key = null;

    
    public DataAccessException() {
    }

    public DataAccessException(String message) {
        super(message);
    }

    public DataAccessException(String message, String key) {
        super(message);
        this.key = key;
    }

    public DataAccessException(String message, Throwable cause) {
        super(message + "\n(" + cause.getMessage() + ")", cause);
    }
    
    public DataAccessException(Throwable cause) {
        super(cause);
    }
    
    public String getKey() {
        return key;
    }
    
}
