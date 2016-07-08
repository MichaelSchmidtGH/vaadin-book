/*
 * Funktion    : EErrorWeight.java
 * Titel       : 
 * Erstellt    : 27.11.2005
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
package com.example.myproject.exceptions;

/**
 * Modul		: EErrorWeight<br>
 * Erstellt		: 27.11.2005<br>
 * Beschreibung	: Aufz�hlung von Fehlergewichtungen.<br>
 * 
 * @author  schmidt
 * @version 1.0.00
 */

public enum EErrorWeight {
    /**
     * Fehler.
     */
    E, ERROR, 
    
    /**
     * Warnung.
     */
    W, WARNING,

    /**
     * Hinweis.
     */
    I, INFO

}
