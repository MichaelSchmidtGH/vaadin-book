package com.example.myproject.exceptions;
import java.io.Serializable;

/**
 * Exception for problems reading or processing configuration files.
 * 
 * @author schmidt
 * @version 1.0.00
 */
public class ConfigException extends Exception implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String key = null;

    
    public ConfigException() {
    }

    public ConfigException(String message) {
        super(message);
    }

    public ConfigException(String message, String key) {
        super(message);
        this.key = key;
    }

    public ConfigException(String message, Throwable cause) {
        super(message + "\n(" + cause.getMessage() + ")", cause);
    }
    
    public ConfigException(Throwable cause) {
        super(cause);
    }
    
    public String getKey() {
        return key;
    }
    
}
