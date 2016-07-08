/*
 * Funktion    : Utils.java
 * Titel       : Utiliy-Methoden
 * Erstellt    : 27.11.2004
 * Author      : CSF GmbH / MS
 * Beschreibung: Allgemein n�tzliche Routinen
 * Anmerkungen :
 * Parameter   : n/a
 * R�ckgabe    : n/a
 * Aufruf      : n/a
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
package com.example.myproject.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.TimeZone;





/**
 * Modul		: Utils<br>
 * Erstellt		: 27.11.2004<br>
 * Beschreibung	: Eine Sammlung Utility Methoden.<br> 
 * 
 * @author 	MS
 * @version 1.0.00
 */
public final class Utils {
//    /**
//     * Timestamp format YYYY.MM.DD HH:MM:SS.
//     */
//    private static final DateFormat YYYY_MM_DD_HH_MI_SS = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
    
//    /**
//     * Timestampformat ohne Sekunden YYYYMMDDHHmm.
//     */
//    public static final DateFormat YYYYMMDDHHMM = new SimpleDateFormat("yyyyMMddHHmm");
    
//	/**
//	 * Datumsformat YYYYMMDD.
//	 */
//    public static final DateFormat YYYYMMDD = new SimpleDateFormat("yyyyMMdd");
    
//    /**
//     * Zeitformat HHMMSS.
//     */
//    public static final DateFormat HHMMSS   = new SimpleDateFormat("HHmmss");

//    /**
//     * Date format YYYYMMDDHHMMSS.
//     */
//    public static final DateFormat YYYYMMDDHHMISS = new SimpleDateFormat("yyyyMMddHHmmss");

//    /**
//     * Date format YYYYMMDDHHMISSMIL.
//     */
//    public static final DateFormat YYYYMMDDHHMISSMIL = new SimpleDateFormat("yyyyMMddHHmmssSSS");

    /**
	 * Das OS-spezifische Zeilenendezeichen.
	 */
    public static final String LF = System.getProperty("line.separator", "\n");

    /**
	 * Windows-spezifische Zeilenendezeichen.
	 */
    public static final String CRLF = System.getProperty("line.separator", "\r\n");

    /**
     * Log-Level NONE.
     * Wert = 0
     */
    public  static final int LOG_NONE    = 0;
    
    /**
     * Log-Level SEVERE.
     * Wert = 1
     */
    public  static final int LOG_FATAL  = 1;
    
    /**
     * Log-Level ERROR.
     * Wert = 2
     */
    public  static final int LOG_ERROR   = 2;
    
    /**
     * Log-Level WARNING.
     * Wert = 3
     */
    public  static final int LOG_WARNING = 3;
    
    /**
     * Log-LevelINFO.
     * Wert = 4
     */
    public  static final int LOG_INFO    = 4;
    
    /**
     * Log-Level DEBUG.
     * Wert = 5
     */
    public  static final int LOG_DEBUG   = 5;
    
    /**
     * Log-Level ALL.
     * Wert = 6
     */
    public  static final int LOG_ALL     = 6;
    
    /**
     * Default-Log-ID-Länge.
     * Wert = 8
     */
    private static final int DEFAULT_LOG_ID_LENGTH = 8;
    
    private static boolean debug      = true;			// Debug für Programmstart einschalten
    private static int     logLevel   = LOG_ALL;		// Loglevel auf ALL für Programmstart
    private static String  logID      = "        ";     // ID zum Kennzeichnen zusammengehöriger Zeilen

    private static DateFormat   df           = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss,SSS");
    private static NumberFormat numberFormat = NumberFormat.getNumberInstance(new Locale("de"));
    
    private static String       timestamp    = null; 
    
//    private static ExecutorService executor = Executors.newFixedThreadPool(1);
//    private static ExecutorService executor = Executors.newFixedThreadPool(2);
     
    private Utils() {
    }
    
    public static synchronized void log(String msg, boolean newline) {
        if (debug) {
        	processId();
		    timestamp = df.format(new Date(System.currentTimeMillis()));
		    if (newline) {
		    	System.out.println(timestamp + " " + logID + " [   ] DBG   " + msg);
		    } else {
		    	System.out.print(timestamp + " " + logID + " [   ] DBG   " + msg);
		    }
    		System.out.flush();
        }
    }
    
    public static synchronized void log(String msg) {
        if (debug) {
        	processId();
		    timestamp = df.format(new Date(System.currentTimeMillis()));
		    System.out.println(timestamp + " " + logID + " [   ] DBG   " + msg);
    		System.out.flush();
        }
    }
    
    public static synchronized void log(int level, String msg) {
        if (logLevel != LOG_NONE && level <= logLevel) {
        	processId();
		    timestamp = df.format(new Date(System.currentTimeMillis()));
		    String levelString = ELogLevel.getName(level);
//		    System.out.println(timestamp + " " + logID + " [   ] LOG   " + msg);
		    System.out.println(timestamp + " " + logID + " [   ] " + levelString + " " + msg);
    		System.out.flush();
        }
    }
    
    private static synchronized void processId() {
	    String tmpID = "" + Utils.nFormat(Thread.currentThread().getId(), DEFAULT_LOG_ID_LENGTH);
		if (logID != null) {
			if (!logID.equalsIgnoreCase(tmpID)) {
        		System.out.println("=======================");
        		System.out.flush();
    		}
    	}
		logID = tmpID;
    }
    
    public static void setDebug(boolean dbg) {
        debug = dbg;
    }

    public static void setLogLevel(int level) {
        log(LOG_INFO, "(Utils setLogLevel) Setting logLevel to " + level);
        logLevel = level;
    }

    /**
     * Liefert das aktuelle Datum als String im Format yyyy.MM.dd HH:mm:ss zurück.
     * 
     * @return Das aktuelle Datum als String im Format yyyy.MM.dd HH:mm:ss
     */
    public static synchronized String getCurrentTimestamp() {
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        return sdf.format(new Date(System.currentTimeMillis()));
    }

