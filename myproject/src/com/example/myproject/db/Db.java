package com.example.myproject.db;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.example.myproject.config.Config;
import com.example.myproject.exceptions.ConfigException;
import com.example.myproject.exceptions.DataSaveException;
import com.example.myproject.utils.Utils;


/**
 * Die Klasse Db repräsentiert die Verbindung zur Datenbank.
 * 
 * @author schmidt
 * @version 1.0.00
 */
public final class Db {
	private static ClassLoader cl = null;
	// MS20150709 Begin
//	private static Connection con;
	private static Connection[] con;
	// MS20150709 End

	
//	private static boolean connected = false;
	private static volatile boolean[] connected;			// Verbindungsstatus. Volatile wegen synchronized Zugriff

	// MS20150709 Begin
	//private static boolean inTransaction = false;			// TransaktionsStatus
	private static boolean[] inTransaction;					// TransaktionsStatus
	// MS20150709 End
	
	private static String configPath = null;
	private static String configFile = null;

    private Db() {
    }
    
	public static synchronized boolean connectAll(String configPath) throws SQLException, ConfigException {
		return Db.connectAll(configPath, "ccwebui.ini");
	}
	
	public static synchronized boolean connectAll(String configPath, String configFile) throws SQLException, ConfigException {
		Db.configPath = configPath;
		Db.configFile = configFile;
		
        if (Config.getDbdriver(0) == null) {
        	Utils.log("(Db connect) configPath = " + configPath);
            Config.configure(configPath, configFile);
        }

		
        ArrayList<String> databases = Config.getDatabases();
        Utils.log("(Db connectAll) numberOfDatabases = " + Config.getNumberOfDatabases());
        Utils.log("(Db connectAll) databases         = " + databases);

		boolean result = true;
        for (int i = 0; i < Config.getNumberOfDatabases(); i++) {
            Utils.log("(Db connectAll) db.driver         = " + Config.getDbdriver(i));
        	result = result && connect(i);
        }
        return result;
	}

	public static synchronized boolean connect(int databaseIndex) throws SQLException, ConfigException {
		if (cl == null) {
			cl = Db.class.getClassLoader();
		}
		
        if (Config.getDbdriver(databaseIndex) == null) {
        	Utils.log("(Db connect) configPath = " + configPath);
            Config.configure(configPath, configFile);
        }

        if (Config.isLogDriverManager()) {
        	DriverManager.setLogWriter(new PrintWriter(System.out));
        }
        
		try {
            Class.forName(Config.getDbdriver(databaseIndex)); 
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		}
		try {
            if (con == null) {
                con = new Connection[Config.getNumberOfDatabases()];
            }
            if (inTransaction == null) {
            	inTransaction = new boolean[Config.getNumberOfDatabases()];
            }
            if (connected == null) {
            	connected = new boolean[Config.getNumberOfDatabases()];
            }

        	inTransaction[databaseIndex] = false;
        	connected[databaseIndex] = false;
            
        	String database = Config.getDatabases().get(databaseIndex);
        	Utils.log("(Db connect) database = " + database);

        	String connect = Config.getDbconnect(databaseIndex).replace("$DATABASE", database);
            Utils.log("(Db connect) connect = " + connect);
            
            if (!Config.getDbuser(databaseIndex).equals("")) {
                con[databaseIndex] = DriverManager.getConnection(connect,
                                                  Config.getDbuser(databaseIndex),
                                                  Config.getDbpasswd(databaseIndex));
            } else {
                con[databaseIndex] = DriverManager.getConnection(connect);
            }
            
			connected[databaseIndex] = true;
			return connected[databaseIndex];
		} catch (SQLException e) {
            Utils.log("(Db connect) SQLException occured");
			e.printStackTrace();
			throw e;
		}
	}

	public static boolean disconnectAll() {
		boolean result = true;
		for (int i = 0; i < con.length; i++) {
			result = result && disconnect(i);
		}
		return result;
	}

	
	public static boolean disconnect(int databaseIndex) {
		boolean result = true;
		if (connected[databaseIndex]) {
			try {
				connected[databaseIndex] = false;
				con[databaseIndex].close();
				result = true;
			} catch (SQLException e) {
				e.printStackTrace();
				result = false;
			}
		}
		return result;
	}

	
	// Entfernen wenn mit CCC-88 fertig!
//	public static synchronized boolean checkConnection(String configPath) {
//		Db.configPath = configPath;
//		return checkConnection(0);
//	}
	
