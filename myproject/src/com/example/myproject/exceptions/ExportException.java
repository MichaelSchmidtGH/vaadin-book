package com.example.myproject.exceptions;

/**
 * Modul		: ExportException<br>
 * Erstellt		: 01.08.2014<br>
 * Beschreibung	: Exception für Fehlermeldungen beim Datenexport.
 * 
 * @author  schmidt
 * @version 1.0.00
 */
public class ExportException extends Exception {
	private static final long serialVersionUID = 0x1L;
	
    private String key = null;

    
    public ExportException() { 
        super();
    }
    public ExportException(String message) {
        super(message);
    }

    public ExportException(String message, String key) {
        super(message);
        this.key = key;
    }

    public ExportException(Throwable cause) {
        super(cause);
    }
    
    public ExportException(String message, Throwable cause) {
        super(message + "\n(" + cause.getMessage() + ")", cause);
    }
    
    public String getKey() {
        return key;
    }
}
