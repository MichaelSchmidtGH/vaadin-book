/*
 * Created on 30.07.2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.example.myproject.db;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.example.myproject.config.Config;
import com.example.myproject.exceptions.ApplicationException;
import com.example.myproject.utils.Utils;

/**
 * Modul		:	util
 * Erstellt		:	30.07.2004
 * Beschreibung	:	Datenbankbezogene Utility-Klassen.
 * 	
 * @author 	schmidt
 * @version 1.0.00
 */
public final class DbUtil {
    /**
     * Die Liste der Datensätze ist aufsteigend sortiert und soll
     * bei getNext/-Prev() usw. vorwärts durchlaufen werden.
     */
    public static final int FORWARD = +1;
    
    /**
     * Die Liste der Datensätze ist absteigend sortiert und soll
     * bei getNext/-Prev() usw. rückwärts durchlaufen werden.
     */
    public static final int BACKWARD = -1;

    /**
	 * Datentyp eines DB-Feldes ist ein String-Typ.
	 * Z. B. char, varchar usw.
	 * Wert = 0
	 */
    public static final int TYPE_STRING = 0;
	
    /**
	 * Datentyp eines DB-Feldes ist ein numerischer Typ.
	 * Z. B. integer, money, decima usw.
	 * Wert = 1
	 */
    public static final int TYPE_NUM    = 1;

    /**
	 * Datentyp eines DB-Feldes ist ein Typ der ein LIKE in der WHERE-Klausel erfordert..
	 * Z. B. blob, clob, text
	 * Wert = 2
	 */
    public static final int TYPE_LIKE   = 2;
    
	/**
	 * Datentyp eines DB-Feldes ist ein Date-Typ.
	 * Z. B. date.
	 * Wert = 3
	 */
    public static final int TYPE_DATE = 3;
    
	/**
	 * Datentyp eines DB-Feldes ist ein Time-Typ.
	 * Z. B. time.
	 * Wert = 4
	 */
    public static final int TYPE_TIME = 4;
    
	/**
	 * Datentyp eines DB-Feldes ist ein Timestamp-Typ.
	 * Z. B. datetime, timestamp.
	 * Wert = 5
	 */
    public static final int TYPE_TIMESTAMP = 5;
    
    /**
	 * Datentyp eines DB-Feldes ist ein Typ der ein IN(...) in der WHERE-Klausel erfordert..
	 * Z. B. eine Liste von Strings
	 * Wert = 6
	 */
    public static final int TYPE_LIST   = 6;
    

    
    /**
     * Standard-Konstruktor verbergen.
     * Die Klasse soll nie instanziiert werden.
     */
    private DbUtil() {
    }
    
    /**
     * Fügt einen Ausdruck an die WHERE-Klausel des übergebenen SELECT-Strings an.  
     * @param sb Stringbuffer, der das bisherige SELECT-Statement enthält.
     * @param fieldName DB-Feld-Name des hinzuzufügenden Ausdrucks. 
     * @param fieldValue Vergleichswert 
     * @param fieldType Daten-Typ des DB-Feldes.
     * @throws ApplicationException Wenn ein Fehler auftrat.
     */
    public static void appendSelect(final StringBuffer sb,
                                    final String fieldName, 
                                    final String fieldValue, 
                                    final int fieldType) throws ApplicationException {
        
        if (fieldValue == null) {
    		return;
    	}
    	
        if (fieldValue.length() == 0 || fieldValue.trim().equals("")) {
            return;
        }
        
        String value  = fieldValue.trim();
        value = trimUnderscores(value);
        if (Utils.isStringEmpty(value)) {
        	return;
        }
        
        
        String op 		= "=";
        int    consumed = 0;
        int    len   	= value.length();
        
//        Utils.log("(DbUtil appendSelect) value = " + value);
//        String[] parts = value.split("[ \t]");
//        Utils.log("(DbUtil appendSelect) parts[0] = " + parts[0]);
//        if (parts[0].equalsIgnoreCase("between")) {
//        	Utils.log("(DbUtil appendSelect) between detected");
//        	op ="between";
//        	consumed = op.length() + 1;
//        }
        
        char first  = value.charAt(0);
        char second = ' ';
        if (len > 1) {
            second = value.charAt(1);
        }
        char last  = value.charAt(len - 1);
        int underscoreIndex = value.indexOf('_');
        if (first == '*' || first == '%' || last == '*' || last == '%' || underscoreIndex > -1) {
            appendSelectLike(sb, fieldName, value, first, last);
        } else {
//            if (first == '=' || first == '<' || first == '>') {
            if (first == '=' || first == '<' || first == '>' || first == '!') {
                consumed++;
                op = "" + first;
            }
            if (second == '=') {
                consumed++;
                op += "=";
            }
            if (second == '>') {
                consumed++;
                op += ">";
            }
            String val = fieldValue.substring(consumed, len);
            op = " " + op + " ";
            switch (fieldType) {
                case TYPE_STRING: appendSelectString(sb, fieldName, op, val);
                    break;
                case TYPE_NUM: appendSelectInt(sb, fieldName, op, val);
                    break;
                case TYPE_LIKE: appendSelectLike(sb, fieldName, val);
                    break;
                case TYPE_DATE: appendSelectDate(sb, fieldName, op, val);
                	break;
                case TYPE_TIME: appendSelectTime(sb, fieldName, op, val);
            		break;
                case TYPE_TIMESTAMP: appendSelectTimestamp(sb, fieldName, op, val);
            		break;
                case TYPE_LIST: appendSelectList(sb, fieldName, val);
                    break;
                default:
            }
        }
    }