	// Entfernen wenn mit CCC-88 fertig!
//	public static synchronized boolean checkConnection() {
//		return checkConnection(0);
//	}
	
	public static synchronized boolean checkConnection(int databaseIndex, String configPath) {
		Db.configPath = configPath;
		return checkConnection(databaseIndex);
	}
	public static boolean checkConnection(int databaseIndex) {
//		Utils.log("(Db checkConnection) ");
        if (connected == null) {
        	connected = new boolean[Config.getNumberOfDatabases()];
        }
		connected[databaseIndex] = true;
		try {
			if (con == null || con.length == 0) {
				connected[databaseIndex] = false;
			} else {
//				Utils.log("(Db checkConnection) con.length = " + con.length);
				if (con[databaseIndex] == null) {
					connected[databaseIndex] = false;
				} else if (con[databaseIndex].isClosed()) {
					connected[databaseIndex] = false;
				} else {
					connected[databaseIndex] = testConnection(databaseIndex);
				}
			}
			// MS20150709 End
			if (!connected[databaseIndex]) {
				connect(databaseIndex);
			} 
		} catch (SQLException e) {
		    Utils.log("(Db checkConnection) SQLException occured");
    		Utils.log("(Db checkConnection) SQLException: " + e.getMessage());
            Utils.log("(Db checkConnection) SQLState: " + e.getSQLState());
            Utils.log("(Db checkConnection) VendorError: " + e.getErrorCode());
			e.printStackTrace();
			connected[databaseIndex] = false; 
		} catch (ConfigException e) {
		    Utils.log("(Db checkConnection) ConfigException occured");
			e.printStackTrace();
			connected[databaseIndex] = false; 
		}
		return connected[databaseIndex];
	}

//	@SuppressFBWarnings(value = "RV_RETURN_VALUE_IGNORED", justification = "Statement serves for connection test only")
	private static synchronized boolean testConnection(int databaseIndex) {
		Statement stmt = null;
		try {
			// MS20150709 Begin
//			stmt = con.createStatement();
//			stmt.executeQuery("SELECT 1");
			if (con == null) {
				return false;
			}
			if (con.length < Config.getNumberOfDatabases()) {
				return false;
			}
			if (con[databaseIndex] == null) {
				return false;
			}
			stmt = con[databaseIndex].createStatement();
			stmt.executeQuery("SELECT 1 FROM dual");
			stmt.close();
			stmt = null;
			// MS20150709 End
			return true;
		} catch (SQLException e) {
		    Utils.log("(Db testConnection) SQLException occured");
    		Utils.log("(Db testConnection) SQLException: " + e.getMessage());
            Utils.log("(Db testConnection) SQLState: " + e.getSQLState());
            Utils.log("(Db testConnection) VendorError: " + e.getErrorCode());
			e.printStackTrace();
			return false; 
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
					stmt = null;
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	
	public static boolean getConnected(int databaseIndex) {
		if (connected == null || connected.length == 0) {
			return false;
		}
		return connected[databaseIndex];
	}


    public static Connection getConnection(int index) {
        return con[index];
    }
	
	public static void setClassLoader(ClassLoader parCl) {
		cl = parCl;
	}
	

    public static ResultSet executeSQL(int databaseIndex, String sql) throws SQLException {
    	return executeSQL(databaseIndex, sql, true);
    }

    public static ResultSet executeSQL(int databaseIndex, String sql, boolean log) throws SQLException {
        ResultSet rs = null;
        Statement stmt = null;
        try {
            checkConnection(databaseIndex);
            stmt = con[databaseIndex].createStatement();
            if (log) {
            	Utils.log("(Db executeSQL) sql = " + sql);
            }
            if (Config.isDbSetQueryTimeout()) {
                stmt.setQueryTimeout(Config.getDbQueryTimeout());
            }
            rs = stmt.executeQuery(sql);
        } catch (SQLException ex) {
            // handle any errors
            String sqlState = ex.getSQLState();
        	// Die beiden 'Retry-fähigen' SQL-Zustände sind 08S01
            // für einen Kommunikationsfehler und 40001 für Deadlock.
            //
            // Retry nur dann, wenn der Fehler wegen einer alten Verbindung,
            // Kommunikationsproblemen oder einem Deadlock auftrat
            //
            if ("08S01".equals(sqlState) || "40001".equals(sqlState)) {
            	Utils.log("(Db executeSQL) Retrying statement (" + sql + ")");
                try {
                    rs = stmt.executeQuery(sql);
                } catch (SQLException ex1) {
            		Utils.log("(Db executeSQL) SQLException: " + ex.getMessage());
                    Utils.log("(Db executeSQL) SQLState: " + ex.getSQLState());
                    Utils.log("(Db executeSQL) VendorError: " + ex.getErrorCode());
                    ex.printStackTrace();
                    throw ex;
                }
            } else {
        		Utils.log("(Db executeSQL) SQLException: " + ex.getMessage());
                Utils.log("(Db executeSQL) SQLState: " + ex.getSQLState());
                Utils.log("(Db executeSQL) VendorError: " + ex.getErrorCode());
                ex.printStackTrace();
            	throw ex;
            }
        }
        return rs;
    }


    public static ResultSet executeSQL(int databaseIndex, PreparedStatement statement) throws SQLException {
        ResultSet rs = null;
        try {
            checkConnection(databaseIndex);
            if (Config.isDbSetQueryTimeout()) {
                statement.setQueryTimeout(Config.getDbQueryTimeout());
            }
            rs = statement.executeQuery();
        } catch (SQLException ex) {
                // handle any errors
                System.out.println("(Db executeSQL) SQLException: " + ex.getMessage());
                System.out.println("(Db executeSQL) SQLState: " + ex.getSQLState());
                System.out.println("(Db executeSQL) VendorError: " + ex.getErrorCode());
                ex.printStackTrace();
                throw ex;
        }
        return rs;
    }

    public static ResultSet executeSQL(int databaseIndex, Statement statement, String sql, boolean log) throws SQLException {
        ResultSet rs = null;
        try {
            checkConnection(databaseIndex);
            if (log) {
            	Utils.log("(Db executeSQL) sql = " + sql);
            }
            if (Config.isDbSetQueryTimeout()) {
                statement.setQueryTimeout(Config.getDbQueryTimeout());
            }
            rs = statement.executeQuery(sql);
        } catch (SQLException ex) {
                // handle any errors
                System.out.println("(Db executeSQL) SQLException: " + ex.getMessage());
                System.out.println("(Db executeSQL) SQLState: " + ex.getSQLState());
                System.out.println("(Db executeSQL) VendorError: " + ex.getErrorCode());
                ex.printStackTrace();
                throw ex;
        }
        return rs;
    }
    
	// Entfernen wenn mit CCC-88 fertig!
//	public static Boolean executeSQLupdate(String sql) throws DataSaveException {
//		return executeSQLupdate(0, sql);
//	}
    
//	public static Boolean executeSQLupdate(int databaseIndex, String sql) throws DataSaveException {
//        PreparedStatement updateStmt = null;
//		int i = 0;
//		try {
//            checkConnection(databaseIndex);
//			Utils.log("(Db executeSQLupdate) prepareStatement(sql): sql = " + sql);
//        	updateStmt = con[databaseIndex].prepareStatement(sql);
//            if (Config.isDbSetQueryTimeout()) {
//            	updateStmt.setQueryTimeout(Config.getDbQueryTimeout());
//            }
//			i = updateStmt.executeUpdate();
//            Utils.log("(Db executeSQLupdate) PreparedStatement finished");
//    	} catch (SQLException ex) {
//    			// handle any errors
//    			System.out.println("SQLException: " + ex.getMessage());
//    			System.out.println("SQLState: " + ex.getSQLState());
//    			System.out.println("VendorError: " + ex.getErrorCode());
//    			ex.printStackTrace();
//    			i = -1;
//    			throw new DataSaveException(ex);
//    	} finally {
//    	    close(updateStmt);
//    	}
//    	
//  		return i > 0;
//	}
	
	public static Boolean executeSQLupdate(int databaseIndex, String sql) throws DataSaveException {
  		return executeSQLupdateInt(databaseIndex, sql) > 0;
	}
	
	public static int executeSQLupdateInt(int databaseIndex, String sql) throws DataSaveException {
        PreparedStatement updateStmt = null;
		int i = 0;
		try {
            checkConnection(databaseIndex);
			Utils.log("(Db executeSQLupdateInt) prepareStatement(sql): sql = " + sql);
        	updateStmt = con[databaseIndex].prepareStatement(sql);
            if (Config.isDbSetQueryTimeout()) {
            	updateStmt.setQueryTimeout(Config.getDbQueryTimeout());
            }
			i = updateStmt.executeUpdate();
            Utils.log("(Db executeSQLupdateInt) PreparedStatement finished");
    	} catch (SQLException ex) {
    			// handle any errors
    			System.out.println("SQLException: " + ex.getMessage());
    			System.out.println("SQLState: " + ex.getSQLState());
    			System.out.println("VendorError: " + ex.getErrorCode());
    			ex.printStackTrace();
    			i = -1;
    			throw new DataSaveException(ex);
    	} finally {
    	    close(updateStmt);
    	}
    	
  		return i;
	}
	
	// Entfernen wenn mit CCC-88 fertig!
//    public static Boolean executeSQLupdate(PreparedStatement updateStmt) throws SQLException {
//    	return executeSQLupdate(0, updateStmt);
//    }
    
	public static Boolean executeSQLupdate(int databaseIndex, PreparedStatement updateStmt) throws SQLException {
    	return executeSQLupdate(databaseIndex, updateStmt, true);
    }
    
    public static Boolean executeSQLupdate(int databaseIndex, PreparedStatement updateStmt, boolean doClose) throws SQLException {
        int i = 0;
        try {
            checkConnection(databaseIndex);
            if (Config.isDbSetQueryTimeout()) {
                updateStmt.setQueryTimeout(Config.getDbQueryTimeout());
            }
            i = updateStmt.executeUpdate();
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            ex.printStackTrace();
            i = -1;
            throw ex;
        } finally {
        	if (doClose) {
        		close(updateStmt);
        	}
        }
        
        return i > 0;
    }
    
	// Entfernen wenn mit CCC-88 fertig!
//	public static void beginTransaction() throws SQLException {
//		beginTransaction(0);
//	}
    
	public static void beginTransaction(int databaseIndex) throws SQLException {
	    // Mehrfache Transaktionsstarts werden ignoriert
	    if (inTransaction[databaseIndex]) {
	        Utils.log("(Db beginTransaction) Transaction already startet.");
	        return;
	    }
	    
		try {
	        Utils.log("(Db beginTransaction) Starting transaction.");
			con[databaseIndex].setAutoCommit(false);
			inTransaction[databaseIndex] = true;
		} catch (SQLException ex) {
 			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState    : " + ex.getSQLState());
			System.out.println("VendorError : " + ex.getErrorCode());			
			ex.printStackTrace();
			throw ex;
		} 
	}

	
	// Entfernen wenn mit CCC-88 fertig!
//	public static void commitTransaction() throws SQLException {
//		commitTransaction(0);
//	}
	public static void commitTransaction(int databaseIndex) throws SQLException {
	    // Wenn keine Transaktion begonnen wurde, wird auch kein commit abgesetzt.
	    if (!inTransaction[databaseIndex]) {
	    	Utils.log("(Db commitTransaction) No transaction active - commit will not be executed.");
	        return;
	    }
	    
		try {
	    	Utils.log("(Db commitTransaction) Committing transaction.");
			con[databaseIndex].commit();
			con[databaseIndex].setAutoCommit(true);
			inTransaction[databaseIndex] = false;
		} catch (SQLException ex) {
 			Utils.log("(Db commitTransaction) SQLException: " + ex.getMessage());
			Utils.log("(Db commitTransaction) SQLState    : " + ex.getSQLState());
			Utils.log("(Db commitTransaction) VendorError : " + ex.getErrorCode());			
			ex.printStackTrace();
			try {
				con[databaseIndex].rollback();
				con[databaseIndex].setAutoCommit(true);
				inTransaction[databaseIndex] = false;
				throw ex;
			} catch (SQLException ex2) {
				ex2.printStackTrace();
				throw ex2;
			}
		} 
	}

	// Entfernen wenn mit CCC-88 fertig!
//	public static void rollbackTransaction() throws SQLException {
//		rollbackTransaction(0);
//	}
	public static void rollbackTransaction(int databaseIndex) throws SQLException {
	    // Wenn keine Transaktion begonnen wurde, wird auch kein rollback abgesetzt.
	    if (!inTransaction[databaseIndex]) {
	    	Utils.log("(Db rollbackTransaction) No transaction active - rollback will not be executed.");
	        return;
	    }
	    
		try {
	    	Utils.log("(Db rollbackTransaction) Rolling back transaction.");
			con[databaseIndex].rollback();
			con[databaseIndex].setAutoCommit(true);
			inTransaction[databaseIndex] = false;
		} catch (SQLException ex) {
 			Utils.log("(Db commitTransaction) SQLException: " + ex.getMessage());
			Utils.log("(Db commitTransaction) SQLState    : " + ex.getSQLState());
			Utils.log("(Db commitTransaction) VendorError : " + ex.getErrorCode());			
			ex.printStackTrace();
			throw ex;
		} 
	}

	public static void close(Statement stmt) {
	    try {
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}
}
