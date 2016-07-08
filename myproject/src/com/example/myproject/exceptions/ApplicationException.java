package com.example.myproject.exceptions;

/**
 * Modul		: ApplicationException<br>
 * Erstellt		: 30.08.2005<br>
 * Beschreibung	: Allgemeine Exception für Fehlermeldungen der Anwendung.
 * 
 * @author  MS
 * @version 1.0.00
 */
public class ApplicationException extends Exception {
	private static final long serialVersionUID = 0x1L;
	
    private String key = null;

    
    public ApplicationException() { 
        super();
    }
    public ApplicationException(String message) {
        super(message);
    }

    public ApplicationException(String message, String key) {
        super(message);
        this.key = key;
    }

    public ApplicationException(Throwable cause) {
        super(cause);
    }
    
    public ApplicationException(String message, Throwable cause) {
        super(message + "\n(" + cause.getMessage() + ")", cause);
    }
    
    public String getKey() {
        return key;
    }
}
