/*
 * Funktion    : ErrorMail.java
 * Titel       :
 * Erstellt    : 27.10.2006
 * Author      : CSF GmbH / schmidt
 * Beschreibung: 
 * Anmerkungen : 
 * Parameter   : 
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

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.apache.log4j.Logger;

import com.example.myproject.config.Config;
import com.example.myproject.exceptions.ConfigException;
import com.example.myproject.utils.Utils;


/**
 * Modul		: ErrorMail<br>
 * Erstellt		: 27.10.2006<br>
 * Beschreibung	: Mailen von Fehlern, die einen Alarm ausl�sen sollen.
 * 
 * @author schmidt
 * @version 1.0.00
 */
public class ErrorMail {
	private Logger logger = null;
    private Logger mailLogger = null;
//    private File   anhang = null;
	
	public static void main(String[] args) throws ConfigException {
        if (Config.configure("conf", "ccwebui.ini")) {
        	
        	ErrorMail errorMail = new ErrorMail();
            errorMail.send(null, "Fehlertext 1", "Testdatei");
            System.exit(0);
        } else {
            System.exit(1);
        }

	}
	
	public ErrorMail() {
        mailLogger = Logger.getLogger("mail");
        mailLogger.setAdditivity(false);
	    logger = Logger.getLogger("ErrorMail");
	}
	
    public void send(Throwable throwable, String errorText, String fileName) {
        send(throwable, errorText, fileName, null, null);
    }
    
	public void send(Throwable throwable, String errorText, String fileName, String inFile, String errorDir) {
	    Utils.log("(ErrorMail send) fileName = " + fileName);
	    String logLine = "Datei: \"" + fileName + "\"";
		String text = Utils.LF + "Hallo CSF-Support," + Utils.LF +
                     "dies ist eine automatisch erzeugte Nachricht." + Utils.LF + 
                     Utils.LF +
                     "Beim Verarbeiten der Datei \"" + fileName + "\" ist ein schwerer Fehler aufgetreten!" + Utils.LF;
		
		if (throwable != null) {
			text = text + Utils.LF;
			text = text + "Exception: " + throwable.toString() + Utils.LF;
			logLine = logLine + "; Exception: " + throwable.toString();
			
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            throwable.printStackTrace(new PrintStream(bos));
            String stackTrace = bos.toString();
            text = text + Utils.LF;
            text = text + "Stack Trace: " + stackTrace.toString() + Utils.LF;
		}
		
		if (errorText != null && !errorText.trim().equals("")) {
			text = text + Utils.LF;
			text = text + "Fehlertext: " + errorText + Utils.LF;
            logLine = logLine + "; Fehlertext: " + errorText;
		}

        if (errorDir != null && !errorDir.trim().equals("")) {
            text = text + Utils.LF;
            if (inFile != null && !inFile.trim().equals("")) {
                text = text + "Die Eingangsdatei " + inFile + " wurde nach " + errorDir + " verschoben." + Utils.LF;
                logLine = logLine + "; Die Eingangsdatei " + inFile + " wurde nach " + errorDir + " verschoben.";
            } else {
                text = text + "Die Eingangsdatei wurde nach " + errorDir + " verschoben." + Utils.LF;
                logLine = logLine + "; Die Eingangsdatei wurde nach " + errorDir + " verschoben.";
            }
        }

		text = text + Utils.LF;
		text = text + "Bitte die Logs prüfen." + Utils.LF;
 
        try {
              sendToHost(text, logLine, fileName);
        } catch (MailException e) {
            logger.fatal("Fehler beim Mail-Versand:");
            e.printStackTrace();
        }
	}


    public void send(Throwable throwable, String errorText) {
        String logLine = "Fehler-Mail ";
        String text = Utils.LF + "Hallo CSF-Support," + Utils.LF +
                     "dies ist eine automatisch erzeugte Nachricht." + Utils.LF + 
                     Utils.LF +
                     "Beim der Verarbeitung ist ein schwerer Fehler aufgetreten!" + Utils.LF;
        
        if (throwable != null) {
            text = text + Utils.LF;
            text = text + "Exception: " + throwable.toString() + Utils.LF;
            logLine = logLine + "; Exception: " + throwable.toString();
        }
        
        if (errorText != null && !errorText.trim().equals("")) {
            text = text + Utils.LF;
            text = text + "Fehlertext: " + errorText + Utils.LF;
            logLine = logLine + "; Fehlertext: " + errorText;
        }

        text = text + Utils.LF;
        text = text + "Bitte die Logs pr�fen." + Utils.LF;
 
        try {
            sendToHost(text, logLine, null);
        } catch (MailException e) {
            logger.fatal("Fehler beim Mail-Versand:");
            e.printStackTrace();
        }
    }
 
    private void sendToHost(String text, String logLine, String fileName) throws MailException {
        Utils.log("(ErrorMail sendToHost) fileName = " + fileName);
        String host = Config.getMailHost();
        Utils.log("(ErrorMail sendToHost) host = " + host);
        try {
            doSendToHost(host, text, logLine, fileName);
      } catch (MailException e) {
          try {
              // Senden über alternativen Server versuchen
              sendToAlternateHost(text, logLine, fileName);
          } catch (MailException e1) {
              logger.fatal("Fehler beim Mail-Versand:");
              e.printStackTrace();
          }
      }
    }
    
    private void sendToAlternateHost(String text, String logLine, String fileName) throws MailException {
        Utils.log("(ErrorMail sendToAlternateHost) fileName = " + fileName);
        String host = Config.getMailAltHost();
        Utils.log("(ErrorMail sendToAlternateHost) alternate host = " + host);
        if (host != null) {
            doSendToHost(host, text, logLine, fileName);
        } else {
            Utils.log("(ErrorMail sendToAlternateHost) Kein alternativer Mailshost in Config eingetragen.");
        }
    }
    
    private void doSendToHost(String host, String text, String logLine, String fileName) throws MailException {
        Utils.log("(ErrorMail doSendToHost) host = " + host);
        Utils.log("(ErrorMail doSendToHost) fileName = " + fileName);
        
        MailMessageInformation mailMessageInformation = new MailMessageInformation();
        mailMessageInformation.setHost(host);
        mailMessageInformation.setFrom(Config.getMailFrom());
        mailMessageInformation.setFromName(Config.getMailFromName());
        mailMessageInformation.setRecipients(Config.getMailTo().split("[ \t]+"));
        mailMessageInformation.setRecipientsCc(Config.getMailCc().split("[ \t]+"));
        mailMessageInformation.setRecipientsBcc(Config.getMailBcc().split("[ \t]+"));
        mailMessageInformation.setSubject(Config.getMailSubject());
        mailMessageInformation.setBody(text);
//        mailMessageInformation.setOutFile(anhang);
        
        SendMail sendMail = new SendMail();
        sendMail.send(mailMessageInformation);
        
        if (fileName != null) {
            logger.info("Fehler-Mail zu Datei \"" + fileName + "\" erfolgreich gesendet.");
        } else {
            logger.info("Fehler-Mail erfolgreich gesendet.");
        }
        mailLogger.info(logLine);
    }
}
