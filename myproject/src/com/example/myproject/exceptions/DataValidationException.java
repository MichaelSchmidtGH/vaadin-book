package com.example.myproject.exceptions;
import java.io.Serializable;


/**
 * Exception for problems validating view data.
 * 
 * @author schmidt
 * @version 1.0.00
 */
public class DataValidationException extends Exception implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String key = null;

    
    public DataValidationException() {
    }

    public DataValidationException(String message) {
        super(message);
    }

    public DataValidationException(String message, String key) {
        super(message);
        this.key = key;
      }

    public DataValidationException(String message, Throwable cause) {
        super(message + "\n(" + cause.getMessage() + ")", cause);
    }
    
    public DataValidationException(Throwable cause) {
        super(cause);
    }
    
    public String getKey() {
        return key;
    }
    
}
