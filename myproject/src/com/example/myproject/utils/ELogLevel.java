package com.example.myproject.utils;

/**
 * Log levels.
 * 
 * Created: 21.06.2015
 * 
 * @author schmidt
 * 
 */
public enum ELogLevel {
	/**
     * Log-Level NONE.
     * Wert = 0
     */
    NONE(Utils.LOG_NONE, "NONE "),
    
    /**
     * Log-Level SEVERE.
     * Wert = 1
     */
    FATAL(Utils.LOG_FATAL, "FATAL"),
    
    /**
     * Log-Level ERROR.
     * Wert = 2
     */
    ERROR(Utils.LOG_ERROR, "ERROR"),
    
    /**
     * Log-Level WARNING.
     * Wert = 3
     */
    WARNING(Utils.LOG_WARNING, "WARN "),
    
    /**
     * Log-Level INFO.
     * Wert = 4
     */
    INFO(Utils.LOG_INFO, "INFO "),
    
    /**
     * Log-Level DEBUG.
     * Wert = 5
     */
    DEBUG(Utils.LOG_DEBUG, "DEBUG"),
    
    /**
     * Log-Level ALL.
     * Wert = 6
     */
    ALL(Utils.LOG_ALL, "ALL  ");
    
    
    private int 	level	= 0;
    private String 	name 	= null;						// MUSS genau 5 Stellen lang sein!
    
    private ELogLevel(int level, String name) {
    	this.level = level;
    	this.name = name;
    }
    
    public int getLevel() {
    	return level;
    }
    
    public String getName() {
    	return name;
    }
    
    public static String getName(int level) {
    	String name = null;
    	switch(level) {
    		case Utils.LOG_NONE: 	name = "NONE "; break;
    		case Utils.LOG_FATAL: 	name = "FATAL"; break;
    		case Utils.LOG_ERROR: 	name = "ERROR"; break;
    		case Utils.LOG_WARNING: name = "WARN "; break;
    		case Utils.LOG_INFO: 	name = "INFO "; break;
    		case Utils.LOG_DEBUG:	name = "DEBUG"; break;
    		case Utils.LOG_ALL: 	name = "ALL  "; break;
    		default: 				name = null;
    	}
//    	System.out.println(">" + name + "<");
    	return name;
    }
}
