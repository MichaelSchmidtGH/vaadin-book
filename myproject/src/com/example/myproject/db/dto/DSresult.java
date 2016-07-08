package com.example.myproject.db.dto;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Data Transfer Object for DataAccessService.
 * 
 * @author schmidt
 * @version 1.0.00
 * @param <T> Typ des DTOs.
 */
public class DSresult<T> implements Serializable {
	private static final long serialVersionUID = 1L;

	private boolean			  				success			 = true;
    private int 			  				fromRow 		 = 0;
    private int 			  				toRow 			 = 0;
    private int 			  				totalRows 		 = 0;
    private String 			  				order			 = null;
	private ArrayList<Long>	  				mainitems     	 = null;

    private ArrayList<T>					resultDTOs 		 = null;

    private ArrayList<String> 				resultStrings	 = null;

    public DSresult() {
    }
    
    public int getFromRow() {
        return fromRow;
    }

    public void setFromRow(int fromRow) {
        this.fromRow = fromRow;
    }

    public int getToRow() {
        return toRow;
    }

    public void setToRow(int toRow) {
        this.toRow = toRow;
    }

    public int getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(int totalRows) {
        this.totalRows = totalRows;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

	public ArrayList<Long> getMainitems() {
		return mainitems;
	}

	public void setMainitems(ArrayList<Long> mainitems) {
		this.mainitems = mainitems;
	}

	public ArrayList<String> getResultStrings() {
		return resultStrings;
	}

	public void setResultStrings(ArrayList<String> resultStrings) {
		this.resultStrings = resultStrings;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public ArrayList<T> getResultDTOs() {
		return resultDTOs;
	}

	public void setResultDTOs(ArrayList<T> resultDTOs) {
		this.resultDTOs = resultDTOs;
	}

}