    /**
     * Replaces all underscore placeholders at the end of the string by one %-placeholder.
     * 
     * @param source Source string.
     * @return Source string with all trailing underscores replaced by one '%' char. 
     */
    private static String trimUnderscores(String source) {
    	char[] array  = source.toCharArray();
    	int    len    = array.length;
    	char[] result = new char[array.length];
    	char   last   = array[len - 1];
    	
    	    	
    	// Eliminate underscores at end of the string
    	int i = len - 1;
    	while (array[i] == '_') {
    		i--;
	    	// Only underscores in string then return empty String
	    	if (i < 0) {
	    		return "";
	    	}
    	}
    	
    	// Copy rest into result
    	while (i >= 0) {
    		result[i] = array[i];
    		i--;
    	}
    	
    	String tempString = new String(result).trim();
    	// If there was at least one underscore at the end:
    	// Add % to end
    	if (last == '_') {
        	tempString = tempString + "%";
    	}

    	return tempString;
    	
    	
    }
    
    /**
     * Fügt einen Ausdruck an die WHERE-Klausel des übergebenen SELECT-Strings an.  
     * 
     * @param sb Stringbuffer, der das bisherige SELECT-Statement enthält.
     * @param fieldName DB-Feld-Name des hinzuzufügenden Ausdrucks.
     * @param operand Operand mit dem fieldName und fieldValue verglichen werden sollen. 
     * @param fieldValue Vergleichswert 
     * @param fieldType Daten-Typ des DB-Feldes.
     * @throws ApplicationException Wenn ein Fehler auftrat.
     */
    public static void appendSelect(final StringBuffer sb,
                                    final String fieldName, 
                                    final String operand, 
                                    final String fieldValue, 
                                    final int fieldType) throws ApplicationException {
        
        switch (fieldType) {
            case TYPE_STRING: appendSelectString(sb, fieldName, operand, fieldValue);
                break;
            case TYPE_NUM: appendSelectInt(sb, fieldName, operand, fieldValue);
                break;
            case TYPE_LIKE: appendSelectLike(sb, fieldName, fieldValue);
                break;
            case TYPE_DATE: appendSelectDate(sb, fieldName, operand, fieldValue);
            	break;
            case TYPE_TIME: appendSelectTime(sb, fieldName, operand, fieldValue);
        		break;
            case TYPE_TIMESTAMP: appendSelectTimestamp(sb, fieldName, operand, fieldValue);
        		break;
            default:
        }
    }

    /**
     * Fügt einen <code>String</code>-Ausdruck an die WHERE-Klausel des 
     * übergebenen SELECT-Strings an.  
     * @param sb Stringbuffer, der das bisherige SELECT-Statement enthält.
     * @param fieldName DB-Feld-Name des hinzuzufügenden Ausdrucks. 
     * @param op Operand der WHERE-Klausel zwischen <code>fieldName</code> und
     * <code>fieldValue</code>
     * @param fieldValue Vergleichswert 
     */
    public static void appendSelectString(StringBuffer sb, 
                                          String fieldName, 
                                          String op, 
                                          String fieldValue) {
    	if (fieldValue.equals("")) {
    		fieldValue = " ";
    	}
        sb.append(" AND " + fieldName + op + "'" + fieldValue + "'");
    }
    
