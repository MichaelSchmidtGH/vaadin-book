package com.example.myproject.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import com.example.myproject.exceptions.ConfigException;

/**
 * Die Klasse Prop verwaltet Properties.
 * 
 * @author schmidt
 * @version 1.0.00
 */
public final class Prop {
	/** Kein Fehler. **/
    public static final int ERR_NO_ERROR       = 0;
    
    /** Properties-Datei wurde bereits eingelesen. **/
    public static final int ERR_ALREADY_LOADED = 1;
    
    
	private static Properties 				prop = new Properties(System.getProperties());
//	private static List<String>      		filesLoaded   = new Vector<String>();
	private static HashMap<String, Long>    filesLoaded   = new HashMap<String, Long>();
	private static List<InputStream> 		streamsLoaded = new Vector<InputStream>();
    private static long              		fileDate      = 0L;
    private static boolean           		reloadConfig  = false;
    
    public static void main(String[] args) throws ConfigException {
        while (true) {
            load("conf", "mips.ini", false);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    private Prop() {
    }
    
	public static int load(final String path, final String file, boolean force) throws ConfigException {
		FileInputStream is = null;
		String loadFile = path.trim() + System.getProperty("file.separator") + file.trim();
        Utils.log("(Prop load) Loading \"" + loadFile + "\"");
        
        File inFile = new File(loadFile);
        long lastModified = inFile.lastModified();
        Utils.log("(Prop load) lastModified = " + lastModified);
        
        if (isLoaded(loadFile)) {
        	fileDate = filesLoaded.get(loadFile);
        } else {
        	fileDate = 0L;
        }
        
        if (fileDate == 0L) {
            fileDate = lastModified;
        }
        Utils.log("(Prop load) fileDate     = " + fileDate);
        
        if (force) {
        	reloadConfig = true;
        } else {
	        if (fileDate < lastModified) {
	        	Utils.log("(Prop load) fileDate ist < lastModified");
	            reloadConfig = true;
	            fileDate = lastModified;
	        } else {
	        	Utils.log("(Prop load) fileDate ist >= lastModified");
	            reloadConfig = false;
	        }
        }
        Utils.log("(Prop load) reloadConfig = " + reloadConfig);
        
        if (isLoaded(loadFile) && !reloadConfig) {
			Utils.log("(Prop load) File " + loadFile + " already loaded.");
//			return ERR_NO_ERROR;
			return ERR_ALREADY_LOADED;
		} 
        try {
//            File inFile = new File(loadFile);
		    if (!inFile.exists()) {
		        Utils.log("(Prop load) File " + loadFile + " does not exist.");
//				return ERR_FILE_NOT_EXIST;
		        throw new ConfigException("(Prop load) File " + loadFile + " does not exist.");
		    }
			is = new FileInputStream(inFile);
			prop.load(is);
			filesLoaded.put(loadFile, lastModified);
//            loaded = true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
            if (is != null) {
                try { 
                    is.close(); 
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
		}
		return ERR_NO_ERROR;
	}
	
	public static void load(InputStream is) {
	    Utils.log("(Prop load) InputStream: " + is);
		if (isLoaded(is))	{
		    Utils.log("(Prop load) Stream " + is + " already loaded.");
			return;
		} 
        try {
			prop.load(is);
			streamsLoaded.add(is);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				try { 
					is.close(); 
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static int reload(final String path, final String file) throws ConfigException {
	    Utils.log("(Prop reload) Reloading " + file);
		FileInputStream is = null;
		String loadFile = path.trim() + System.getProperty("file.separator") + file.trim();
	    try {
		    File inFile = new File(loadFile);
		    if (!inFile.exists()) {
		        Utils.log("(Prop reload) File " + loadFile + " does not exist.");
//				return ERR_FILE_NOT_EXIST;
		        throw new ConfigException("(Prop load) File " + loadFile + " does not exist.");
		    }
			is = new FileInputStream(inFile);
			prop.load(is);
			filesLoaded.put(loadFile, inFile.lastModified());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
            if (is != null) {
                try { 
                    is.close(); 
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
		}
		return ERR_NO_ERROR;
	}

	public static void reload(InputStream is) {
	    Utils.log("(Prop reload) InputStream: " + is);
	    try {
			prop.load(is);
			streamsLoaded.add(is);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				try { 
					is.close(); 
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private static boolean isLoaded(final String file) {
		return filesLoaded.containsKey(file);
	}
	
	private static boolean isLoaded(final InputStream is) {
		return streamsLoaded.contains(is);
	}
	
	public static String getProperty(final String key) {
        String value = prop.getProperty(key);
		return (value == null) ? null : value.trim();
	}

	public static String getProperty(final String key, final String def) {
        String value = prop.getProperty(key, def);
        if (value != null) {
            return prop.getProperty(key, def).trim();
        } else {
            return prop.getProperty(key, def);
        }
	}

	public static int getIntProperty(final String key) {
	    int ret = 0;
	    try {
            String value = prop.getProperty(key);
            ret = (value == null) ? 0 : Integer.parseInt(value.trim());
	    } catch (NumberFormatException e) {
	        ret = 0;
	    }
		return ret;
	}

	public static int getIntProperty(final String key, final String def) {
	    int ret = 0;
	    try {
	        ret = Integer.parseInt(prop.getProperty(key, def).trim());
	    } catch (NumberFormatException e) {
	        Utils.log(Utils.LOG_FATAL, "(Prop getIntProperty) " + e + ": key = " +  key + ", default = " + def);
	        ret = 0;
	    }
		return ret;
	}

	public static boolean getBooleanProperty(final String key) {
	    boolean ret = false;
        String value = prop.getProperty(key);
        ret = (value == null) ? false : Boolean.parseBoolean(value.trim());
		return ret;
	}

	public static boolean getBooleanProperty(final String key, final String def) {
	    boolean ret = false;
        ret = Boolean.parseBoolean(prop.getProperty(key, def).trim());
		return ret;
	}

	public static Class< ? > getClassProperty(final String key) {
		return getClassProperty(key, null);
	}

	public static Class<?> getClassProperty(final String key, final String def) {
        try {
            String value = prop.getProperty(key);
            return (value == null) ? null : Class.forName(value.trim());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
	}

//    public static boolean isLoaded() {
//        return loaded;
//    }

}
