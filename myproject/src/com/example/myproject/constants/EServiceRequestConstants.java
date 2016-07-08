package com.example.myproject.constants;

/**
 * Constants for type parameters of requested services.<br>
 * 
 * @author schmidt
 * @version 1.0.00
 * @see #EXPORT_APP_USER
 * @see #EXPORT_APP_USER_LIST
 * @see #EXPORT_KUNDEN_IDS
 * @see #EXPORT_MASTER_LIST
 * @see #EXPORT_S2M
 * @see #EXPORT_TAX
 * @see #EXPORT_TAX_LIST
 * @see #EXPORT_USER
 * @see #EXPORT_USER_LIST
 * @see #EXPORT_ZOLL
 * @see #EXPORT_ZOLL_LIST
 * @see #EXPORT_ZONR
 * @see #EXPORT_ZONR_LIST
 * @see #EXPORT_SELECTED
 * @see #EXPORT_ALL
 * @see #EXPORT_AS_TEXT
 * @see #EXPORT_AS_HTML
 */
public enum EServiceRequestConstants {
	
	// What data to export
	/**
	 * Request type to export application user records.
	 */
	EXPORT_APP_USER("EXPORT_APP_USER"),

	/**
	 * Request type to export appUser list records.
	 */
	EXPORT_APP_USER_LIST("EXPORT_APP_USER_LIST"),
	
	/**
	 * Request type to export KundenIds.  
	 */
	EXPORT_KUNDEN_IDS("EXPORT_KUNDEN_IDS"),
	
	/**
	 * Request type to export master records of user table.
	 */
	EXPORT_MASTER_LIST("EXPORT_MASTER_LIST"),

	/**
	 * Request type to export master records of user table.
	 */
	EXPORT_MASTER_HOST_LIST("EXPORT_MASTER_HOST_LIST"),

	/**
	 * Request type to export list of KdVerfNagruDTOs.
	 * Kunden - Verfahren - Nachrichtengruppen.
	 */
	EXPORT_KUNDEN_VERF_NAGRUS("EXPORT_KUNDEN_VERF_NAGRUS"),
	
	/**
	 * Request type to export list of KdnagruDTOs.
	 * Kunden - Nachrichtengruppen.
	 */
	EXPORT_KUNDEN_NAGRUS("EXPORT_KUNDEN_NAGRUS"),
	
	/**
	 * Request type to export s2m records.
	 */
	EXPORT_S2M("EXPORT_S2M"),
	
	/**
	 * Request type to export tax records.
	 */
	EXPORT_TAX("EXPORT_TAX"),

	/**
	 * Request type to export tax list records.
	 */
	EXPORT_TAX_LIST("EXPORT_TAX_LIST"),
	
	/**
	 * Request type to export user records.
	 */
	EXPORT_USER("EXPORT_USER"),

	/**
	 * Request type to export user list records.
	 */
	EXPORT_USER_LIST("EXPORT_USER_LIST"),

	/**
	 * Request type to export zol records.
	 */
	EXPORT_ZOLL("EXPORT_ZOLL"),

	/**
	 * Request type to export zol list records.
	 */
	EXPORT_ZOLL_LIST("EXPORT_ZOLL_LIST"),

	/**
	 * Request type to export zonr records.
	 */
	EXPORT_ZONR("EXPORT_ZONR"),

	/**
	 * Request type to export zonr list records.
	 */
	EXPORT_ZONR_LIST("EXPORT_ZONR_LIST"),

	/**
	 * Request type to export list of currently active customers.
	 */
	EXPORT_WHO_WORK_U_LIST("WHO_WORK_U_LIST"),

	/**
	 * Request type to export list of currently active customs offices.
	 */
	EXPORT_WHO_WORK_Z_LIST("WHO_WORK_Z_LIST"),
	
	/**
	 * Request type to export list of scheduler queue items.
	 */
	EXPORT_SCHEDULER_QUEUE_LIST("SCHEDULER_QUEUE_LIST"),
	
	/**
	 * Request type to export list of scheduler user status items.
	 */
	EXPORT_SCHEDULER_USER_LIST("SCHEDULER_USER_LIST"),
	
	/**
	 * Request type to export user data from shared memory.
	 * Export user data and send error list of ShowUserDTO.
	 */
	EXPORT_SHOW_USER_ALL("SHOW_USER_ALL"),
	/**
	 * Request type to export user data from shared memory.
	 * Export only user data of ShowUserDTO.
	 */
	EXPORT_SHOW_USER_DATA("SHOW_USER_DATA"),
	/**
	 * Request type to export user data from shared memory.
	 * Export only send error list of ShowUserDTO.
	 */
	EXPORT_SHOW_USER_LIST("SHOW_USER_LIST"),
	
	/**
	 * Request type to export customs office data from shared memory.
	 * Export data of ShowZollDTO.
	 */
	EXPORT_SHOW_ZOLL("SHOW_ZOLL"),

	/**
	 * Request type to export customs office data from shared memory.
	 * Export data of ShowZollListDTO.
	 */
	EXPORT_SHOW_ZOLL_LIST("SHOW_ZOLL_LIST"),

	/**
	 * Request type to export showLast monitoring data from shared memory.
	 * Export data of ShowLastDTO.
	 */
	EXPORT_SHOW_LAST("SHOW_LAST"),

	/**
	 * Request type to export showLast customs offices alarm list monitoring data from shared memory.
	 * Export data of ShowLastErrorDTO.
	 */
	EXPORT_SHOW_LAST_ALARM("SHOW_LAST_ALARM"),

	/**
	 * Request type to export prozesse records.
	 * Export data of ProzessDTO.
	 */
	EXPORT_PROZESSE("PROZESSE"),
	
	/**
	 * Request type to export prozesse records of ProcessListView.
	 * Export data of ProzessDTO.
	 */
	EXPORT_PROZESSE_LIST("PROZESSE_LIST"),
	
	/**
	 * Request type to export hostUser records.
	 */
	EXPORT_HOST_USER("EXPORT_HOST_USER"),

	/**
	 * Request type to export hostUser list records.
	 */
	EXPORT_HOST_USER_LIST("EXPORT_HOST_USER_LIST"),
	
	
	
	// Amount to export
	/**
	 * Request type to export all records.
	 */
	EXPORT_ALL("EXPORT_ALL"), 

	/**
	 * Request type to export selected records.  
	 */
	EXPORT_SELECTED("EXPORT_SELECTED"), 

	/**
	 * Request type to export current record.  
	 */
	EXPORT_CURRENT("EXPORT_CURRENT"), 
	
	
	// Format to export
	/**
	 * Request type to export a list into a HTML file.
	 */
	EXPORT_AS_HTML("EXPORT_AS_HTML"),
	
	/**
	 * Request type to export a list into a text file.  
	 */
	EXPORT_AS_TEXT("EXPORT_AS_TEXT"), 
	
	/**
	 * Request type to export a list into a EXCEL file.  
	 */
	EXPORT_AS_EXCEL("EXPORT_AS_EXCEL"), 
	
	
	// Export only selected columns? 
	/**
	 *  Columns to export have been selected.
	 */
	EXPORT_SELECTED_COLUMNS("EXPORT_SELECTED_COLUMNS");
	
	
	
	
	private EServiceRequestConstants(String name) { 
		this.name = name;
	}
	
	private String name = null;
	
	public String getName() {
		return name;
	}
	
	
}
