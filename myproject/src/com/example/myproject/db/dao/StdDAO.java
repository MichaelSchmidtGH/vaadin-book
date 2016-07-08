package com.example.myproject.db.dao;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.example.myproject.db.dto.DSrequest;
import com.example.myproject.db.dto.DSresult;
import com.example.myproject.exceptions.NoDataFoundException;
import com.example.myproject.utils.Utils;


/**
 * Standard Master DAO.
 * Oberklasse aller DAOs.
 * 
 * @author schmidt
 * @param <T> Typ des DTO.
 */
public abstract class StdDAO<T> {
	/**
	 * Wert für den Parameter "Anzahl zu selektierende Sätze",
	 * wenn alle Sätze gelesen werden sollen. 
	 */
	protected static final int ALL_ROWS = -1;

	/**
	 * Resultset für die Datenbankabfragen.
	 */
	protected ResultSet rs = null;
	
	
    protected Object fillDTO(String className) {
    	Object dto = null;
    	try {
			Class<?> c = Class.forName(className);
			
			Constructor<?> con = c.getConstructor();
			dto = con.newInstance();

			Field[] fields = c.getDeclaredFields();
			String fieldName = null;
			
			for (Field field : fields) {
				fieldName = field.getName();
				try {
					Object value = rs.getObject(fieldName);
					field.setAccessible(true);

					if (value instanceof java.math.BigInteger) {
						long longValue = ((BigInteger) value).longValue();
						field.set(dto, longValue);
					} else {
						field.set(dto, value);
					}
				} catch (SQLException e) {
					if (!e.getSQLState().equalsIgnoreCase("S0022")) {
						Utils.log("(HostUserDAO fillDTO) SQLMessage      = " + e.getMessage());
						Utils.log("(HostUserDAO fillDTO) SQLState  		 = " + e.getSQLState());
						Utils.log("(HostUserDAO fillDTO) VendorErrorCode = " + e.getErrorCode());
						e.printStackTrace();
						throw e;
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return dto;
    }

    protected DSresult<T> fillDSresultMainitems(DSrequest request, DSresult<T> result, ResultSet rs, String mainitemField) throws NoDataFoundException {
    	Utils.log("(StdMasterDAO fillDSresultMainitems) Filling DSresult mainitems from ResultSet.");
        ArrayList<Long> mainitems = new ArrayList<Long>();
        
        int totalRows = request.getTotalRows();
        if (totalRows <= 0) {
            totalRows = Integer.MAX_VALUE;
        }
        
        try {
            if (rs != null) {
                int rows = 0;
                for (int i = 0; i < totalRows; i++) {
                    if (rs.next()) {
                        rows++;
//                        Utils.log("(StdMasterDAO fillDSresultMainitems) mainitemField = " + mainitemField);
                        mainitems.add(rs.getLong(mainitemField));
                    } else {
                        break;
                    }
                }
                result.setTotalRows(rows);
                result.setMainitems(mainitems);
                return result;
            } else {
                throw new NoDataFoundException("Resultset is null while trying to get searched records.");
            }
        } catch (SQLException e) {
        	e.printStackTrace();
            throw new NoDataFoundException("SQLException occured while trying to get searched records.", e);
        } finally {
            close();
        }
    }
    
	
    /**
     * Schliesst das Statement zum angegebenen ResultSet.
     * Das ResultSet selbst wird dabei automatisch mit geschlossen.
     */
    protected void close() {
//    	Utils.log("(StdMasterDAO close) Closing Statement");
        if (rs != null) {
            try {
                rs.getStatement().close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
	
	
}