    /**
     * Fügt einen <code>int</code>-Ausdruck an die WHERE-Klausel des übergebenen SELECT-Strings an.
     *   
     * @param sb Stringbuffer, der das bisherige SELECT-Statement enthält.
     * @param fieldName DB-Feld-Name des hinzuzufügenden Ausdrucks. 
     * @param op Operand der WHERE-Klausel zwischen <code>fieldName</code> und <code>fieldValue</code>
     * @param fieldValue Vergleichswert 
     */
    public static void appendSelectInt(final StringBuffer sb, 
                                        final String fieldName,  
                                        final String op, 
                                        final String fieldValue) {
        if (fieldValue.length() > 0) {
            sb.append(" AND " + fieldName + op + fieldValue);
        } else {
            sb.append(" AND " + fieldName + op + "0");
        }
    }
    
    /**
     * Fügt einen <code>LIKE</code>-Ausdruck an die WHERE-Klausel des 
     * übergebenen SELECT-Strings an.  
     * @param sb Stringbuffer, der das bisherige SELECT-Statement enth�lt.
     * @param fieldName DB-Feld-Name des hinzuzuf�genden Ausdrucks. 
     * @param fieldValue Vergleichswert 
     */
    public static void appendSelectLike(final StringBuffer sb, 
                                         final String fieldName, 
                                         final String fieldValue) {
        sb.append(" AND " + fieldName + " LIKE '%" + fieldValue + "%'");
    }
    
    /**
     * Fügt einen <code>IN</code>-Ausdruck an die WHERE-Klausel des 
     * übergebenen SELECT-Strings an.  
     * @param sb Stringbuffer, der das bisherige SELECT-Statement enthält.
     * @param fieldName DB-Feld-Name des hinzuzufügenden Ausdrucks. 
     * @param fieldValue Liste von Strings 
     */
    public static void appendSelectList(final StringBuffer sb, final String fieldName, final String fieldValue) {
        sb.append(" AND " + fieldName + " IN(");
    	String[] tokens = fieldValue.split("[ ,]");
    	for (String value : tokens) {
			sb.append("'").append(value).append("', ");
		}
    	// Letztes Komma und nachfolgendes Leerzeichen entfernen
    	sb.replace(sb.length() - 2, sb.length(), "");
        sb.append(")");
        
        Utils.log("(DbUtil appendSelectList) " + sb.toString());
    }
    
    /**
     * F�gt einen <code>Date</code>-Ausdruck an die WHERE-Klausel des 
     * �bergebenen SELECT-Strings an.  
     * @param sb Stringbuffer, der das bisherige SELECT-Statement enth�lt.
     * @param fieldName DB-Feld-Name des hinzuzuf�genden Ausdrucks. 
     * @param op Operand der WHERE-Klausel zwischen <code>fieldName</code> und
     * <code>fieldValue</code>
     * @param fieldValue Vergleichswert 
     * @throws ApplicationException Wenn ein Fehler auftrat.
     */
    public static void appendSelectDate(final StringBuffer sb, 
                                           final String fieldName, 
                                           final String op, 
                                           final String fieldValue) throws ApplicationException {
    	Locale locale = getLocale();
    	SimpleDateFormat df = (SimpleDateFormat) DateFormat.getDateInstance(DateFormat.SHORT, locale);
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	String datum = null;
		Date dat = null;
    	try {
			dat = df.parse(fieldValue);
		} catch (ParseException e) {
			try {
				dat = sdf.parse(fieldValue);
			} catch (ParseException e1) {
//				String dateFormat  = df.toPattern();
//				calendar.set(Calendar.DAY_OF_MONTH, 31);
//				calendar.set(Calendar.MONTH, Calendar.DECEMBER);
//				String dateExample = df.format(calendar.getTime());
//				Object[] format = new Object[] {dateFormat, dateExample};
//				ApplicationError error = new ApplicationError(e1, "DbUtil", "ERR0002", null, format);
				throw new ApplicationException(e1);
			}
		}
		Utils.log("(DbUtil appendSelectDate) dat = " + dat.toString());
		datum = "{d '" + sdf.format(dat) + "'}";
        sb.append(" AND " + fieldName + op + datum);
    }
    
