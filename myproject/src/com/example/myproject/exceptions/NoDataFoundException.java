package com.example.myproject.exceptions;
import java.io.Serializable;


/**
 * Exception if the data was not found in the database.
 * 
 * @author schmidt
 * @version 1.0.00
 */
public class NoDataFoundException extends Exception implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String key = null;

    
    public NoDataFoundException() {
    }

    public NoDataFoundException(String message) {
        super(message);
    }

    public NoDataFoundException(String message, String key) {
        super(message);
        this.key = key;
      }

    public NoDataFoundException(String message, Throwable cause) {
        super(message + "\n(" + cause.getMessage() + ")", cause);
    }
    
    public NoDataFoundException(Throwable cause) {
        super(cause);
    }
    
    public String getKey() {
        return key;
    }
    
}
