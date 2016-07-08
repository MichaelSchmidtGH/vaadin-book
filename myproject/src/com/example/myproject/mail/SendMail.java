/*
 * Funktion    : ErrorMail.java
 * Titel       :
 * Erstellt    : 01.02.2006
 * Author      : CSF GmbH / kron
 * Beschreibung: 
 * Anmerkungen : 
 * Parameter   : 
 * Rückgabe    : keine
 * Aufruf      : 
 *
 * Änderungen:
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

 
import java.io.File;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.example.myproject.config.Config;
import com.example.myproject.utils.Utils;


/**
 * Modul        : ZabisMail<br>
 * Erstellt     : 29.10.2006<br>
 * Beschreibung : Modul zum Versenden von Mails mit und ohne Anhang.<br>
 * 
 * @author  schmidt
 * @version 1.0.00
 */
public class SendMail {

    /**
     * Bevor man Mails verschicken kann braucht man eine Mail-Session.
     */
	private Session 			session;
    
    /**
     * Die zu sendende Mail-Message.
     */
	private MimeMessage         message;
    
	private BodyPart 			msgBodyPart;
	
	private String				transport = "mail.smtp.host";
	
	public static void main(String[] args) throws Exception {
		String iniPath = "conf";

		if (args.length > 0) {
			iniPath = args[0];
		}

		Utils.log("iniPath = " + iniPath);
		if (Config.configure(iniPath, "ccwebui.ini")) {
            SendMail               sendMail              = new SendMail();
            MailMessageInformation mailMessageInformation = new MailMessageInformation();
            
            File anhang = new File("c:/temp/MailAnhang.txt");
            String host = "smtpmail.t-online.de";
            String body = "\nHallo,\n\ndies ist eine automatisch erzeugte Nachricht\n"
                        + "mit einem Fehlerreport im Anhang!\n\n";
            
            mailMessageInformation.setHost(host);
            mailMessageInformation.setFrom("mschmidt2@t-online.de");
            mailMessageInformation.setFromName("MIPS");
            mailMessageInformation.setRecipients(new String[] {"mschmidt2@t-online.de"});
            mailMessageInformation.setRecipientsCc(new String[] {});
            mailMessageInformation.setRecipientsBcc(new String[] {});
            mailMessageInformation.setSubject("test-subject");
            mailMessageInformation.setBody(body);
            mailMessageInformation.setOutFile(anhang);
            
            sendMail.send(mailMessageInformation);
        }
	}

    public void send(MailMessageInformation mailMessageInformation) throws MailException {
        String host             = mailMessageInformation.getHost();
        String from             = mailMessageInformation.getFrom();
        String fromName         = mailMessageInformation.getFromName();
        String[] recipients     = mailMessageInformation.getRecipients();
        String[] recipientsCc   = mailMessageInformation.getRecipientsCc();
        String[] recipientsBcc  = mailMessageInformation.getRecipientsBcc();
        String   subject        = mailMessageInformation.getSubject();
        String   body           = mailMessageInformation.getBody();
        File     outFile        = mailMessageInformation.getOutFile();
        String   dstFileName    = mailMessageInformation.getDstFileName();

        
        if (Utils.isStringEmpty(dstFileName)) {
            if (outFile != null) {
                dstFileName = outFile.getName();
            }
        }
        
        if (from == null || from.trim().equals(""))  {
            throw new MailException("Mail nicht gesendet da Absender nicht gesetzt!");
        }
        if (recipients == null || recipients[0].trim().equals(""))  {
            throw new MailException("Mail nicht gesendet da Empf�nger nicht gesetzt!");
        }
		if (subject == null || subject.trim().equals(""))  {
            throw new MailException("Mail nicht gesendet da Betreff nicht gesetzt!");
		}
	      if (body == null || body.trim().equals(""))  {
            throw new MailException("Mail nicht gesendet da kein Nachrichtentext gesetzt!");
		}
		if (host == null) {
            throw new MailException("Mail nicht gesendet da mail.host nicht gesetzt!");
		}
		  
	  	try {
	  		// Zeichensatz definieren
			System.setProperty("file.encoding", "ISO-8859-1");
			
			// Mail Transport und Server muss in compliance.ini definiert sein
			// z.B.: props.put( "mail.smtp.host", "mail01" );
            Properties props = new Properties();
			props.put(transport, host);

			// session = Session.getDefaultInstance(props);
            session = Session.getInstance(props);
		    if (Config.getMailDebug()) {
		    	session.setDebug(true);
		    }

		    message = new MimeMessage(session);
			
			message.setSentDate(new Date());
			
			message.setFrom(new InternetAddress(from, fromName));
            for (String recipient : recipients) {
                Utils.log("recipient = >" + recipient + "<");
                if (!recipient.trim().equals("")) {
                    message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient.trim()));
                }
            }

            for (String recipient : recipientsCc) {
                Utils.log("recipientCc = >" + recipient + "<");
                if (!recipient.trim().equals("")) {
                    message.addRecipient(Message.RecipientType.CC, new InternetAddress(recipient.trim()));
                }
            }
            for (String recipient : recipientsBcc) {
                Utils.log("recipientBcc = >" + recipient + "<");
                if (!recipient.trim().equals("")) {
                    message.addRecipient(Message.RecipientType.BCC, new InternetAddress(recipient.trim()));
                }
            }
		    
			message.setSubject(subject);
			
			// Mailbody(part1) und Attachment(part2) definieren		    
			Multipart multipart = new MimeMultipart();
						
			msgBodyPart = new MimeBodyPart();
			msgBodyPart.setText(body);
			multipart.addBodyPart(msgBodyPart);
			
		    if (outFile != null) {
		    	msgBodyPart = new MimeBodyPart();
		    	DataSource source = new FileDataSource(outFile);
		    	msgBodyPart.setDataHandler(new DataHandler(source));
		    	// 	Attachment Name wird hier "neu" definiert
                msgBodyPart.setFileName(dstFileName);
		    	// 	hinzufuegen des zweiten mailparts
		    	multipart.addBodyPart(msgBodyPart);
		    }		    
			
			message.setContent(multipart);
			Utils.log("(SendMail send) " + transport + " = " + session.getProperty(transport));
			Transport.send(message);
			
        } catch (MessagingException e) {
            e.printStackTrace();
            throw new MailException(e.getMessage());
	  	} catch (Exception e) {
			e.printStackTrace();
            throw new MailException(e.getMessage());
		} 
	  	
	  }
}