    /**
     * F�gt einen <code>Time</code>-Ausdruck an die WHERE-Klausel des 
     * �bergebenen SELECT-Strings an.  
     * @param sb Stringbuffer, der das bisherige SELECT-Statement enth�lt.
     * @param fieldName DB-Feld-Name des hinzuzuf�genden Ausdrucks. 
     * @param op Operand der WHERE-Klausel zwischen <code>fieldName</code> und
     * <code>fieldValue</code>
     * @param fieldValue Vergleichswert 
     * @throws ApplicationException Wenn ein Fehler auftrat.
     */
    public static void appendSelectTime(final StringBuffer sb, 
                                           final String fieldName, 
                                           final String op, 
                                           final String fieldValue) throws ApplicationException {
//    	DateFormat df = DateFormat.getTimeInstance();
    	Locale locale = getLocale();
    	SimpleDateFormat df = (SimpleDateFormat) DateFormat.getTimeInstance(DateFormat.MEDIUM, locale);
    	SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
    	String datum = null;
		Date dat = null;
    	try {
			dat = df.parse(fieldValue);
		} catch (ParseException e) {
			try {
				dat = sdf.parse(fieldValue);
			} catch (ParseException e1) {
//				String timeFormat  = df.toPattern();
//				
//				calendar.set(Calendar.HOUR_OF_DAY, 10);
//				calendar.set(Calendar.MINUTE, 59);
//				calendar.set(Calendar.SECOND, 00);
//				String timeExample = df.format(calendar.getTime());
//
//				Object[] format = new Object[] {timeFormat, timeExample};
//				ApplicationError error = new ApplicationError(e1, "DbUtil", "ERR0003", null, format);
				throw new ApplicationException(e1);
			}
		}
		Utils.log("(DbUtil appendSelectTime) dat = " + dat.toString());
		datum = "{t '" + sdf.format(dat) + "'}";
        sb.append(" AND " + fieldName + op + datum);
    }
    
    /**
     * F�gt einen <code>Timestamp</code>-Ausdruck an die WHERE-Klausel des 
     * �bergebenen SELECT-Strings an.  
     * @param sb Stringbuffer, der das bisherige SELECT-Statement enth�lt.
     * @param fieldName DB-Feld-Name des hinzuzuf�genden Ausdrucks. 
     * @param op Operand der WHERE-Klausel zwischen <code>fieldName</code> und
     * <code>fieldValue</code>
     * @param fieldValue Vergleichswert 
     * @throws ApplicationException Wenn ein Fehler auftrat.
     */
    public static void appendSelectTimestamp(final StringBuffer sb, 
                                           final String fieldName, 
                                           final String op, 
                                           final String fieldValue) throws ApplicationException {
    	Utils.log("(DbUtil appendSelectTimestamp) fieldValue = " + fieldValue);
    	
    	
    	if (Utils.isStringEmpty(fieldValue)) {
    		String operand = op.trim();
    		if (operand.equals("=")) {
    			sb.append(" AND " + fieldName + " is null");
                return;
    		} else if (operand.contains(">")) {
    			sb.append(" AND " + fieldName + " is not null");
                return;
    		} else {
    			throw new ApplicationException("Ungültiger Operand (" + operand + ") bei Suche nach einem Timestamp.");
    		}
    	}
    	
//    	DateFormat df = DateFormat.getDateTimeInstance();
    	Locale locale = getLocale();
        SimpleDateFormat df  = (SimpleDateFormat) DateFormat.getDateTimeInstance(DateFormat.SHORT, 
        		                                                                 DateFormat.MEDIUM, locale);
    	SimpleDateFormat df2 = (SimpleDateFormat) DateFormat.getDateInstance(DateFormat.SHORT, locale);
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	SimpleDateFormat sdf2 = new SimpleDateFormat("dd.MM HH:mm");
    	String datum = null;
		Date dat = null;
    	try {
			dat = df.parse(fieldValue);
			Utils.log("(DbUtil appendSelectTimestamp) df matched");
		} catch (ParseException e) {
			try {
				dat = df2.parse(fieldValue);
				Utils.log("(DbUtil appendSelectTimestamp) df2 matched");
			} catch (ParseException e1) {
				try {
					dat = sdf.parse(fieldValue);
					Utils.log("(DbUtil appendSelectTimestamp) sdf matched");
				} catch (ParseException e2) {
					try {
						dat = sdf2.parse(fieldValue);
						Utils.log("(DbUtil appendSelectTimestamp) sdf2 matched");
					} catch (ParseException e3) {
						throw new ApplicationException(e3);
					}
				}
			}
		}
		Utils.log("(DbUtil appendSelectTimestamp) dat = " + dat.toString());
		datum = "{ts '" + sdf.format(dat) + "'}";
        sb.append(" AND " + fieldName + op + datum);
    }
    
