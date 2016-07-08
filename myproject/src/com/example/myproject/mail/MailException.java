/*
 * Funktion    : MailException.java
 * Titel       : 
 * Erstellt    : 22.10.2006
 * Author      : CSF GmbH / Administrator
 * Beschreibung: 
 * Anmerkungen :
 * Parameter   : keine
 * R�ckgabe    : keine
 * Aufruf      : 
 * 
 * �nderungen:
 * -----------
 * Author      :
 * Datum       :
 * Kennzeichen :
 * Beschreibung:
 * Anmerkungen :
 * Parameter   :
 * 
 */
package com.example.myproject.mail;

/**
 * Modul        : MailException<br>
 * Erstellt     : 22.10.2006<br>
 * Beschreibung : Exception bei fehlendem Parameter "Id".<br>
 * 
 * @author  schmidt
 * @version 1.0.00
 */
public class MailException extends Exception {
    private static final long serialVersionUID = 1; 
    
    /**
     * Standardkonstruktor.
     * Ruft super().
     */
    public MailException() {
        super();
    }

    /**
     * Konstruktor mit Meldungstext.
     * Ruft super(Meldungstext).
     * 
     * @param s Meldungstext.
     */
    public MailException(final String s) {
        super(s);
    }

}
