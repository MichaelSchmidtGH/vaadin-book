package com.example.myproject.mail;

import java.io.File;

/**�
 * Modul        : MailMessageInformation.<br>
 * Erstellt     : 03.02.2011<br>
 * Beschreibung : Datentransferobjekt f�r Mail-�bertragungsparameter.<br>
 * 
 * @author schmidt
 * @version 1.0.00 
 */
public class MailMessageInformation {
    private String   host          = null;      // Local mail host   
    private String   from          = null;      // Mail address of the sender
    private String   fromName      = null;      // Name of the sender       
    private String[] recipients    = null;      // Array of mail recipients      
    private String[] recipientsCc  = null;      // Array of carbon copy recipents
    private String[] recipientsBcc = null;      // Array of blind carbon copy recipients
    private String   subject       = null;      // Mail subject      
    private String   body          = null;      // Mail body         
    private File     outFile       = null;      // Local file to transfer to target host
    private String   dstFileName   = null;      // Name of the transfered file on the target host  
    

    
    public String getHost() {
        return host;
    }
    public void setHost(String host) {
        this.host = host;
    }
    public String getDstFileName() {
        return dstFileName;
    }
    public void setDstFileName(String dstFileName) {
        this.dstFileName = dstFileName;
    }
    public String getSubject() {
        return subject;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }
    public String getBody() {
        return body;
    }
    public void setBody(String body) {
        this.body = body;
    }
    public String getFrom() {
        return from;
    }
    public void setFrom(String from) {
        this.from = from;
    }
    public String getFromName() {
        return fromName;
    }
    public void setFromName(String fromName) {
        this.fromName = fromName;
    }
    public String[] getRecipients() {
        return recipients;
    }
    public void setRecipients(String[] recipients) {
        this.recipients = recipients;
    }
    public String[] getRecipientsCc() {
        return recipientsCc;
    }
    public void setRecipientsCc(String[] recipientsCc) {
        this.recipientsCc = recipientsCc;
    }
    public String[] getRecipientsBcc() {
        return recipientsBcc;
    }
    public void setRecipientsBcc(String[] recipientsBcc) {
        this.recipientsBcc = recipientsBcc;
    }
    public File getOutFile() {
        return outFile;
    }
    public void setOutFile(File outFile) {
        this.outFile = outFile;
    }

}