    /**
     * F�gt einen <code>LIKE</code>-Ausdruck an die WHERE-Klausel des 
     * �bergebenen SELECT-Strings an.
     * Ersetzt "*"-Wildcards durch "%"-Wildcards.  
     * @param sb Stringbuffer, der das bisherige SELECT-Statement enth�lt.
     * @param fieldName DB-Feld-Name des hinzuzuf�genden Ausdrucks. 
     * @param fieldValue Vergleichswert 
     * @param first Wildcard am Anfang des Ausdrucks. 
     * Wenn <code>first</code> dem Zeichen <code>*</code> entspricht, 
     * wird <code>first</code> durch <code>%</code> ersetzt.
     * @param last Wildcard am Ende des Ausdrucks. 
     * Wenn <code>last</code> dem Zeichen <code>*</code> entspricht, 
     * wird <code>last</code> durch <code>%</code> ersetzt.
     */
    public static void appendSelectLike(final StringBuffer sb, 
                                        final String fieldName, 
                                        final String fieldValue, 
                                        final char first, final char last) {
    	if (Utils.isStringEmpty(fieldValue)) {
    		return;
    	}
        String op1 = " LIKE '";
        String op2 = "'";
        int pos1 = 0;
        int pos2 = fieldValue.length();
        if (first == '*') {
            op1 += "%";
            pos1 = 1;
        }
        if (last == '*') {
            op2 = "%'";
            pos2 -= 1;
        }
        Utils.log("(DbUtil appendSelectLike) fieldValue = " + fieldValue);
        Utils.log("(DbUtil appendSelectLike) pos1       = " + pos1);
        Utils.log("(DbUtil appendSelectLike) pos2       = " + pos2);
        Utils.log("(DbUtil appendSelectLike) op1        = " + op1);
        Utils.log("(DbUtil appendSelectLike) op2        = " + op2);
        if (pos2 > 0) {
        	sb.append(" AND " + fieldName + op1 + fieldValue.substring(pos1, pos2) + op2);
        } else {
        	sb.append(" AND " + fieldName + op1 + op2);
        }
    }
    
    private static Locale getLocale() {
    	String applicationLocale = Config.getApplicationLocale();
        Locale locale;
        try {
            locale = new Locale(applicationLocale.substring(0, 2), applicationLocale.substring(3, 5));
        } catch (NullPointerException e) {
            locale = new Locale("de_DE");
        }
    	return locale;
    }
    
//	----------------------------------------------------------------------------
//	Zusammenbau einer SET-Klausel     
//	----------------------------------------------------------------------------

    /**
     * F�gt einen Ausdruck an die SET-Klausel des �bergebenen UPDATE-Strings an.  
     * @param sb Stringbuffer, der das bisherige UPDATE-Statement enth�lt.
     * @param fieldName DB-Feld-Name des hinzuzuf�genden Ausdrucks. 
     * @param fieldValue Vergleichswert 
     * @param fieldType Daten-Typ des DB-Feldes.
     * @throws ApplicationException Wenn ein Fehler auftrat.
     */
    public static void appendSet(final StringBuffer sb,
            final String fieldName, 
            final String fieldValue, 
            final int fieldType) throws ApplicationException {

    	if (fieldValue == null || fieldValue.trim().length() == 0) {
    		return;
    	}
    	
    	if (sb.length() > 0) {
    		sb.append(", ");
    	}
    	
    	String val = fieldValue.trim();
    	switch (fieldType) {
    		case TYPE_STRING: appendSetString(sb, fieldName, val);
    		break;
    		case TYPE_NUM: appendSetInt(sb, fieldName, val);
    		break;
    		case TYPE_DATE: appendSetDate(sb, fieldName, val);
    		break;
    		case TYPE_TIME: appendSetTime(sb, fieldName, val);
    		break;
    		case TYPE_TIMESTAMP: appendSetTimestamp(sb, fieldName, val);
    		break;
    		default:
    	}
    }

