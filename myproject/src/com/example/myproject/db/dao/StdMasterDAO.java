package com.example.myproject.db.dao;

import java.sql.SQLException;

import com.example.myproject.constants.EDatabases;
import com.example.myproject.db.Db;
import com.example.myproject.exceptions.DataSaveException;
import com.example.myproject.utils.Utils;


/**
 * Standard Master DAO.
 * Oberklasse aller DAOs für die Datenbank "Master".
 * 
 * @author schmidt
 * @param <T> Typ des DTO.
 *
 */
public abstract class StdMasterDAO<T> extends StdDAO<T> {
//	/**
//	 * Wert für den Parameter "Anzahl zu selektierende Sätze",
//	 * wenn alle Sätze gelesen werden sollen. 
//	 */
//	protected static final int ALL_ROWS = -1;
//
//	/**
//	 * Resultset für die Datenbankabfragen.
//	 */
//	protected ResultSet rs = null;
//	
	/**
	 * Datenbank-Index der  Master-Datenbank.
	 */
	protected static final int DATABASE_INDEX = EDatabases.MASTER.getIndex();

	/**
	 * Check if record already exists in a database table.
	 * 
	 * @param mainitemField	Name of the primary key field (usually <tablename>_mainitem or just mainitem. 
	 * @param mainitem		Primary key of the current record. Do not complain if THIS record exists. 
	 * @param keyFields		String listing the field names of the unique key to check. Format should be name1/name2/... . 
	 * @param keyValues		String listing values of the key fields of the current record.
	 * @param sql			SQL-Statement to read all records matching a unique key.
	 * @param errorText		This text will be thrown via DataSaveException if there is a duplicat key condition. 
	 * @throws DataSaveException If a duplicate key condition is detected (i. e. the SQL-Statement results in more than the current record).
	 * 							 The Exceptions message will be the contents of parameter <i>errorText</i>.<br>
	 * 							 If a SQL-Exception occurs. The Exceptions message will be a text containing <i>keyFields</i> and <i>keyValues</i>.
	 * 							 
	 */
    protected void checkKey(String mainitemField, long mainitem, String keyFields, String keyValues, String sql, String errorText) throws DataSaveException {
    	Utils.log("(StdMasterDAO checkKey) sql = " + sql);
        try {
            rs = Db.executeSQL(DATABASE_INDEX, sql);
            if (rs != null) {
                while (rs.next()) {
                	if (rs.getInt(mainitemField) != mainitem) {
                        throw new DataSaveException(errorText);
                	}
                }
            }
        } catch (SQLException e) {
            throw new DataSaveException("SQLException occured while trying to select record for key " + keyFields + 
            																							" values(" + keyValues + ")", e);
        }
    }
	
//    protected DSresult fillDSresultMainitems(DSrequest request, DSresult result, ResultSet rs, String mainitemField) throws NoDataFoundException {
//    	Utils.log("(StdMasterDAO fillDSresultMainitems) Filling DSresult mainitems from ResultSet.");
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
//                        rows++;
////                        Utils.log("(StdMasterDAO fillDSresultMainitems) mainitemField = " + mainitemField);
//                        mainitems.add(rs.getLong(mainitemField));
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
//            throw new NoDataFoundException("SQLException occured while trying to get searched records.", e);
//        } finally {
//            close();
//        }
//    }
    
	
//    /**
//     * Schliesst das Statement zum angegebenen ResultSet.
//     * Das ResultSet selbst wird dabei automatisch mit geschlossen.
//     */
//    protected void close() {
////    	Utils.log("(StdMasterDAO close) Closing Statement");
//        if (rs != null) {
//            try {
//                rs.getStatement().close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//    }
	
	
}