    /**
     * Liefert das aktuelle Datum als String im angeforderten Format zurück.
     * 
     * @param format Ein Format String laut {@link com.google.gwt.i18n.shared.DateTimeFormat}
     * @return Das aktuelle Datum als String im angeforderten Format.
     */
    public static synchronized String getCurrentTimestamp(String format) {
    	SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(System.currentTimeMillis()));
    }

    /**
	 * Wandelt ein Datum vom Typ <code>java.sql.Date</code> in einen String um.
	 * Ist der Datum-Parameter <code>null</code> wird ein leerer String zurück 
	 * geliefert.
	 * 
	 * @param date Das umzuwandelnde Datum
	 * @return Das Datum als String
	 */
	public static String dateToString(java.sql.Date date) {
	    if (date == null) {
	        return "";
	    } else {
		    return DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.GERMAN).format(date);
	    }
	}
    
    /**
     * Wandelt einen Zeitstempel vom Typ <code>java.sql.Timestamp</code> in
     * einen String im Format YYYYMMDDHHMMSS um. 
     * Ist der Zeitstempel-Parameter <code>null</code> wird ein leerer String zurück geliefert.
     *
     * @param timestamp Der umzuwandelnde Timestamp
     * @return Der Timestamp als String
     */
    public static synchronized String timestampToString(java.sql.Timestamp timestamp) {
        if (timestamp == null) {
            return "";
        } else {
        	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            return sdf.format(timestamp);
        }
    }

    /**
     * Wandelt einen Zeitstempel vom Typ <code>java.sql.Timestamp</code> in 
     * einen String um. Ist der Zeitstempel-Parameter <code>null</code> wird 
     * ein leerer String zurück geliefert.
     * 
     * @param timestamp Der umzuwandelnde Timestamp
     * @param format Format des zurückgelieferten Strings
     * @return Der <code>java.sql.Timestamp</code> als String.
     */
    public static String timestampToString(java.sql.Timestamp timestamp, String format) {
        if (timestamp == null) {
            return "";
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat(format); 
            return sdf.format(timestamp).trim();
        }
    }

    /**
     * Wandelt einen Zeitstempel vom Typ <code>java.sql.Timestamp</code> in
     * einen String im Format YYYYMMDDHHMMSSMIL (MIL = Millisekunden)um. 
     * Ist der Zeitstempel-Parameter <code>null</code> wird ein leerer String zurück geliefert.
     *
     * @param timestamp Der umzuwandelnde Timestamp
     * @return Der Timestamp als String
     */
    public static synchronized String timestampToStringMillis(java.sql.Timestamp timestamp) {
        if (timestamp == null) {
            return "";
        } else {
        	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
            return sdf.format(timestamp);
        }
    }


    /**
     * Wandelt einen String in einen Zeitstempel vom Typ 
     * <code>java.sql.Timestamp</code> um. Ist der String leer wird 
     * <code>null</code> zurück geliefert.
     * Das Format kann wie bei Open Declaration java.text.SimpleDateFormat angegeben werden.
     * Z.B.: yyyy.MM.dd HH:mm:ss
     * 
     * @param date Der umzuwandelnde Timestamp im String-Format
     * @param format Format des Datumstrings.
     * @return Der String als <code>java.sql.Timestamp</code>
     */
    public static java.sql.Timestamp stringToTimestamp(String date, String format) {
        if (date.trim().equals("")) {
            return null;
        } else {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(format); 
                return new java.sql.Timestamp(sdf.parse(date).getTime());
            } catch (java.text.ParseException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    /**
	 * Wandelt ein Zeitobjekt vom Typ <code>java.sql.Time</code> in einen String um.
	 * Ist der Zeit-Parameter <code>null</code> wird ein leerer String zurück 
	 * geliefert.
	 * 
	 * @param time Die umzuwandelnde Zeit
	 * @return Die Zeit als String
	 */
	public static String timeToString(java.sql.Time time) {
	    if (time == null) {
	        return "";
	    } else {
		    return DateFormat.getTimeInstance().format(time);
	    }
	}
    
	/**
	 * Wandelt einen Zeitstempel vom Typ <code>java.sql.Timestamp</code> in 
	 * einen String um. Ist der Zeitstempel-Parameter <code>null</code> wird 
	 * ein leerer String zurück geliefert.
	 * 
	 * @param date Der umzuwandelnde Timestamp
	 * @return Der Timestamp als String
	 */
	public static String dateTimeToString(java.sql.Timestamp date) {
	    if (date == null) {
	        return "";
	    } else {
		    return DateFormat.getDateTimeInstance().format(date);
	    }
	}

	/**
	 * Wandelt einen String in ein Datum vom Typ 
	 * <code>java.sql.Date</code> um. Ist der String leer wird 
	 * <code>null</code> zurück geliefert.
	 * 
	 * @param date Das umzuwandelnde Datum im String-Format
	 * @return Der String als <code>java.sql.Date</code>
	 */
	public static java.sql.Date stringToDate(String date) {
	    if (date.trim().equals("")) {
	        return null;
	    } else {
			try {
				DateFormat df = DateFormat.getDateInstance();
				return new java.sql.Date(df.parse(date).getTime());
			} catch (java.text.ParseException e) {
				e.printStackTrace();
				return null;
			}
	    }
	}
    
	/**
	 * Wandelt einen String in ein Datum vom Typ 
	 * <code>java.sql.Time</code> um. Ist der String leer wird 
	 * <code>null</code> zurück geliefert.
	 * 
	 * @param time Das umzuwandelnde Datum im String-Format
	 * @return Der String als <code>java.sql.Time</code>
	 */
	public static java.sql.Time stringToTime(String time) {
	    if (time.trim().equals("")) {
	        return null;
	    } else {
			try {
				DateFormat df = DateFormat.getTimeInstance();
				return new java.sql.Time(df.parse(time).getTime());
			} catch (java.text.ParseException e) {
				e.printStackTrace();
				return null;
			}
	    }
	}
    
	/**
	 * Wandelt einen String in einen Zeitstempel vom Typ 
	 * <code>java.sql.Timestamp</code> um. Ist der String leer wird 
	 * <code>null</code> zurück geliefert.
	 * 
	 * @param date Der umzuwandelnde Timestamp im String-Format
	 * @return Der String als <code>java.sql.Timestamp</code>
	 */
	public static java.sql.Timestamp stringToDateTime(String date) {
	    if (date.trim().equals("")) {
	        return null;
	    } else {
			try {
				DateFormat df = DateFormat.getDateTimeInstance();
				return new java.sql.Timestamp(df.parse(date).getTime());
			} catch (java.text.ParseException e) {
				e.printStackTrace();
				return null;
			}
	    }
	}

	/**
	 * Wandelt einen String in einen <code>int</code>. 
	 * Repräsentiert der String keine Zahl wird <code>0</code> 
	 * zurück geliefert.
	 * 
	 * @param string Der umzuwandelnde Wert im String-Format
	 * @return Der String als <code>int</code>
	 */
	public static int stringToInt(String string) {
        int value = 0;
        try {
			value = Integer.parseInt(string);
		} catch (NumberFormatException e) {
		    value = 0;
		}
		return value;
	}
	
	/**
	 * Wandelt einen String in einen <code>long</code>. 
	 * Repräsentiert der String keine Zahl wird <code>0</code> 
	 * zurück geliefert.
	 * 
	 * @param string Der umzuwandelnde Wert im String-Format
	 * @return Der String als <code>long</code>
	 */
	public static long stringToLong(String string) {
        long value = 0;
        try {
			value = Long.parseLong(string);
		} catch (NumberFormatException e) {
		    value = 0;
		}
		return value;
	}
	
	/**
	 * Wandelt einen String in einen <code>float</code>. 
	 * Repräsentiert der String keine Zahl wird <code>0.0</code> 
	 * zurück geliefert.
	 * 
	 * @param string Der umzuwandelnde Wert im String-Format
	 * @return Der String als <code>float</code>
	 */
	public static float stringToFloat(String string) {
        float value = 0.0F;
        try {
			value = Float.parseFloat(string);
		} catch (NumberFormatException e) {
		    value = 0.0F;
		}
		return value;
	}
	
    /**
     * Wandelt einen <code>int</code> in einen String. 
     * 
     * @param intParam Der umzuwandelnde Wert im int-Format
     * @return Der <code>int</code> als <code>String</code>
     */
    public static String intToString(int intParam) {
        return "" + intParam;
    }


    /**
     * Formatiert eine float oder double Wert auf n Nacchkommastellen.
     * 
     * @param number	Der zu formatierende Wert
     * @param precision Anzahl Nachkommastellen
     * @return String mit dem übergebenen Wert auf n Nachkommastellen formatiert.
     */
    public static String formatFloat(double number, int precision) {
		NumberFormat nf = NumberFormat.getNumberInstance();
		nf.setMinimumFractionDigits(precision);
		nf.setMaximumFractionDigits(precision);
		return nf.format(number);
    }
	

	/*****************************************************************************
	 * Methode     : nFormat
	 * Beschreibung: Konvertiert eine Zahl vom Typ <code>long</code> in einen String 
	 * 				 der übergebenen Länge mit führenden Nullen. 
	 * Autor 	   : Michael Schmidt
	 * Erstellt:   : 11.07.2005
	 * @param 		 number Zu formatiernde Zahl
	 * @param 		 tlen   Länge des Zielstrings (target length)
	 * @return 		 Mit führenden Nullen aufgefüllte Zahl als String
	 *****************************************************************************/
	public static String nFormat(long number, int tlen)	{
		StringBuffer sb   = new StringBuffer();
		String       num  = Long.toString(number);	// In String wandeln um Länge zu ermitteln
		int          nlen = num.length();			// Länge ermitteln
		int          dif  = tlen - nlen;			// Benötigte Anzahl Vornullen ermitteln

		if (dif > 0) {
			for (int i = 0; i < dif; i++) {
				sb.append("0"); 
			}
			sb.append(num);
		} else {
			sb.append(num);
		}
		return sb.toString();	
	}
	
    /*****************************************************************************
     * Methode     : nFormat
     * Beschreibung: Versorgt eine als String übergebene Zahl mit führenden Nullen. 
     * Autor       : Michael Schmidt
     * Erstellt:   : 11.07.2005
     * @param        number Zu formatiernde Zahl
     * @param        tlen   Länge des Zielstrings (target length)
     * @return       Mit führenden Nullen aufgefüllte Zahl als String
     *****************************************************************************/
    public static String nFormat(String number, int tlen) {
        StringBuffer sb   = new StringBuffer();
        String       num  = number.trim();
        int          nlen = num.length();           // Länge ermitteln
        int          dif  = tlen - nlen;            // Benötigte Anzahl Vornullen ermitteln

        if (dif > 0) {
            for (int i = 0; i < dif; i++) {
                sb.append("0"); 
            }
            sb.append(num);
        } else {
            sb.append(num);
        }
        return sb.toString();   
    }
    
    /**
     * Prüft ob ein Verzeichnis existiert und legt es gegebenenfalls neu an.
     * Legt auch alle fehlenden übergeordneten Verzeichnisse an.
     * 
     * @param directory Pfad des Verzeichnisses als <code>File</code>.
     * @param dirDescr Beschreibung des Verzeichnisses für Fehlerausgaben.
     * @return Das Verzeichnis als <code>File</code>.
     */
    public static File checkDir(File directory, String dirDescr) {
        return checkDir(directory.getPath(), dirDescr);
    }
    
	/**
	 * Prüft ob ein Verzeichnis existiert und legt es gegebenenfalls neu an.
	 * Legt auch alle fehlenden übergeordneten Verzeichnisse an.
	 * 
	 * @param dirName Pfad des Verzeichnisses
	 * @param dirDescr Beschreibung des Verzeichnisses für Fehlerausgaben.
	 * @return Das Verzeichnis als <code>File</code>.
	 */
    public static File checkDir(String dirName, String dirDescr) {
        File dir = null;
        
		dir = new File(dirName);
		Utils.log("(Utils checkDir) dir = " + dir.getAbsolutePath());
        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                Utils.log("(Utils checkDir) Das " + dirDescr + " " + dirName + 
                             " existiert nicht und konnte auch nicht erstellt werden.");
                dir = null;
            }
        } else if (!dir.isDirectory()) {
            Utils.log("(Utils checkDir) \"" + dirName + "\" ist kein Dateiverzeichnis.");
            dir = null;
        }

        return dir;
    }
    
	/**
	 * Verschiebt eine Datei in ein anderes Verzeichnis.
	 * Ist die Datei im Zielverzeichnis bereits vorhanden, wird solange eine 
	 * Laufnummer angehängt, bis ein freier Dateiname gefunden wurde.
	 *   
	 * @param targetDir Zielverzeichnis
	 * @param inFile    Zu verschiebenden Datei.
	 * @return File Die verschobene und evtl. umbenannte Datei.
	 */
    public static File moveToDir(File targetDir, File inFile) {
    	return moveToDir(targetDir, inFile, false);
    }

   	/**
	 * Verschiebt eine Datei in ein anderes Verzeichnis.
	 * Ist der Parameter "overwrite"<code>false</code> und die Datei 
	 * im Zielverzeichnis bereits vorhanden, wird solange eine neue 
	 * Laufnummer angehängt, bis ein freier Dateiname gefunden wurde.
	 * Ist der Parameter "overwrite"<code>true</code> wird eine vorhandene Datei überschrieben. 
	 *   
	 * @param targetDir Zielverzeichnis
	 * @param inFile    Zu verschiebenden Datei.
	 * @param overwrite Datei überschreiben (true) oder neue Datei mit Laufnummer schreiben (false)
	 * @return File Die verschobene und evtl. umbenannte Datei.
	 */
    public static File moveToDir(File targetDir, File inFile, boolean overwrite) {
    	checkDir(targetDir.getPath(), "Verzeichnis");
        int laufnr = 0;
        boolean ret = true;
        File targetFile = null;
        File targetFile2 = null;
        String fileName = null;
        String fileBody = null;
        String fileExt = null;
        fileName = inFile.getName();
        String[] tokens = fileName.split("\\.");
        fileBody = tokens[0];
        if (tokens.length > 1) {
            fileExt  = "." + tokens[1].toLowerCase();
        } else {
            fileExt  = "";
        }

//        targetFile = new File(targetDir, fileName);
//        laufnr = 0;
//        ret = targetFile.exists();
//        if (!overwrite) {
//        	while (ret) {
//        		laufnr++;
//        		targetFile = new File(targetDir, fileBody + "_" + laufnr + fileExt);
//        		ret = targetFile.exists();
//        	}
//        }

        targetFile = new File(targetDir, fileName);
        laufnr = 0;
        ret = targetFile.exists();
        //Nur verschieben wenn targetFile bereits existiert.
        if (!overwrite && ret) {
            while (ret) {
                laufnr++;
                targetFile2 = new File(targetDir, fileBody + "_" + laufnr + fileExt);
                ret = targetFile2.exists();
            }
            
            moveFile(targetFile2, targetFile, overwrite);
        }

        moveFile(targetFile, inFile, overwrite);
        
        return targetFile;
    }
    

   	/**
	 * Verschiebt eine Datei.
	 * Ist der Parameter "overwrite"<code>true</code> und die Datei 
	 * im Zielverzeichnis bereits vorhanden, wird sie überschrieben. 
	 * Ist der Parameter "overwrite"<code>false</code> wird eine Fehlermeldung ausgegeben. 
	 *   
	 * @param targetFile Zieldatei
	 * @param inFile     Zu verschiebenden Datei.
	 * @param overwrite  Datei überschreiben (true) oder neue Datei mit Laufnummer schreiben (false)
	 */
    public static void moveFile(File targetFile, File inFile, boolean overwrite) {
        if (targetFile.getParent() != null) {
            checkDir(targetFile.getParent(), "Verzeichnis");
        }
        boolean fileExists = true;
        
        try {
        	fileExists = targetFile.exists();
        	if (fileExists) {
                if (overwrite) {
            		targetFile.delete();
                } else {
                    Utils.log("(Utils moveToDir) Datei " + inFile 
                    		+ " konnte nicht nach " + targetFile + " verschoben werden," +
                    		  " da " + targetFile + " bereits existiert.");
                    return;
                }
        	}

            boolean ret = inFile.renameTo(targetFile);
            if (!ret) {
                Utils.log("(Utils moveFile) Datei " + inFile 
                		+ " konnte nicht nach " + targetFile + " verschoben werden.");
            } else {
                Utils.log("(Utils moveFile) Datei " +  inFile + " verschoben nach " + targetFile + ".");
            }
        } catch (SecurityException e) {
            Utils.log(Utils.LOG_FATAL, "(Utils moveFile) SecurityException beim Verschieben von Datei " 
                    + inFile + " nach " + targetFile + "\n" + e);
        }
    }
    
    /**
     * Kopierte eine Datei in ein anderes Verzeichnis.
     * Ist die Datei im Zielverzeichnis bereits vorhanden, wird solange eine 
     * Laufnummer angehängt, bis ein freier Dateiname gefunden wurde.
     *   
     * @param targetDir Zielverzeichnis
     * @param inFile    Zu kopierende Datei.
     * @param newName   Neuer Name der zu kopierenden Datei
     * @return File Die kopierte und evtl. umbenannte Datei.
     * @throws Exception Wenn beim Speichern etwas schief ging.
     */
    public static File copyToDir(File targetDir, File inFile, String newName) throws Exception {
    	checkDir(targetDir.getPath(), "Verzeichnis");
        int laufnr = 0;
        boolean ret = true;
        File targetFile = null;
        String fileName = null;
        String fileBody = null;
        String fileExt = null;
        if (newName == null) {
            fileName = inFile.getName();
        } else {
            fileName = newName;
        }
        
        try {
            String[] tokens = fileName.split("\\.");
            fileBody = tokens[0];
            if (tokens.length > 1) {
                fileExt  = "." + tokens[1].toLowerCase();
            } else {
                fileExt  = "";
            }

            targetFile = new File(targetDir, fileName);
            laufnr = 0;
            ret = targetFile.exists();
            while (ret) {
//                Utils.log("(Utils copyToDir) " + targetFile + " existiert bereits.");
                laufnr++;
                targetFile = new File(targetDir, fileBody + "_" + laufnr + fileExt);
//                Utils.log("(Utils copyToDir) Zielname geändert nach " + targetFile + ".");
                ret = targetFile.exists();
            }

//            Utils.log("(Utils copyToDir) Kopieren von " + inFile + " nach " + targetFile + ".");
            
            try {
                BufferedReader in  = new BufferedReader(new FileReader(inFile));
                BufferedWriter out = new BufferedWriter(new FileWriter(targetFile));
                char[] ca = new char[(int) inFile.length()];
                while (in.read(ca) != -1) {
                    out.write(ca);
                }
                in.close();
                out.close();
                ret = true;
            } catch (IOException e) {
                e.printStackTrace();
                throw new Exception(e.toString(), e);
            }
            
            if (!ret) {
                Utils.log("(Utils copyToDir) Datei " + inFile + " konnte nicht nach " + targetFile + " kopiert werden.");
            } else {
                Utils.log("(Utils copyToDir) Datei " +  inFile + " kopiert nach " + targetFile + ".");
            }
        } catch (SecurityException e) {
            String msg = "(Utils copyToDir) SecurityException beim Kopieren von Datei " + inFile + " nach " + targetFile + "\n" + e;
            Utils.log(msg);
            throw new Exception(msg, e);
        }
        return targetFile;
    }
    
    /**
     * Kopierte eine Datei in ein anderes Verzeichnis.
     * Ist die Datei im Zielverzeichnis bereits vorhanden, wird sie überschrieben wenn 
     * das Flag "overwrite" gesetzt ist. 
     * Ist das Flag "overwrite" nicht gesetzt, wird eine Laufnummer angehängt.
     *   
     * @param targetDir Zielverzeichnis
     * @param inFile    Zu kopierende Datei.
     * @param overwrite Soll eine vorhandene Datei überschrieben werden?
     * @param rename    Datei unter temporärem Namen (mit Punkt am Beginn) kopieren und anschliessend umbenennen
     * @return File Die kopierte Datei.
     */
    public static boolean copyToDir(File targetDir, File inFile, boolean overwrite, boolean rename) {
        checkDir(targetDir.getPath(), "Verzeichnis");
        boolean ret = true;
        File targetFile = null;
        File tempFile   = null;
        try {
            targetFile = new File(targetDir, inFile.getName());
            tempFile   = new File(targetDir, "." + inFile.getName());
            try {
                BufferedReader in  = new BufferedReader(new FileReader(inFile));
                BufferedWriter out = null;
                if (rename) {
                    out = new BufferedWriter(new FileWriter(tempFile));
                } else {
                    out = new BufferedWriter(new FileWriter(targetFile));
                }
                char[] ca = new char[(int) inFile.length()];
                while (in.read(ca) != -1) {
                    out.write(ca);
                }
                in.close();
                out.close();
                ret = true;
            } catch (IOException e) {
                e.printStackTrace();
                ret = false;
            }
            
            if (!ret) {
                Utils.log("(Utils copyToDir) Datei " + inFile + " konnte nicht nach " + targetFile + " kopiert werden.");
            } else {
                if (rename) {
                    moveFile(targetFile, tempFile, overwrite);
                }
                Utils.log("(Utils copyToDir) Datei " +  inFile + " kopiert nach " + targetFile + ".");
            }
        } catch (SecurityException e) {
            Utils.log(Utils.LOG_FATAL, "(Utils copyToDir) SecurityException beim Kopieren von Datei " 
                    + inFile + " nach " + targetFile + "\n" + e);
            ret = false;
        }
        return ret;
    }
    
    
	/**
	 * Returns the logID.
	 * @return Returns the logID.
	 */
	public static String getLogID() {
		return logID;
	}

	/**
	 * Sets the logID.
	 * @param logID The logID to set.
	 */
	public static void setLogID(String logID) {
		Utils.logID = logID;
	}
    
	/**
	 * Prüft anhand der Dateiendung, ob die Datei eine XML-Datei ist.
	 *  
	 * @param fileName Zu prüfender Dateiname.
	 * @return true, wenn die Dateiendung XML ist, sonst false.
	 */
	public static boolean isXMLFile(String fileName) {
	    String[] tokens = fileName.split("\\.");
	    if (tokens.length > 1) {
	        return tokens[1].equalsIgnoreCase("xml") ? true : false;
	    } else {
	        return false;
	    }
	}
	
	/**
	 * Prüft ob ein String Null oder leer ist.
	 * 
	 * @param str Zu prüfender String
	 * @return true wenn der String Null oder leer ist sonst false
	 */
    public static boolean isStringEmpty(String str) {
        return str == null || str.trim().equals("");
    }
    
    
    /**
     * Prüft ob ein String nur Ziffern enthält.
     *  
     * @param numberString Zu prüfender String.
     * @return true, wenn der String nur aus Ziffern besteht.
     */
    public static boolean isNumeric(String numberString) {
        try {
            new BigInteger(numberString);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    /**
     * Gibt den Namen einer als File übergebenen Datei ohne Pfad und Endung zurück.
     * 
     * @param file Ein File-Objekt.
     * @return Der Dateiname aus dem File-Objekt ohne Pfad und Extension.
     */
    public static String getFileBasename(File file) {
        String filename = file.getName();
        String basename = filename;
        int lastDot = filename.lastIndexOf('.');
        if (lastDot > 0) {
            basename = filename.substring(0, lastDot);
        }
        return basename;
    }
    
    /**
     * Gibt den Namen einer als File übergebenen Datei incl. Pfad aber ohne Endung zurück.
     * 
     * @param file Ein File-Objekt.
     * @return Der Dateiname aus dem File-Objekt incl. Pfad aber ohne Extension.
     */
    public static String getFilePathBasename(File file) {
        String filename = file.getPath();
        String basename = filename;
        int lastDot = filename.lastIndexOf('.');
        if (lastDot > 0) {
            basename = filename.substring(0, lastDot);
        }
        return basename;
    }
    

    /**
     * Prüft ob ein String eine hexadezimale Zahl enthält.
     *  
     * @param numberString Zu prüfender String.
     * @return true, wenn der String nur aus hexadezimalen Zeichen besteht.
     */
    public static boolean isHexString(String numberString) {
        try {
            Integer.parseInt(numberString, 16);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    public static String logElapsedTime(long start, String text) {
    	String result = null;
		Time zeit = new Time(System.currentTimeMillis() - start);
		// Zeitzone auf UTC setzen, sonst kommt bei der Zeitausgabe die Sommerzeit dazu
		// In zwei Schritten, da der Jenkins-Build auf der ccdb-dev sonst Fehler meldet
//		TimeZone tz = TimeZone.getTimeZone("UTC"); 
//		TimeZone.setDefault(tz); 
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		result = String.valueOf(zeit);
		Utils.log(Utils.LOG_INFO, text + result);
		// Zeitzone wieder zurücksetzen
		TimeZone.setDefault(null);

		return result;
    }
    
    public static String getElapsedTime(long start) {
    	String result = null;
		Time zeit = new Time(System.currentTimeMillis() - start);
		// Zeitzone auf UTC setzen, sonst kommt bei der Zeitausgabe die Sommerzeit dazu
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		result = String.valueOf(zeit);
		// Zeitzone wieder zurücksetzen
		TimeZone.setDefault(null);

		return result;
    }
    
    
    /**
     * Setzt den Zeitstempel des letzten Zugriffs einer Datei auf die aktuelle Zeit plus einem Offset in Millisekunden.
     * 
     * @param file Datei deren Zeitstempel geändert werden soll.
     * @param offset Anzahl Millisekunden die auf den Zeitstempel addiert werden soll. Kann negativ sein um die Zeit zu vermindern.
     */
    public static void touch(File file, long offset) {
        file.setLastModified(System.currentTimeMillis() + offset);
    }
    
    public static void showMemory() {
        MemoryUsage mu  = getMemoryUsage();
        long committed  = mu.getCommitted();
        long init       = mu.getInit();
        long used       = mu.getUsed();
        long max        = mu.getMax();
        
        Utils.log("(Utils showMemory) Memory: init      = " + numberFormat.format(init));
        Utils.log("(Utils showMemory) Memory: committed = " + numberFormat.format(committed));
        Utils.log("(Utils showMemory) Memory: used      = " + numberFormat.format(used));
        Utils.log("(Utils showMemory) Memory: max       = " + numberFormat.format(max));
    }

    public static String getUsedMemory() {
        MemoryUsage mu  = getMemoryUsage();
        long used       = mu.getUsed();
        String usedString = numberFormat.format(used);
        return usedString;
    }
    
    private static MemoryUsage getMemoryUsage() {
        MemoryMXBean m  = ManagementFactory.getMemoryMXBean();
        MemoryUsage mu  = m.getHeapMemoryUsage();
        return mu;
    }
    
    public static void showRuntimeMemory() {
        Utils.log("(Utils showRuntimeMemory) FreeMemory : " + numberFormat.format(Runtime.getRuntime().freeMemory()));
//        Utils.log("(Utils showRuntimeMemory) MaxMemory  : " + Runtime.getRuntime().maxMemory());
    }

    /**
     * Ruft ein Shell-Script mit Parametern auf und gibt die Ausgabe auf STDOUT und STDERR als Html-String zurück.
     * @param name		Name des Skripts
     * @param params	ArrayList mit Parametern
     * @return			STDERR und STOUT Ausgaben des Skripts als Html-String (mit \<br\> stat \\nl getrennte Zeilen).
     * @throws Exception Wenn ein Fehler auftrat.
     */
    public static String callScript(String name, ArrayList<String> params) throws Exception {
    	return callScript(name, params, 0, 0);
    }

    /**
     * Ruft ein Shell-Script mit Parametern auf und gibt die Ausgabe auf STDOUT und STDERR als Html-String zurück.
     * @param name		Name des Skripts
     * @param params	ArrayList mit Parametern
     * @param initialTimeout	Timeout zum Lesen von STDOUT and STDERR in milliseconds. Bei 0 wird Mindestwert von 1000 ms genommen.
     * @param nextTimeouts		Timeout zum Lesen von STDOUT and STDERR in milliseconds. 
     * @return			STDERR und STOUT Ausgaben des Skripts als Html-String (mit \<br\> stat \\nl getrennte Zeilen).
     * @throws Exception Wenn ein Fehler auftrat.
     */
    public static String callScript(String name, ArrayList<String> params, long initialTimeout, long nextTimeouts) throws Exception {
    	Utils.log("(Utils callScript) name    		 = " + name);
    	Utils.log("(Utils callScript) params  		 = " + params);
    	Utils.log("(Utils callScript) initialTimeout = " + initialTimeout);
    	Utils.log("(Utils callScript) nextTimeouts   = " + nextTimeouts);
    	HashMap<String, String> map = systemCall(name, params, null, initialTimeout, nextTimeouts);
//    	Utils.log("(Utils callScript) Nach systemCall");
    	String err = map.get("ERR");
    	String out = map.get("OUT");
    	String errHtml = "";
    	String outHtml = "";
    	String result = "Nothing.";
    	
//    	Utils.log("(Utils callScript) err = " + err);
    	if (!Utils.isStringEmpty(err)) {
    		StringBuffer sb = new StringBuffer("ERR:<br>");
    		StringReader reader = new StringReader(err);
    		BufferedReader br = new BufferedReader(reader);
    		String line = br.readLine();
    		while (line != null) {
    			sb.append(line).append("<br>");
    			line = br.readLine();
    		}
    		errHtml = sb.toString() + "<br>";
    	}
    	
//    	Utils.log("(Utils callScript) out = " + out);
    	if (!Utils.isStringEmpty(out)) {
    		StringBuffer sb = new StringBuffer("OUT:<br>");
    		StringReader reader = new StringReader(out);
    		BufferedReader br = new BufferedReader(reader);
    		String line = br.readLine();
    		while (line != null) {
    			sb.append(line).append("<br>");
    			line = br.readLine();
    		}
    		outHtml = sb.toString() + "<br>";
    	}
    	
    	// Ist mindestens eines der beiden gefüllt wird die Kombination als Ergebnis zurückgegeben.
    	// Ansonsten der Initialwert von result ("Nothing."). 
    	if (!(Utils.isStringEmpty(errHtml) && Utils.isStringEmpty(outHtml))) {
    		result = errHtml + outHtml;
    	}
    	
//    	Utils.log("(Utils callScript) returning result: " + result);
        return result;
    }

    
//    /**
//     * Ruft ein Programm per System-Call auf und gibt STDERR und STDOUT in einer HashMap zurück.
//     * Optional kann eine Datei angegeben werden in welche die STDERR- und STDOUT-Ausgaben geschrieben werden.
//     * 
//     * @param program	Das aufzurufende Programm
//     * @param params	Die dem Programm zu übergebenden Paramter in einer ArrayList
//     * @param outFile	Optional eine Datei in die STDERR un STDOUT geschrieben werden.
//     * @param timeout	Timeout zum Lesen von STDOUT and STDERR in milliseconds. 0 = Endlos
//     * @return			Eine HashMap mit den Ausgaben auf STDERR und STDOUT.
//     * 					Schlüssel für STDERR ist "ERR", Schlüsssel für STDOUT ist "OUT".
//     * @throws Exception Wenn ein Fehler auftrat.
//     */
//    public static HashMap<String, String> systemCall(String program, ArrayList<String> params, String outFile, long timeout) throws Exception {
//    	String[] cmdarray = new String[params.size() + 1];
//    	cmdarray[0] = program;
//  		Utils.log("(Utils systemCall) cmdarray[" + 0  + "] = " + cmdarray[0]);
//    	for (int i = 0; i < params.size(); i++) {
//    		cmdarray[i + 1] = params.get(i);
//    		Utils.log("(Utils systemCall) cmdarray[" + (i + 1)  + "] = " + cmdarray[i + 1]);
//    	}
//		FileOutputStream fos = null;
//		String errorText = null;
//		String outputText = null;
//    	try {
//        	Runtime runtime = Runtime.getRuntime();
//			Process process = runtime.exec(cmdarray);
//			Utils.log("(Utils systemCall) nach runtime.exec");
//	    	if (outFile != null) {
//	    		fos = new FileOutputStream(outFile);
//	    	}
//	    	Utils.log("(Utils systemCall) reading STDOUT");
//			outputText = readStream(process.getInputStream(), fos, timeout);
//	    	Utils.log("(Utils systemCall) reading STDERR");
//			errorText  = readStream(process.getErrorStream(), fos, timeout);
//			
//			Utils.log("(Utils systemCall) waiting for end of process...");
//			int exitVal = process.waitFor();
//			Utils.log("(Utils systemCall) exitValue = " + exitVal);
//	    	if (outFile != null) {
//				fos.flush();
//				fos.close();
//	    	}
//		} catch (Throwable t) {
//			t.printStackTrace();
//			throw new Exception(t);
//		}
//		HashMap<String, String> result = new HashMap<String, String>();
//		result.put("ERR", errorText);
//		result.put("OUT", outputText);
//		
//		return result;
//    }
    
    /**
     * Ruft ein Programm per System-Call auf und gibt STDERR und STDOUT in einer HashMap zurück.
     * Optional kann eine Datei angegeben werden in welche die STDERR- und STDOUT-Ausgaben geschrieben werden.
     * 
     * @param program			Das aufzurufende Programm
     * @param params			Die dem Programm zu übergebenden Paramter in einer ArrayList
     * @param outFile			Optional eine Datei in die STDERR un STDOUT geschrieben werden.
     * @param initialTimeout	Timeout zum Lesen von STDOUT and STDERR in milliseconds. Bei 0 wird Mindestwert von 1000 ms genommen.
     * @param nextTimeouts		Timeout zum Lesen von STDOUT and STDERR in milliseconds. 
     * @return					Eine HashMap mit den Ausgaben auf STDERR und STDOUT.
     * 							Schlüssel für STDERR ist "ERR", Schlüsssel für STDOUT ist "OUT".
     * @throws Exception Wenn ein Fehler auftrat.
     */
    public static HashMap<String, String> systemCall(String program, ArrayList<String> params, String outFile, long initialTimeout, long nextTimeouts) 
    																																	throws Exception {
    	String[] cmdarray = new String[params.size() + 1];
    	cmdarray[0] = program;
  		Utils.log("(Utils systemCall) cmdarray[" + 0  + "] = " + cmdarray[0]);
    	for (int i = 0; i < params.size(); i++) {
    		cmdarray[i + 1] = params.get(i);
    		Utils.log("(Utils systemCall) cmdarray[" + (i + 1)  + "] = " + cmdarray[i + 1]);
    	}
    	
    	Utils.log("(Utils systemCall) cmdarray = " + Arrays.asList(cmdarray));
    	
    	HashMap<String, String> result = doSystemCall(cmdarray, outFile, initialTimeout, nextTimeouts);
    	return result;
    }

    private static HashMap<String, String> doSystemCall(String[] cmdarray, String outFile, long initialTimeout, long nextTimeouts) throws Exception {
		FileOutputStream fos = null;
		String errorText  = null;
		String outputText = null;
    	try {
    		ProcessBuilder pb = new ProcessBuilder();
    		pb.redirectErrorStream(true);
    		pb.command(cmdarray);
    		final Process process = pb.start();
			Utils.log("(Utils systemCall) nach ProcessBuilder.start");
			
	    	if (outFile != null) {
	    		fos = new FileOutputStream(outFile);
	    	}

	    	
			// Mit timeout == 0 wird nichts oder nur zufällig gelesen
	    	// Zumindest der erste zu lesende Stream muss daher einen ausreichenden Timeout haben 
			if (initialTimeout == 0) {
				initialTimeout = 1000;
			}
	    	
//	    	Utils.log("(Utils systemCall) reading STDOUT/STDERR");
//			outputText = readStream(process.getInputStream(), fos, timeout);
//	    	Utils.log("(Utils systemCall) reading STDOUT");
//			outputText = readStream(process.getInputStream(), fos, timeout);
//	    	Utils.log("(Utils systemCall) reading STDERR");
//			errorText  = readStream(process.getErrorStream(), fos, timeout);
			
			Utils.log("(Utils systemCall) waiting for end of process...");
			int exitVal = process.waitFor();
		    Utils.log("(Utils systemCall) exitValue = " + exitVal);

		    Utils.log("(Utils systemCall) reading STDOUT/STDERR");
			outputText = readStream(process.getInputStream(), fos, initialTimeout, nextTimeouts);
		    
	    	if (fos != null) {
				fos.flush();
	    	}
		} catch (Throwable t) {
			t.printStackTrace();
			throw new Exception(t);
		} finally {
			if (fos != null) {
				fos.close();
			}
		}
		HashMap<String, String> result = new HashMap<String, String>();
		if (outputText == null) { outputText = "\n"; }
		if (errorText == null) { errorText = "\n"; }
		Utils.log("(Utils doSystemCall) outputText = " + outputText);
		Utils.log("(Utils doSystemCall) errorText  = " + errorText);
		result.put("OUT", outputText);
		result.put("ERR", errorText);
		Utils.log("(Utils doSystemCall) result.get(OUT) = " + result.get("OUT"));
		Utils.log("(Utils doSystemCall) result.get(ERR) = " + result.get("ERR"));
		
		return result;

    }
    
    private static String readStream(InputStream stream, OutputStream redirect, long initialTimeout, long nextTimeouts) {
    	Utils.log("(Utils readStream) START");
    	StringBuffer buffer = new StringBuffer();
    	
		PrintWriter pw = null;
		if (redirect != null) {
			pw = new PrintWriter(redirect);
		}
		
		
		String output = null;
		output = readAvailable(stream, initialTimeout, nextTimeouts);
	    if (pw != null) {
	        pw.println(output);
			pw.flush();
	    }

	    // Mindestens ein CR am Zeilenende
	    if (output == null) {
	    	Utils.log("(Utils readStream) output von readAvailable ist NULL");
	    	output = "\n";
    		buffer.append(Utils.LF);
	    } else {
		    Utils.log("(Utils readStream) output.length = " + output.length());
	    	buffer.append(output);
		    if (output.length() > 0) {
		    	char last = output.charAt(output.length() - 1);
		    	if (last != '\n' && last != '\r') {
		    		buffer.append(Utils.LF);
		    	} 
		    }
	    }
	    
	    // Stream muss vom aufgerufenen Prozess geschlossen werden!!!
	    // Wenn der Close vor dem Ende des Prozesses kommt bricht der Prozess ab!
	    // Wenn der Close nach dem Ende des Prozesses kommt ist der Stream sowieso schon zu.
	    // Das Schliessen aus dem aufrufenden Programm ist also Unsinn.
	    // closeInputStream(stream);
	    
		if (pw != null) {
			pw.close();
		}

		Utils.log("(Utils readStream) returning buffer.toString() -> " + buffer.toString(), false);
		return buffer.toString();
    }
    

    private static String readAvailable(InputStream stream, long initialTimeout, long nextTimeouts) {
    	Utils.log("(Utils readAvailable) START");

    	String result = null;
    	
    	byte[] b1 = new byte[1];
    	byte[] b = new byte[4096];
    	int index = 0;
    	try {
    		int available = stream.available();
    		Utils.log("(Utils readAvailable) 1 available = " + available);
    		int waited = 0;
    		int step = 100;
    		Utils.log("(Utils readAvailable) waited      = " + waited);
    		Utils.log("(Utils readAvailable) timeout     = " + initialTimeout);
    		while (available == 0 && waited < initialTimeout) {
    			Utils.log("(Utils readAvailable) sleeping " + step + " ms");
    			Thread.sleep(step);
    			waited += step;
    			available = stream.available();
    			Utils.log("(Utils readAvailable) 2 available = " + available);
    		}
   			Utils.log("(Utils readAvailable) 3 available = " + available);
    		Utils.log("(Utils readAvailable) waited      = " + waited);
			while (available > 0) {
				int read = stream.read(b1);
				if (read > 0) {
					b[index++] = b1[0];
					available = stream.available();
					Utils.log("(Utils readAvailable) 4 available = " + available);
					waited = 0;
//					timeout = 100;
		    		while (available == 0 && waited < nextTimeouts) {
		    			Utils.log("(Utils readAvailable) sleeping " + step + " ms");
		    			Thread.sleep(step);
		    			waited += step;
		    			available = stream.available();
		    			Utils.log("(Utils readAvailable) 5 available = " + available);
		    		}
				} else {
					break;
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

    	Utils.log("(Utils readAvailable) index = " + index);
    	if (index > 0) {
    		result = new String(b).substring(0, index);
    	}
    	Utils.log("(Utils readAvailable) result = " + result);
    	return result;
    }

//   private static String readStream(InputStream stream, OutputStream redirect, long timeout) {
//    	StringBuffer buffer = new StringBuffer();
//    	
////    	long timeout  = 1000;		// Timeout for input in milliseconds
//    	
//		PrintWriter pw = null;
//		if (redirect != null) {
//			pw = new PrintWriter(redirect);
//		}
//		
////		BufferedInputStream bis = new BufferedInputStream(stream);
//		InputStreamReader isr = new InputStreamReader(stream);
//        BufferedReader    br  = new BufferedReader(isr);
//		String line = null;
//		try {
//			if (timeout == 0) {
//				line = br.readLine();
//			} else {
////				line = readLine(timeout, br);
//				line = readAvailable(stream);
//				Utils.log("(Utils readStream) line1 = " + line);
//			    if (pw != null) {
//			        pw.println(line);
//					pw.flush();
//			    }
//			    buffer.append(line);    
//			    buffer.append(Utils.LF);    
//			}
////			while (line != null) {
////				if (timeout == 0) {
////					line = br.readLine();
////				} else {
//////					line = readLine(timeout, br);
////					line = readAvailable(stream);
////					Utils.log("(Utils readStream) line2 = " + line);
////				}
//////				line = br.readLine();
////				if (line != null) {
////				    if (pw != null) {
////				        pw.println(line);
////						pw.flush();
////				    }
////				    buffer.append(line);    
////				    buffer.append(Utils.LF);
////				}
////			}
//		} catch (IOException e) {
//			e.printStackTrace();
////		} catch (InterruptedException e) {
////			e.printStackTrace();
////		} catch (ExecutionException e) {
////			e.printStackTrace();
////		} catch (TimeoutException e) {
////			Utils.log("(Utils readLine) " + e);
//		} finally {
////			closeInputStream(stream);
//			closeReader(isr);
//			closeReader(br);
//			if (pw != null) {
//				pw.close();
//			}
//		}
//		Utils.log("(Utils readStream) returning buffer.toString() -> " + buffer.toString());
//		return buffer.toString();
//    }

//    private static String readLine(final long timeout, final BufferedReader br) throws InterruptedException, ExecutionException, TimeoutException {
//    	String line = null;
//    	
//    	Callable<String> readTask = new Callable<String>() {
//	        @Override
//	        public String call() throws Exception {
//	        	boolean isReady = br.ready();
//	    		Utils.log("(Utils readLine) Stream not ready");
//	    		try {
//	    			int step = 100;
//	    			for (int i = 0; i < (timeout / step); i++) {
//	    				Utils.log("(Utils readLine) sleep " + step + " milliseconds");
//	    				Thread.sleep(step);
//	    				if (br.ready()) {
//	    					Utils.log("(Utils readLine) ready");
//	    					break;
//	    				}
//	    				
//	    			}
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
////	        	if (! isReady) {
////	        		Utils.log("(Utils.readLine(...).new Callable() {...} call) not ready 1");
////	        		Thread.sleep(timeout / 2);
////	        	}
//	        	isReady = br.ready();
//	        	if (! isReady) {
//	        		Utils.log("(Utils.readLine(...).new Callable() {...} call) not ready 2");
//	        		return null;
//	        	}
//	        	String line = br.readLine();
//	        	Utils.log("(Utils.readLine(...).new Callable() {...} call) line = " + line);
//	            return line;
//	        }
//    	};
//    	
//	    Future<String> future = executor.submit(readTask);
//		try {
//			line = future.get(timeout, TimeUnit.MILLISECONDS);
//		} catch (TimeoutException e) {
//			e.printStackTrace();
//			if (future.isDone()) {
//				Utils.log("(Utils readLine) future is done 1");
//			} else {
//				Utils.log("(Utils readLine) Trying to cancel future readTask");
//				boolean canceled = future.cancel(true);
//				if (canceled) {
//					Utils.log("(Utils readLine) future canceled");
//				} else {
//					Utils.log("(Utils readLine) future could not be canceled");
//				}
//				if (future.isDone()) {
//					Utils.log("(Utils readLine) future is done 2");
//				}
//			}
//		}
//
//	    return line;
//    }
    
//    private static String readLine(long timeout, BufferedReader br) throws IOException {
//    	String line = null;
//    	
//    	boolean isReady = br.ready();
//    	if (! isReady) {
//    		Utils.log("(Utils readLine) Stream not ready");
//    		try {
//    			int step = 100;
//    			for (int i = 0; i < (timeout / step); i++) {
//    				Utils.log("(Utils readLine) sleep " + step + " milliseconds");
//    				Thread.sleep(step);
//    				if (br.ready()) {
//    					Utils.log("(Utils readLine) ready");
//    					break;
//    				}
//    				
//    			}
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//    	}
//    	isReady = br.ready();
//    	if (! isReady) {
//    		Utils.log("(Utils readLine) not ready after " + timeout + " milliseconds. Returning null.");
//    		return null;
//    	}
//    	
//		Utils.log("(Utils readLine) line = br.readLine() ...");
//    	line = br.readLine();
//
//    	Utils.log("(Utils.readLine) line = " + line);
//        return line;
//    }

    
//    private static void closeReader(Reader r) {
//    	try {
//    		if (r != null) {
//    			Utils.log("(Utils closeReader) vor close");
//    			r.close();
//    			Utils.log("(Utils closeReader) nach close");
//    		}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//    }
    
}