    /**
     * F�gt einen <code>String</code>-Ausdruck an die SET-Klausel des 
     * �bergebenen UPDATE-Strings an.  
     * @param sb Stringbuffer, der das bisherige SELECT-Statement enth�lt.
     * @param fieldName DB-Feld-Name des hinzuzuf�genden Ausdrucks. 
     * @param fieldValue neuer Wert des Feldes. 
     */
    public static void appendSetString(final StringBuffer sb, 
            final String fieldName, 
            final String fieldValue) {
    	
    	sb.append(fieldName);
    	sb.append("='");
    	sb.append(fieldValue.length() == 0 ? " " : fieldValue);
    	sb.append("'");
    }

    /**
     * F�gt einen <code>int</code>-Ausdruck an die SET-Klausel des 
     * �bergebenen UPDATE-Strings an.  
     * @param sb Stringbuffer, der das bisherige SELECT-Statement enth�lt.
     * @param fieldName DB-Feld-Name des hinzuzuf�genden Ausdrucks. 
     * @param fieldValue neuer Wert des Feldes. 
     */    
    public static void appendSetInt(final StringBuffer sb, 
            final String fieldName,  
            final String fieldValue) {
    	
    	sb.append(fieldName);
    	sb.append("=");
    	sb.append(fieldValue.length() == 0 ? "0" : fieldValue);
    }
    
    /**
     * F�gt einen <code>Date</code>-Ausdruck an die SET-Klausel des 
     * �bergebenen UPDATE-Strings an.  
     * @param sb Stringbuffer, der das bisherige SELECT-Statement enth�lt.
     * @param fieldName DB-Feld-Name des hinzuzuf�genden Ausdrucks. 
     * @param fieldValue neuer Wert des Feldes. 
     * @throws ApplicationException Wenn ein Fehler auftrat.
     */
    public static void appendSetDate(final StringBuffer sb, 
            final String fieldName, 
            final String fieldValue) throws ApplicationException {

    	Locale locale = getLocale();
    	SimpleDateFormat df = (SimpleDateFormat) DateFormat.getDateInstance(DateFormat.SHORT, locale);
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	Date dat = null;
    	try {
    		dat = df.parse(fieldValue);
    	} catch (ParseException e) {
    		try {
    			dat = sdf.parse(fieldValue);
    		} catch (ParseException e1) {
//    			String dateFormat  = df.toPattern();
//    			
//    			calendar.set(Calendar.DAY_OF_MONTH, 31);
//    			calendar.set(Calendar.MONTH, Calendar.DECEMBER);
//    			String dateExample = df.format(calendar.getTime());
//    			
//    			Object[] format = new Object[] {dateFormat, dateExample};
//    			ApplicationError error = new ApplicationError(e1, "DbUtil", "ERR0005", null, format);
    			throw new ApplicationException(e1);
    		}
    	}
    	sb.append(fieldName);
    	sb.append("={d '");
    	sb.append(sdf.format(dat));
    	sb.append("'}");
    }

    /**
     * F�gt einen <code>Time</code>-Ausdruck an die SET-Klausel des 
     * �bergebenen UPDATE-Strings an.  
     * @param sb Stringbuffer, der das bisherige SELECT-Statement enth�lt.
     * @param fieldName DB-Feld-Name des hinzuzuf�genden Ausdrucks. 
     * @param fieldValue neuer Wert des Feldes. 
     * @throws ApplicationException Wenn ein Fehler auftrat.
     */
    public static void appendSetTime(final StringBuffer sb, 
            final String fieldName, 
            final String fieldValue) throws ApplicationException {

    	Locale locale = getLocale();
    	SimpleDateFormat df = (SimpleDateFormat) DateFormat.getTimeInstance(DateFormat.MEDIUM, locale);
    	SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
    	Date dat = null;
    	try {
    		dat = df.parse(fieldValue);
    	} catch (ParseException e) {
    		try {
    			dat = sdf.parse(fieldValue);
    		} catch (ParseException e1) {
//    			String timeFormat  = df.toPattern();
//    			
//    			calendar.set(Calendar.HOUR_OF_DAY, 10);
//    			calendar.set(Calendar.MINUTE, 59);
//    			calendar.set(Calendar.SECOND, 00);
//    			String timeExample = df.format(calendar.getTime());
//    			
//    			Object[] format = new Object[] {timeFormat, timeExample};
//    			ApplicationError error = new ApplicationError(e1, "DbUtil", "ERR0006", null, format);
    			throw new ApplicationException(e1);
    		}
    	}
    	sb.append(fieldName);
    	sb.append("={t '");
    	sb.append(sdf.format(dat));
    	sb.append("'}");
    }
    
