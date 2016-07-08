package com.example.myproject.exceptions;
import java.io.Serializable;


/**
 * Exception for problems during login.
 * 
 * @author schmidt
 * @version 1.0.00
 */
public class LoginException extends Exception implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String key = null;

    
    public LoginException() {
    }

    public LoginException(String message) {
        super(message);
    }

    public LoginException(String message, String key) {
        super(message);
        this.key = key;
    }

    public LoginException(String message, Throwable cause) {
        super(message + "\n(" + cause.getMessage() + ")", cause);
    }
    
    public LoginException(Throwable cause) {
        super(cause);
    }
    
    public String getKey() {
        return key;
    }
    
}
