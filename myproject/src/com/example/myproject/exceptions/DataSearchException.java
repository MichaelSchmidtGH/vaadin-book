package com.example.myproject.exceptions;
import java.io.Serializable;


/**
 * Exception for problems searching data in the database.
 * 
 * @author schmidt
 * @version 1.0.00
 */
public class DataSearchException extends Exception implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String key = null;

    
    public DataSearchException() {
    }

    public DataSearchException(String message) {
        super(message);
    }

    public DataSearchException(String message, String key) {
        super(message);
        this.key = key;
      }

    public DataSearchException(String message, Throwable cause) {
        super(message + "\n(" + cause.getMessage() + ")", cause);
    }
    
    public DataSearchException(Throwable cause) {
        super(cause);
    }
    
    public String getKey() {
        return key;
    }
    
}
