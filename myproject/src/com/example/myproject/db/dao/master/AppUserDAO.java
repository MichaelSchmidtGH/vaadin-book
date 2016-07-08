package com.example.myproject.db.dao.master;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.example.myproject.config.Config;
import com.example.myproject.db.Db;
import com.example.myproject.db.DbUtil;
import com.example.myproject.db.dao.StdMasterDAO;
import com.example.myproject.db.dto.AppUserDTO;
import com.example.myproject.db.dto.DSrequest;
import com.example.myproject.db.dto.DSresult;
import com.example.myproject.exceptions.ApplicationException;
import com.example.myproject.exceptions.DataSaveException;
import com.example.myproject.exceptions.NoDataFoundException;
import com.example.myproject.utils.BCrypt;
import com.example.myproject.utils.Utils;

/**
 * Data Access Object für Tabelle "appUser".
 *     
 * Column name          Type                                    Nulls
 * mainitem             serial                                  no
 * user                 char(16)                                no
 * hash                 char(128)                               no
 * active               int(1)                                  no
 * admin                int(1)                                  no
 * readOnly             int(1)                                  no
 * status               char(1)                                 no
 * edat                 timestamp()                             yes
 * adat                 timestamp()                             yes
 * sb                   char(16)                                no
 *
 * @author schmidt
 * @version 1.0.00
 */
public class AppUserDAO extends StdMasterDAO<AppUserDTO> {
	private static final int ALL_ROWS = -1;

	/**
     * Statement für Query By Example SELECTs.
     */
    protected Statement         searchStmt;

    private PreparedStatement preparedStatement = null;
//    private ResultSet rs = null;
    
    private String configPath = null;
    
    public AppUserDAO() {
    }
    
    public AppUserDAO(String configPath) {
    	this.configPath = configPath; 
    }

    
    /**
     * Selektiert alle Sätze der Tabelle user, die zu den im übergebenen
     * DatenTransferObjekt angegebenen Selektionskriterien passen.
     *
     * @param  request Data Source Request enTransferObjekt mit den Suchkriterien.
     * @param  direction Leserichtung. Vor- oder rückwärts.
     * @return Menge der selektierten Sätze der Tabelle user in einer Liste von Objekten in einem DSresult objekt.
     * @throws ApplicationException Beim Auftreten einer SQLException
     * @throws NoDataFoundException Falls keine Userdaten gefunden wurden 
     * @see <code>IStdDAO</code>
     */
    public DSresult<AppUserDTO> searchRecords(DSrequest request, int direction) throws ApplicationException, NoDataFoundException {
//        Utils.log("(AppUserDAO searchRecords) configPath = " + configPath);

        DSresult<AppUserDTO> result = new DSresult<AppUserDTO>();
        
        AppUserDTO dto = request.getAppUserDto();
        
        StringBuffer s = new StringBuffer("SELECT mainitem FROM appUser WHERE 1=1");

        DbUtil.appendSelect(s, "mainitem", dto.getStrMainitem(), 						DbUtil.TYPE_NUM);
        DbUtil.appendSelect(s, "user",     DbUtil.getEmptyString(dto.getStrUser()),     DbUtil.TYPE_STRING);
        DbUtil.appendSelect(s, "hash",     DbUtil.getEmptyString(dto.getStrHash()),     DbUtil.TYPE_STRING);
        DbUtil.appendSelect(s, "active",   DbUtil.getEmptyString(dto.getStrActive()),   DbUtil.TYPE_NUM);
        DbUtil.appendSelect(s, "admin",    DbUtil.getEmptyString(dto.getStrAdmin()),    DbUtil.TYPE_NUM);
        DbUtil.appendSelect(s, "readOnly", DbUtil.getEmptyString(dto.getStrReadOnly()), DbUtil.TYPE_NUM);
        DbUtil.appendSelect(s, "status",   DbUtil.getEmptyString(dto.getStrStatus()),   DbUtil.TYPE_STRING);
        DbUtil.appendSelect(s, "edat",     dto.getStrEdat(),  							DbUtil.TYPE_TIMESTAMP);
        DbUtil.appendSelect(s, "adat",     dto.getStrAdat(),  							DbUtil.TYPE_TIMESTAMP);
        DbUtil.appendSelect(s, "sb",       DbUtil.getEmptyString(dto.getStrSb()), 		DbUtil.TYPE_STRING);

        if (direction == DbUtil.FORWARD) {
            s.append(" ORDER BY mainitem");
        } else {
            s.append(" ORDER BY mainitem DESC");
        }

        String query = s.toString();

        if (Config.getLogSQL()) {
             Utils.log("(AppUserDAO searchRecords) " + query);
        }

        if (!Db.checkConnection(DATABASE_INDEX)) {
            return null;
        }
        
        try {
            searchStmt = Db.getConnection(DATABASE_INDEX).createStatement();
            ResultSet rs = searchStmt.executeQuery(query);
            fillDSresultMainitems(request, result, rs, "mainitem");
        } catch (SQLException e) {
            throw new ApplicationException(e);
        }
        
        return result;
    }


