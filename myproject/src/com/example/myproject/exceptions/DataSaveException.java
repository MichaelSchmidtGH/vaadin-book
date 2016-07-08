package com.example.myproject.exceptions;
import java.io.Serializable;


/**
 * Exception for problems saving data to the database.
 * 
 * @author schmidt
 * @version 1.0.00
 */
public class DataSaveException extends Exception implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String key = null;
    private String errorCode = null;

    
    public DataSaveException() {
    }

    public DataSaveException(String message) {
        super(message);
    }

    public DataSaveException(String message, String key) {
        super(message);
        this.key = key;
      }

    public DataSaveException(String message, String key, String errorCode) {
        super(message);
        this.key = key;
        this.errorCode = errorCode;
      }

    public DataSaveException(String message, Throwable cause) {
        super(message + "\n(" + cause.getMessage() + ")", cause);
    }
    
    public DataSaveException(Throwable cause) {
        super(cause);
    }
    
    public String getKey() {
        return key;
    }
    
    public String getErrorCode() {
        return errorCode;
    }
}
