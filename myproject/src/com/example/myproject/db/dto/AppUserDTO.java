package com.example.myproject.db.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * Data Transfer Object für Tabelle "appUser".
 * Erstellt: 13.08.2012
 * 
 * @author schmidt
 * @version 1.0.00
 */
public class AppUserDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    
    // Dialog- und Datenbank-Felder
    private long      mainitem     = 0L;
	private String    user         = null;
	private String    hash         = null;
	private boolean   active       = false;
	private boolean   admin        = false;
	private boolean   readOnly     = false;
    private String    status       = null;
	private Timestamp edat         = null;
    private Timestamp adat         = null;
    private String    sb           = null;
    // Nur Dialog-Felder
    private String    passwort     = null;

    
    // Suchfelder als Strings
    private String    strMainitem  = null;
    private String    strUser      = null;
    private String    strHash      = null;
    private String    strActive    = null;
    private String    strAdmin     = null;
    private String    strReadOnly  = null;
    private String    strStatus    = null;
    private String    strEdat      = null;
    private String    strAdat      = null;
    private String    strSb        = null;
	
    
	public AppUserDTO() {
	}
	
	public AppUserDTO(String user, boolean active, boolean admin, boolean readOnly) {
		this.user     = user;
		this.active   = active;
		this.admin    = admin;
		this.readOnly = readOnly;
	}

	
	public static ArrayList<AppUserDTO> createTestRecords(int amount, String userPrefix) {
		ArrayList<AppUserDTO> dtoList = new ArrayList<AppUserDTO>();
		
		for (int i = 0; i < amount; i++) {
			AppUserDTO dto = new AppUserDTO();
			dto.setUser(userPrefix + i);
			dto.setHash("hash" + i);
			dto.setActive(true);
			dto.setAdmin(true);
			dto.setReadOnly(true);
		    dto.setStatus("A");
		    Timestamp ts = new Timestamp(System.currentTimeMillis());
		    ts.setNanos(0);
			dto.setEdat(ts);
			dto.setAdat(ts);
		    dto.setSb("TEST");
		    dtoList.add(dto);
		}
		
		return dtoList;
	}
	
	
	
	
	/* *************************************************************************************
	 * Getter und Setter
	 * *************************************************************************************/
    public long getMainitem() {
        return mainitem;
    }

    public void setMainitem(long mainitem) {
        this.mainitem = mainitem;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getEdat() {
        return edat;
    }

    public void setEdat(Timestamp edat) {
        this.edat = edat;
    }

    public Timestamp getAdat() {
        return adat;
    }

    public void setAdat(Timestamp adat) {
        this.adat = adat;
    }

    public String getSb() {
        return sb;
    }

    public void setSb(String sb) {
        this.sb = sb;
    }

    public String getStrMainitem() {
        return strMainitem;
    }

    public void setStrMainitem(String strMainitem) {
        this.strMainitem = strMainitem;
    }

    public String getStrUser() {
        return strUser;
    }

    public void setStrUser(String strUser) {
        this.strUser = strUser;
    }

    public String getStrHash() {
        return strHash;
    }

    public void setStrHash(String strHash) {
        this.strHash = strHash;
    }

    public String getStrActive() {
        return strActive;
    }

    public void setStrActive(String strActive) {
        this.strActive = strActive;
    }

    public String getStrStatus() {
        return strStatus;
    }

    public void setStrStatus(String strStatus) {
        this.strStatus = strStatus;
    }

    public String getStrEdat() {
        return strEdat;
    }

    public void setStrEdat(String strEdat) {
        this.strEdat = strEdat;
    }

    public String getStrAdat() {
        return strAdat;
    }

    public void setStrAdat(String strAdat) {
        this.strAdat = strAdat;
    }

    public String getStrSb() {
        return strSb;
    }

    public void setStrSb(String strSb) {
        this.strSb = strSb;
    }

	public String getPasswort() {
		return passwort;
	}

	public void setPasswort(String passwort) {
		this.passwort = passwort;
	}

	public Boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public String getStrAdmin() {
		return strAdmin;
	}

	public void setStrAdmin(String strAdmin) {
		this.strAdmin = strAdmin;
	}

	public Boolean isReadOnly() {
		return readOnly;
	}

	public void setReadOnly(boolean radOnly) {
		this.readOnly = radOnly;
	}

	public String getStrReadOnly() {
		return strReadOnly;
	}

	public void setStrReadOnly(String strReadOnly) {
		this.strReadOnly = strReadOnly;
	}

}