    public boolean saveRecord(AppUserDTO dto) throws DataSaveException {
        long mainitem = dto.getMainitem();
//        String user = dto.getUser();
        
        // Password-Hash nur ermitteln, wenn Password nicht leer.
        String password = dto.getPasswort();
        if (!Utils.isStringEmpty(password)) {
            String hash = BCrypt.hashpw(password, BCrypt.gensalt()); 
        	dto.setHash(hash);
        	dto.setPasswort(null);
//            Utils.log("(AppUserDAO saveAppUserRecord) Hash " + hash + " für User " + user + " ermittelt.");
//        } else {
//        	Utils.log("(AppUserDAO saveAppUserRecord) Passwort ist leer. Es wurde kein Hash ermittelt.");
        }

        String sqlSelect = "select mainitem from appUser where mainitem = " + mainitem + ";";
        boolean recordExists = false;
        try {
            rs = Db.executeSQL(DATABASE_INDEX, sqlSelect);
            if (rs != null) {
                if (rs.next()) {
                    recordExists = true;
                }
            }
        } catch (SQLException e) {
            throw new DataSaveException("SQLException occured while trying to save User record with id (mainitem) " + mainitem, e);
        }
        
        checkUniqueKeys(dto);

        if (recordExists) {
            return updateRecord(dto);
        } else {
            return insertRecord(dto);
        }
    }
    

    public boolean checkUniqueKeys(AppUserDTO dto) throws DataSaveException {
        long mainitem  = dto.getMainitem();
        String user  = dto.getUser();
        
        String sqlCheckUser  = "SELECT mainitem, user from appUser where user='" + user + "' and status='A';";
        try {
            rs = Db.executeSQL(DATABASE_INDEX, sqlCheckUser);
            if (rs != null) {
                if (rs.next()) {
                	if (rs.getInt("mainitem") != mainitem) {
                		throw new DataSaveException("Ein Satz mit dem Benutzer " + user + " existiert bereits!");
                	}
                }
            }
        } catch (SQLException e) {
            throw new DataSaveException("SQLException occured while trying to select AppUser record for user " + user, e);
        }
        
        return false;
    }
    
