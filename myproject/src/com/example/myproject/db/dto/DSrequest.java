package com.example.myproject.db.dto;

import java.io.Serializable;
import java.util.ArrayList;

import com.example.myproject.constants.EServiceRequestConstants;

/**
 * Data Set Request object.
 * Value object to transfer date for a request to server data access services. 
 * 
 * @author schmidt
 * @version 1.0.00
 *
 */
public class DSrequest implements Serializable {
	private static final long serialVersionUID = 1L;
		
    private int 								fromRow 					= 0;
    private int 								toRow 						= 0;
    private int 								totalRows 					= 0;
    private String 								order 						= null;		// Value of SQL "Order by" clause
    private boolean 							isAscending					= true;		// Is the sort order ascending or descending?
    private long 								id 							= 0L;
    private boolean 							active 						= true;		// Read active records
    private boolean 							history 					= false;	// Read history records
    private boolean 							deleted 					= false;	// Read deleted records
    private boolean 							asp 						= true;		// Read ASP/SAAS customer records
    private boolean 							net 						= true;		// Read Atlas-Net customer records
    private boolean 							inh 						= true;		// Read Inhouse customer records
    private EServiceRequestConstants 			exportType 		= null;		// Parameter for ExportService: What to export (user ids, users, ...)? 
    																		// Constants can be found in shared/constants/EServiceRequestConstants
    private EServiceRequestConstants 			exportAmount 	= null;		// Parameter for ExportService: What amount to export (all, seleted)? 
    																		// Constants can be found in shared/constants/ServiceRequestConstants
    private EServiceRequestConstants 			exportFormat 	= null;		// Parameter for ExportService: What format to export (text, HTML)? 
																			// Constants can be found in shared/constants/ServiceRequestConstants
    private ArrayList<String> 					exportColumns				= null;		// List of column names to show up in the export result
    private ArrayList<AppUserDTO>				appUserExportData			= null;		// Parameter for ExportService: appUser DTOs to export.
    private boolean								exportDataSet				= false;	// Must be true if one of the ...ExportData are used, else false
    
    private AppUserDTO 							appUserDto 					= null;
    
    private ArrayList<Long>   					mainitems  					= null;
    private ArrayList<String> 					stringParams				= null;


	public DSrequest() {
    }
    
    public DSrequest(int fromRow, int toRow, int totalRows, String order) {
        this.fromRow = fromRow;
        this.toRow = toRow;
        this.totalRows = totalRows;
        this.order = order;
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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

    public AppUserDTO getAppUserDto() {
        return appUserDto;
    }

    public void setAppUserDto(AppUserDTO appUserDto) {
        this.appUserDto = appUserDto;
    }

    public ArrayList<Long> getMainitems() {
        return mainitems;
    }

    public void setMainitems(ArrayList<Long> mainitems) {
        this.mainitems = mainitems;
    }

	public ArrayList<String> getStringParams() {
		return stringParams;
	}

	public void setStringParams(ArrayList<String> stringParams) {
		this.stringParams = stringParams;
	}

	public String getStringParam(int index) {
		return stringParams.get(index);
	}

	public void addStringParam(String param) {
//		GWT.log("(DSrequest addStringParam) param = " + param);
//		GWT.log("(DSrequest addStringParam) stringParams = " + this.stringParams);
		if (stringParams == null) {
			stringParams = new ArrayList<String>();
		}
		this.stringParams.add(param);
	}

//    public ArrayList<Object> getObjectParams() {
//		return objectParams;
//	}
//
//	public void setObjectParams(ArrayList<Object> objectParams) {
//		this.objectParams = objectParams;
//	}
//
//	public Object getObjectParam(int index) {
//		return objectParams.get(index);
//	}
//
//	public void addObjectParam(Object param) {
//		if (objectParams == null) {
//			objectParams = new ArrayList<Object>();
//		}
//		this.objectParams.add(param);
//	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isHistory() {
		return history;
	}

	public void setHistory(boolean history) {
		this.history = history;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}



	/**
	 * Parameter for ExportService: What format to export (text, HTML)?<br>
	 * Constants can be found in {@link de.kewill.ccwebui.shared.constants.ServiceRequestConstants ServiceRequestConstants}.
	 * 
	 * @param exportFormat 
	 * @see ServiceRequestConstants
	 */
	public void setExportFormat(EServiceRequestConstants exportFormat) {
		this.exportFormat = exportFormat;
	}
	public EServiceRequestConstants getExportFormat() {
		return exportFormat;
	}


	/**
	 * Parameter for ExportService: What amount to export (all, seleted)?<br>
	 * Constants can be found in {@link de.kewill.ccwebui.shared.constants.ServiceRequestConstants ServiceRequestConstants}.
	 * 
	 * @param exportAmount 
	 * @see de.kewill.ccwebui.shared.constants.ServiceRequestConstants
	 */
	public void setExportAmount(EServiceRequestConstants exportAmount) {
		this.exportAmount = exportAmount;
	}
	public EServiceRequestConstants getExportAmount() {
		return exportAmount;
	}


	/**
	 * Parameter for ExportService: What to export (user ids, users, ...)?<br>
	 * Constants can be found in {@link de.kewill.ccwebui.shared.constants.EServiceRequestConstants ServiceRequestConstants}.
	 * 
	 * @param exportType 
	 * @see de.kewill.ccwebui.shared.constants.EServiceRequestConstants
	 */
	public void setExportType(EServiceRequestConstants exportType) {
		this.exportType = exportType;
	}
	public EServiceRequestConstants getExportType() {
		return exportType;
	}
	
	/**
	 * Parameter for ExportService: List of columns names of the database table to export.<br>
	 *  
	 * @param exportColumns	List of column names 
	 * @see de.kewill.ccwebui.shared.constants.EServiceRequestConstants
	 */
	public void setExportColumns(ArrayList<String> exportColumns) {
		this.exportColumns = exportColumns;
	}
	public ArrayList<String> getExportColumns() {
		return exportColumns;
	}
	
	public ArrayList<AppUserDTO> getAppUserExportData() {
		return appUserExportData;
	}

	public void setAppUserExportData(ArrayList<AppUserDTO> appUserExportData) {
		this.appUserExportData = appUserExportData;
	}


	public boolean isExportDataSet() {
		return exportDataSet;
	}

	public void setExportDataSet(boolean exportDataSet) {
		this.exportDataSet = exportDataSet;
	}

	public boolean isAscending() {
		return isAscending;
	}

	public void setAscending(boolean isAscending) {
		this.isAscending = isAscending;
	}

	public boolean isAsp() {
		return asp;
	}

	public void setAsp(boolean asp) {
		this.asp = asp;
	}

	public boolean isNet() {
		return net;
	}

	public void setNet(boolean net) {
		this.net = net;
	}

	public boolean isInh() {
		return inh;
	}

	public void setInh(boolean inh) {
		this.inh = inh;
	}

}