    /**
     * F�gt einen <code>Timestamp</code>-Ausdruck an die SET-Klausel des 
     * �bergebenen UPDATE-Strings an.  
     * @param sb Stringbuffer, der das bisherige SELECT-Statement enth�lt.
     * @param fieldName DB-Feld-Name des hinzuzuf�genden Ausdrucks. 
     * @param fieldValue neuer Wert des Feldes. 
     * @throws ApplicationException Wenn ein Fehler auftrat.
     */    
    public static void appendSetTimestamp(final StringBuffer sb, 
            final String fieldName, 
            final String fieldValue) throws ApplicationException {
    	
    	Locale locale = getLocale();
    	SimpleDateFormat df  = (SimpleDateFormat) DateFormat.getDateTimeInstance(DateFormat.SHORT, 
                                                  DateFormat.MEDIUM, locale);
    	SimpleDateFormat df2 = (SimpleDateFormat) DateFormat.getDateInstance(DateFormat.SHORT, locale);
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	Date dat = null;
    	try {
    		dat = df.parse(fieldValue);
    	} catch (ParseException e) {
    		try {
    			dat = df2.parse(fieldValue);
    		} catch (ParseException e1) {
    			try {
    				dat = sdf.parse(fieldValue);
    			} catch (ParseException e2) {
//    				calendar.set(Calendar.DAY_OF_MONTH, 31);
//    				calendar.set(Calendar.MONTH, Calendar.DECEMBER);
//    				calendar.set(Calendar.HOUR_OF_DAY, 10);
//    				calendar.set(Calendar.MINUTE, 59);
//    				calendar.set(Calendar.SECOND, 00);
//    				String dateFormat  = df2.toPattern();
//    				String dateExample = df2.format(calendar.getTime());
//    				String datetimeFormat  = df.toPattern();
//    				String datetimeExample = df.format(calendar.getTime());
//
//    				Object[] format = new Object[] {dateFormat, dateExample, 
//    						datetimeFormat, datetimeExample};
//    				ApplicationError error = new ApplicationError(e1, "DbUtil", "ERR0007", null, format);
    				throw new ApplicationException(e2);
    			}
    		}
    	}
    	sb.append(fieldName);
    	sb.append("={ts '");
    	sb.append(sdf.format(dat));
    	sb.append("'}");
    }


    /**
     * Oracle erwartet für Char-Felder mindestens ein Leerzeichen.
     * Leere Strings werden als NULL interpretiert und führen zu
     * einer SQLException.
     *
     * @param  value Zu prüfender String
     * @return String bestehend aus genau einem SPACE, wenn der String
     * null oder leer ist sonst der ursprüngliche Wert.
     */
    public static String getEmptyString(String value) {
        if (value == null || value.equals("")) {
            return " ";
        } else {
            return value;
        }
    }


    /**
     * Informix-SE erwartet für Time-Felder die Sekunden ab Mitternacht als int.
     * 
     * @param  time Zeit als <code>Time</code>-Objekt.
     * @return Zeit in Sekunden ab Mitternacht
     */
    @SuppressWarnings("deprecation")
    public static int getIfxTime(Time time) {
        if (time == null) {
            return 0;
        }
        
        int hours = time.getHours();
        int minutes = time.getMinutes();
        int seconds = time.getSeconds();
        
        int intTime = (hours * 3600) + (minutes * 60) + seconds;
        return intTime;
    }

    /**
     * Informix-SE erwartet für Time-Felder die Sekunden ab Mitternacht als int.
     * 
     * @param  stringTime Zeit als <code>Time</code>-Objekt.
     * @return Zeit in Sekunden ab Mitternacht als String.
     */
    public static String getIfxStringTime(String stringTime) {
        if (stringTime == null) {
            return "";
        }
        
        stringTime = stringTime.trim();
        Time time = Utils.stringToTime(stringTime);
        
        int intTime = getIfxTime(time);
        if (intTime == 0) {
            return "";
        }
        
        return "" + intTime;
    }


}