    public boolean insertRecord(AppUserDTO dto) throws DataSaveException {
        String sqlInsert = "INSERT INTO appUser VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            preparedStatement = Db.getConnection(DATABASE_INDEX).prepareStatement(sqlInsert);

            preparedStatement.setLong(1, dto.getMainitem());
            preparedStatement.setString(2, DbUtil.getEmptyString(dto.getUser()));
            preparedStatement.setString(3, DbUtil.getEmptyString(dto.getHash()));
            preparedStatement.setBoolean(4, dto.isActive());
            preparedStatement.setBoolean(5, dto.isAdmin());
            preparedStatement.setBoolean(6, dto.isReadOnly());
            preparedStatement.setString(7, DbUtil.getEmptyString(dto.getStatus()));
            preparedStatement.setTimestamp(8, dto.getEdat());
            preparedStatement.setTimestamp(9, dto.getAdat());
            preparedStatement.setString(10, DbUtil.getEmptyString(dto.getSb()));

            return Db.executeSQLupdate(DATABASE_INDEX, preparedStatement);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataSaveException(e);
        }
    }
    
    
    public boolean updateRecord(AppUserDTO dto) throws DataSaveException {
    	Utils.log("(AppUserDAO updateRecord) Updating record in User table.");
        long mainitem = dto.getMainitem();
    	
        try {
        	Db.beginTransaction(DATABASE_INDEX);
			// Set old records status to History (H)...
			Utils.log("(AppUserDAO updateRecord) Update old record and setting status to H");
        	String sqlUpdateOld = "UPDATE appUser set status='H' where mainitem=" + mainitem + ";";
            if (Db.executeSQLupdate(DATABASE_INDEX, sqlUpdateOld)) {
	            // ...and insert new record containing the new values and status set to Active (A).
	        	Utils.log("(AppUserDAO updateRecord) Insert new record containing the new values and setting status to A.");
	        	dto.setMainitem(0);
	        	dto.setStatus("A");
	        	if (insertRecord(dto)) {
	        		Db.commitTransaction(DATABASE_INDEX);
	        		return true;
	        	} else {
	        		Db.rollbackTransaction(DATABASE_INDEX);
	        		return false;
	        	}
            } else {
        		Db.rollbackTransaction(DATABASE_INDEX);
            	return false;
            }
        } catch (DataSaveException e) {
        	try {
            	Db.rollbackTransaction(DATABASE_INDEX);
        	} catch (SQLException e1) {
                throw new DataSaveException(e);
        	}
            throw e;
        } catch (SQLException e) {
        	try {
            	Db.rollbackTransaction(DATABASE_INDEX);
        	} catch (SQLException e1) {
                throw new DataSaveException(e);
        	}
            throw new DataSaveException(e);
        }
    }
    
    
    /**
     * Satz löschen.
     * Der Satz wird nicht physikalisch aus der DB entfernt.
     * Statt dessen wird zuerst im alten Satz das Statuskennzeichen auf "H" gesetzt, damit das Änderungsdatum erhalten bleibt. 
     * Dann wird ein neuer Satz mit Status-Kennzeichen auf "D" (deleted) und Änderungsdatum = aktuelles Datum eingefügt.
     *  
     * @param mainitem Primary Key dessen Satz gelöscht werden soll.
     * @return true: Der Satz wurde als gelösch gekennzeichnet. False: Das Löschen ist fehlgeschlagen.
     * @throws DataSaveException Wenn eine SQLException auftrat
     */
    public boolean deleteRecord(long mainitem) throws DataSaveException {
    	Utils.log("(AppUserDAO deleteRecord) Deleting record. mainitem = " + mainitem);

    	// read old record...
    	AppUserDTO dto = null;
		try {
			dto = getRecord(mainitem);
		} catch (NoDataFoundException e) {
			// Record was already deleted by someone else
			return true;
		}
        
        try {
        	Db.beginTransaction(DATABASE_INDEX);
			// Set old records status to History (H)...
			Utils.log("(AppUserDAO deleteRecord) Update old record and setting status to H");
        	String sqlUpdateOld = "UPDATE appUser set status='H' where mainitem=" + mainitem + ";";
            if (Db.executeSQLupdate(DATABASE_INDEX, sqlUpdateOld)) {
	            // ...and insert new record with old values and setting status to Deleted (A).
	        	Utils.log("(AppUserDAO deleteRecord) Insert new record containing status D.");
	        	dto.setMainitem(0);
	        	dto.setAdat(new Timestamp(System.currentTimeMillis()));
	        	dto.setStatus("D");
	        	if (insertRecord(dto)) {
	        		Db.commitTransaction(DATABASE_INDEX);
	        		return true;
	        	} else {
	        		Db.rollbackTransaction(DATABASE_INDEX);
	                throw new DataSaveException("Fehler beim Einfügen des Satzes mit Status \"D\". Transaktion wurde zurückgerollt.");
	        	}
            } else {
        		Db.rollbackTransaction(DATABASE_INDEX);
                throw new DataSaveException("Fehler bei UPDATE appUser set user_status='H' where mainitem=" + mainitem + 
                																				"; Transaktion wurde zurückgerollt.");
            }
        } catch (SQLException e) {
        	try {
            	Db.rollbackTransaction(DATABASE_INDEX);
        	} catch (SQLException e1) {
                throw new DataSaveException(e);
        	}
            throw new DataSaveException(e);
        }
    }
    
    /**
     * Löscht alle Sätze eines User aus der Datenbank.
     * Die Sätze werden physikalisch aus der DB entfernt!
     * Die Sätze werden unabhängig vom Status ((A)ktiv, (H)istorie, (D)eleted) gelöscht.
     *  
     * Diese Methode darf nur von (JUnit-)Tests benutzt werden!
     * 
     * @param  user		Name des Users dessen Sätze gelöscht werden sollen.
     * @return Anzahl der gelöschten Sätze.
     * @throws DataSaveException Wenn eine SQLException auftrat
     */
    public int deleteRecords(String user) throws DataSaveException {
    	Utils.log("(AppUserDAO deleteRecords) Deleting ALL records for user " + user);
    	String sql = "delete from appUser where user = '" + user + "';";
        return Db.executeSQLupdateInt(DATABASE_INDEX, sql);
    }

    

    /**
     * Liest einen Satz aus der Datenbank.
     * 
     * @param user		Zu lesender User
     * @param status	Zu lesender Status
     * @return			Den gefundenen Satz
     * @throws NoDataFoundException Falls kein Satz gefunden wurde
     */
    public AppUserDTO getRecord(String user, String status) throws NoDataFoundException {
        String sql = "select * from appUser where user = '" + user + "' and status = '" + status + "';";
        try {
            rs = Db.executeSQL(DATABASE_INDEX, sql);
            if (rs != null) {
                if (rs.next()) {
                    AppUserDTO dto = new AppUserDTO();
                    fillDTO(dto, rs);
                    return dto;
                } else {
                    throw new NoDataFoundException("No AppUser record found for user " + user);
                }
            } else {
                throw new NoDataFoundException("Resultset is null while trying to get AppUser record for user " + user);
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
            e.printStackTrace();
            throw new NoDataFoundException("SQLException occured while trying to get AppUser record for user " + user);
        } finally {
            close();
        }
    }
    
    
    public AppUserDTO getRecord(String user, String status, Timestamp adat) throws NoDataFoundException {
        String sql = "select * from appUser where user = '" + user + "' and status = '" + status + "' and adat = '" + adat + "';";
        try {
            rs = Db.executeSQL(DATABASE_INDEX, sql);
            if (rs != null) {
                if (rs.next()) {
                    AppUserDTO dto = new AppUserDTO();
                    fillDTO(dto, rs);
                    return dto;
                } else {
                    throw new NoDataFoundException("No AppUser record found for user " + user + " Status " + status + " Adat " + adat);
                }
            } else {
                throw new NoDataFoundException("Resultset is null while trying to get AppUser record for user " + user);
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
            e.printStackTrace();
            throw new NoDataFoundException("SQLException occured while trying to get AppUser record for user " + user);
        } finally {
            close();
        }
    }
    
    public AppUserDTO getRecordEdat(String user, String status, Timestamp edat) throws NoDataFoundException {
        String sql = "select * from appUser where user = '" + user + "' and status = '" + status + "' and edat = '" + edat + "';";
        try {
            rs = Db.executeSQL(DATABASE_INDEX, sql);
            if (rs != null) {
                if (rs.next()) {
                    AppUserDTO dto = new AppUserDTO();
                    fillDTO(dto, rs);
                    return dto;
                } else {
                    throw new NoDataFoundException("No AppUser record found for user " + user + " Status " + status + " Edat " + edat);
                }
            } else {
                throw new NoDataFoundException("Resultset is null while trying to get AppUser record for user " + user);
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
            e.printStackTrace();
            throw new NoDataFoundException("SQLException occured while trying to get AppUser record for user " + user);
        } finally {
            close();
        }
    }
    
    public AppUserDTO getRecord(long mainitem) throws NoDataFoundException {
        String sql = "select * from appUser where mainitem = '" + mainitem + "';";
        try {
            rs = Db.executeSQL(DATABASE_INDEX, sql);
            if (rs != null) {
                if (rs.next()) {
                    AppUserDTO dto = new AppUserDTO();
                    fillDTO(dto, rs);
                    return dto;
                } else {
                    throw new NoDataFoundException("No User record found for mainitem " + mainitem);
                }
            } else {
                throw new NoDataFoundException("Resultset is null while trying to get User record for mainitem " + mainitem);
            }
        } catch (SQLException e) {
            throw new NoDataFoundException("SQLException occured while trying to get User record for mainitem " + mainitem);
        } finally {
            close();
        }
    }
    

    /**
     * Selects all active (status = A) appUser records ordered by user.
     * 
     * @return DSresult object containing a list of the selected AppUserDTOs. 
     * @throws NoDataFoundException If no records are found.
     */
    public DSresult<AppUserDTO> getRecords() throws NoDataFoundException {
        Utils.log("(AppUserDAO getRecords() ");
//        if (!Db.checkConnection(DATABASE_INDEX, configPath)) {
//            return (null);
//        }

        DSresult<AppUserDTO> result = new DSresult<AppUserDTO>();
        
        String sql = "select * " +
        		     "from appUser " +
        		     "where status = 'A' " +
        		     "order by user;";
        
        ArrayList<AppUserDTO> appUserRecords = new ArrayList<AppUserDTO>();
        AppUserDTO dto = null;
        try {
            rs = Db.executeSQL(DATABASE_INDEX, sql);
            if (rs != null) {
                int rows = 0;
                while (rs.next()) {
                	rows++;
                	dto = new AppUserDTO();
                	fillDTO(dto, rs);
                	appUserRecords.add(dto);
                }
                if (rows == 0) {
                    Utils.log("(AppUserDAO getRecords) No rows found");
                } else {
                    Utils.log("(AppUserDAO getRecords) " + rows + " rows found");
                }
//                result.setAppUserDTOs(appUserRecords);
                result.setResultDTOs(appUserRecords);
                return result;
            } else {
                throw new NoDataFoundException("Resultset is null while trying to get all AppUser records.");
            }
        } catch (SQLException e) {
            throw new NoDataFoundException("SQLException occured while trying to get all AppUser records.", e);
        } finally {
            close();
        }
    }
    

    /**
     * Selects all active (status = A) appUser records which are contained in the given 
     * list of primary keys (mainitems).
     * 
     * @param mainitems List of primary keys (long mainitem). 
     * @return DSresult object containing a list of the selected AppUserDTOs. 
     * @throws NoDataFoundException If no records are found.
     */
    public DSresult<AppUserDTO> getRecords(ArrayList<Long> mainitems) throws NoDataFoundException {
        Utils.log("(AppUserDAO getRecords(mainitems) ");
        if (!Db.checkConnection(DATABASE_INDEX, configPath)) {
            return (null);
        }

        DSresult<AppUserDTO> result = new DSresult<AppUserDTO>();
        AppUserDTO dto;
        ArrayList<AppUserDTO> appUserDTOs = new ArrayList<AppUserDTO>();
        
        String sql = "select * from appUser where mainitem = ? and status = 'A';";
        
        try {
        	Utils.log("(AppUSerDAO getRecords(mainitems)) sql = " + sql);
            preparedStatement = Db.getConnection(DATABASE_INDEX).prepareStatement(sql);
            for (long mainitem : mainitems) {
                preparedStatement.setLong(1, mainitem);
                rs = Db.executeSQL(DATABASE_INDEX, preparedStatement);
                if (rs != null) {
                    if (rs.next()) {
                    	dto = new AppUserDTO();
                    	fillDTO(dto, rs);
                    	appUserDTOs.add(dto);
                    } else {
                        Utils.log("(AppUaerDAO getRecords(mainitems)) No appUser record found for mainiem " + mainitem + "!");
                        throw new NoDataFoundException("(AppUserDAO getRecords(mainitems)) No appUser record found for mainitem " + mainitem + "!");
                    }
                } else {
                    throw new NoDataFoundException("Resultset is null while trying to get appUser record for mainitem " + mainitem + "!");
                }
            }
//            result.setAppUserDTOs(appUserDTOs);
            result.setResultDTOs(appUserDTOs);
            return result;
        } catch (SQLException e) {
            throw new NoDataFoundException("SQLException occured while trying to read AppUser records.", e);
        } finally {
            close();
        }
    }
    
    /**
     * Selects all records which have the requested status.
     * Requested statuses must be set in DSrequest object.
     *   
     * @param request DSrequest object.
     * @return DSresult object containing a list of the selected DTOs. 
     * @throws NoDataFoundException If no recorded matched the requested statuses.
     */
    public DSresult<AppUserDTO> getRecords(DSrequest request) throws NoDataFoundException {
        Utils.log("(AppUserDAO getRecords(DSRequest) ");
//        if (!Db.checkConnection(DATABASE_INDEX, configPath)) {
//            return (null);
//        }

        int fromRow   = request.getFromRow();
        int toRow     = request.getToRow();
        int totalRows = request.getTotalRows();
        String order  = request.getOrder();

    	Utils.log("(AppUserDAO getRecords) request: fromRow   = " + fromRow);
    	Utils.log("(AppUserDAO getRecords) request: toRow     = " + toRow);
    	Utils.log("(AppUserDAO getRecords) request: totalRows = " + totalRows);
    	Utils.log("(AppUserDAO getRecords) request: order     = " + order);

        
        DSresult<AppUserDTO> result = new DSresult<AppUserDTO>();
        
        String status = "";
        if (request.isActive()) {
        	status = status + ", 'A'";
        }
        if (request.isHistory()) {
        	status = status + ", 'H'";
        }
        if (request.isDeleted()) {
        	status = status + ", 'D'";
        }
        
        String whereStatus = null;
        if (Utils.isStringEmpty(status)) {
        	whereStatus = "status = 'A' ";
        } else {
        	status = status.replaceFirst(", ", "");
            whereStatus = "status in (" + status + ")";
        }
        
        String orderString;
        if (request.isAscending()) {
        	orderString = "order by " + order + ", edat, adat, status;";
        } else {
        	orderString = "order by " + order + " desc, edat desc, adat desc, status desc;";
        }
        
        String sql = "select * from appUser " + 
        			 "where " + whereStatus + " " +
        			 orderString;

        ArrayList<AppUserDTO> appUserRecords = new ArrayList<AppUserDTO>();
        AppUserDTO dto = null;
        
        try {
            rs = Db.executeSQL(DATABASE_INDEX, sql);
            if (rs != null) {
            	int i;
                int rows = 0;
            	if (totalRows == ALL_ROWS) {
            		fromRow = 1;
                    while (rs.next()) {
                    	rows++;
                    	dto = fillDTO(rs);
                        appUserRecords.add(dto);
                    }
                    if (rows == 0) {
                        Utils.log("(AppUserDAO getRecords) No rows found");
                    } else {
                        Utils.log("(AppUserDAO getRecords) " + rows + " rows found");
                    }
            	} else {
                	Utils.log("(AppUserDAO getRecords) Skipping " + fromRow + " records.");
	                for (i = 1; i < fromRow && rs.next(); i++) {
	                	;
	                }
	                fromRow = i;
	            	Utils.log("(AppUserDAO getRecords) Fetching " + totalRows + " records...");
	                for (i = 0; i < totalRows; i++) {
	                    if (rs.next()) {
	                    	rows++;
	                    	dto = fillDTO(rs);
	                        appUserRecords.add(dto);
	                    } else {
	                        Utils.log("(AppUserDAO getRecords) No more rows");
	                        break;
	                    }
					}
            	}
                result.setFromRow(fromRow);
                result.setToRow(fromRow + rows - 1);
                result.setTotalRows(rows);
//                result.setAppUserDTOs(appUserRecords);
                result.setResultDTOs(appUserRecords);
            	Utils.log("(AppUserDAO getRecords) result: fromRow   = " + result.getFromRow());
            	Utils.log("(AppUserDAO getRecords) result: toRow     = " + result.getToRow());
            	Utils.log("(AppUserDAO getRecords) result: totalRows = " + result.getTotalRows());
                return result;
            } else {
                throw new NoDataFoundException("Resultset is null while trying to get all AppUser records.");
            }
        } catch (SQLException e) {
            throw new NoDataFoundException("SQLException occured while trying to get all AppUser records.", e);
        } finally {
            close();
        }
    }
    
    
    

//    private DSresult fillDSresultMainitems(DSrequest request, DSresult result, ResultSet rs) throws NoDataFoundException {
//        ArrayList<Long> mainitems = new ArrayList<Long>();
//        
//        int totalRows = request.getTotalRows();
//        if (totalRows <= 0) {
//            totalRows = Integer.MAX_VALUE;
//        }
//        
//        try {
//            if (rs != null) {
//                int rows = 0;
//                for (int i = 0; i < totalRows; i++) {
//                    if (rs.next()) {
//                    	Utils.log("(AppUserDAO fillDSresultMainitems) rs.next ausgeführt");
//                        rows++;
//                        Long mainitem = rs.getLong("mainitem");
//                    	Utils.log("(AppUserDAO fillDSresultMainitems) mainitem = " + mainitem);
//                        mainitems.add(mainitem);
//                    } else {
//                        break;
//                    }
//                }
//                result.setTotalRows(rows);
//                result.setMainitems(mainitems);
//                return result;
//            } else {
//                throw new NoDataFoundException("Resultset is null while trying to get searched records.");
//            }
//        } catch (SQLException e) {
//        	e.printStackTrace();
//            throw new NoDataFoundException("SQLException occured while trying to get searched records.");
//        } finally {
//            close();
//        }
//    }
    
    private AppUserDTO fillDTO(ResultSet rs) throws SQLException {
    	AppUserDTO dto = new AppUserDTO();
    	dto.setMainitem(rs.getLong("mainitem"));
        dto.setUser(rs.getString("user"));
        dto.setHash(rs.getString("hash"));
        dto.setActive(rs.getBoolean("active"));
        dto.setAdmin(rs.getBoolean("admin"));
        dto.setReadOnly(rs.getBoolean("readonly"));
        dto.setStatus(rs.getString("status"));
        dto.setEdat(rs.getTimestamp("edat"));
        dto.setAdat(rs.getTimestamp("adat"));
        dto.setSb(rs.getString("sb"));
        return dto;
    }
    
    private void fillDTO(AppUserDTO dto, ResultSet rs) throws SQLException {
        dto.setMainitem(rs.getInt("mainitem"));
        dto.setUser(rs.getString("user"));
        dto.setHash(rs.getString("hash"));
        dto.setActive(rs.getBoolean("active"));
        dto.setAdmin(rs.getBoolean("admin"));
        dto.setReadOnly(rs.getBoolean("readonly"));
        dto.setStatus(rs.getString("status"));
        dto.setEdat(rs.getTimestamp("edat"));
        dto.setAdat(rs.getTimestamp("adat"));
        dto.setSb(rs.getString("sb"));
    }
    
    
//    private void close() {
//        if (rs != null) {
//            try {
//                rs.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//    }

}
