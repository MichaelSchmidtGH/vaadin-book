/*
 * Funktion    : Config.java
 * Titel       : Compliance Check Konfiguration
 * Erstellt    : 25.06.2005
 * Author      : CSF GmbH / MS
 * Beschreibung: Konfiguration des Compliance Check Moduls
 * Anmerkungen :
 * Parameter   : keine
 * Rückgabe    : keine
 * Aufruf      : 
 * 
 * Änderungen:
 * -----------
 * Author      :
 * Datum       :
 * Kennzeichen :
 * Beschreibung:
 * Anmerkungen :
 * Parameter   :
 * 
 */
package com.example.myproject.db.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;


/**
 * Modul		: ConfigDTO<br>
 * Erstellt		: 19.10.2012<br>
 * Beschreibung	: Konfigurationseinträge die auf Client-Seite gebraucht werden.<br>
 * 
 * @author  Schmidt
 * @version 1.0.00
 */

public class ConfigDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
    // Parameter aus der Konfigurationsdatei.ini
    private int      			logLevel					= 0;		// Log-Level 0=OFF, 1=FATAL, 2=ERROR, 3=WARN, 4=INFO, 5=DEBUG, 6=TRACE
    
    private boolean  			showLogin					= false;	// Show Login-Dialog
    private boolean	 			useLdapLogin				= false;	// Login über LDAP oder eigene AppUser-Tabelle?
    
    // Diese Maske wird nach dem Login angezeigt
    private String				defaultViewName				= null;				// Name der Default-View
    private String				defaultViewObject			= null;				// Name incl. Packages der Default-View Klasse
    private String				defaultViewIndex			= null;				// Index der Default-View im DeckPanel

    private String	 			cluster						= "02";		// Vorbelegung des Feldes user_cluster
    private ArrayList<String>	validClusters				= null;		// Liste der gültigen Clusternummern
    
    private String   			applicationEnvironment		= null;     // Ablaufumgebung "development" oder "production"
    
    private String   			deployScript				= null;     // Skript für die Stammdatenverteilung
    private String   			deployTaxScript				= null;     // Skript für die Verteilung der EMCS-Tax Stammdaten

    private boolean				enableSperrenMenuItem		= true;		// Soll der Menüpunkt für Zollstellen- und Verfahrenssperren aktiviert werden?
    private String				verteileSperrenScript		= null;     // Skript zum Verteilen von Zollstellen- und Verfahrenssperren
    private String				aktiviereSperrenScript		= null;     // Skript zum Verteilen und Aktivieren von Zollstellen- und Verfahrenssperren
    private String				aktiviereCronScript			= null;     // Skript zum Eintragen von Sperren in die Crontab
    private String				deaktiviereSperrenScript	= null;     // Skript zum Deaktivieren von Zollstellen- und Verfahrenssperren
    private String				moveParkScript				= null;     // Skript zum Verschieben der geparkten Dateien in die Arbeitsverzeichnisse 
    private String				showSperrenScript			= null;     // Skript zum Anzeigen der Sperren auf den gewählten Servern 
    private String				bereinigeCronScript			= null;		// Skript zum Löschen alter Sperren aus der Crontab 
    private String				bereinigeSperrlisten		= null;		// Skript zum Löschen alter Sperrkonfigurationen
    
    private String 				testScript			 		= null;		// Skript zum Testen
    private ArrayList<String>	testParams			 		= null;		// Parameter für das Test-Skript

    private String				dstnLockFileName		 	= null;		// Dateiname für Dienststellensperren
    private String				verfLockFileName		 	= null;		// Dateiname für Verfahrenssperren
    
    private int					clusterCount			 	= 0;		// Anzahl der Cluster-Konfigurationen (cluster.#.xxx)
    private ArrayList<String>	clusterDisplayNames		 	= null;		// Anzeigenamen der konfigurierten Cluster (cluster.#.displayName)
    private ArrayList<String>	clusterNames		 	 	= null;		// Logische Namen der konfigurierten Cluster (cluster.#.name)
    private ArrayList<String>	clusterHosts		 	 	= null;		// Hostnamen der konfigurierten Cluster (cluster.#.host)
    private ArrayList<String>	clusterUsers			 	= null;		// Benutzernamen der konfigurierten Cluster (cluster.#.user)
    private ArrayList<String[]>	clusterEnvironments		 	= null;		// Ablaufumgebungen in denen die Clusterkonfiguration angezeigt wird 
    private ArrayList<String[]>	clusterViews			 	= null;		// Views auf denen die Clusterkonfiguration angezeigt wird
    private ArrayList<String>	clusterCategories		 	= null;		// Bereich zu dem der Cluster gehört (dev, demo, prod, zoll, ...)
    private ArrayList<String>	uniqueClusterHosts		 	= null;		// Liste eindeutiger Hostnamen der konfigurierten Cluster (aus clusterHosts) 
    private String				clusterDefaultHost		 	= "cc12";	// Default Cluster Hostname
    
    private boolean				periodicAutostart		 	= false;	// Periodischen Datenabruf (z. B. bei Monitoringmasken) starten, 
    																	// sobald die Maske angezeigt wird oder manueller Start erforderlich.

//    private boolean				enableHostsListMenuItem		= true;		// Soll der Menüpunkt für die Bearbeitung der /etc/hosts aktiviert werden?
    private String				hostsListTempDir			= null;		// Verzeichnis in das die /etc/hosts zum Bearbeiten kopiert wird
    private String				hostsDirName	 		 	= null;		// Name des Verzeichnises das die Datei "hosts" enthält

    private String				sitaSucheHost				= null;		// Hostname des Servers auf dem die SITA-Suche läuft (z. Zt. cccom)

    private boolean				enableProcessStatusMenuItem	= true;		// Soll der Menüpunkt für die Anzeige der Prozessstatus aktiviert werden?
    
    private String				zollSossIP					= "172.16.6.100"; 		// IP/Name des Rechners in Sossenheim
    private String				zollQSossIP					= "192.168.174.129"; 	// IP/Name des Rechners in Sossenheim bei gesetzter Hostroute
    private String				zollKleyerIP				= "172.16.6.101";		// IP/Name des Rechners in der Kleyerstrasse
    private String				zollQKleyerIP				= "192.168.174.130";	// IP/Name des Rechners in der Kleyerstrasse bei gesetzter Hostroute
    private boolean				zollUseQSoss				= false;				// Querverbindung zum Soss-Rechner nutzen (wenn Hostroute gesetzt) 
    private boolean				zollUseQKleyer				= false;				// Querverbindung zum Kleyer-Rechner nutzen (wenn Hostroute gesetzt)
    private String				zollSossProxy				= null;					// Proxy um den Soss-Rechner zu erreichen
    private String				zollSoss1Proxy				= null;					// Proxy um den Soss1-Rechner zu erreichen
    private String				zollKleyerProxy				= null;					// Proxy um den Kleyer-Rechner zu erreichen
    private String				zollKleyer1Proxy			= null;					// Proxy um den Kleyer1-Rechner zu erreichen
    
    private boolean				jmsUseJMS           		= false; 	// Use JMS as a communication method 
    private boolean				jmsStartBroker      		= false; 	// Start an embedded JMS broker
    private HashMap<String, String> proxies 				= new HashMap<String, String>();
    
    private HashMap<String, String> hosts 					= new HashMap<String, String>();		// Einträge aus conf/hosts
    
    
    /**
     * Get the name or IP of a Proxy host for a given host name or IP.
     * The proxies can be configured by setting the parameters zoll.<host>.proxy.<environment> to a non empty string.
     * Where host can be "soss", "soss1", "kleyer" and "kleyer1". 
     *  
     * @param host IP or name of the computer for 
     * @return The proxy name or IP for the given host or null if no proxy is configured.
     */
    public String getProxyFor(String host) {
    	if (proxies.containsKey(host)) {
    		return proxies.get(host);
    	} 
    	return null;
    }

    
    
    public String getNameForIP(String ip) {
    	return hosts.get(ip);
    }
    
    public String getIpForName(String name) {
    	String[] tokens = null;
    	for (Entry<String, String> entry : hosts.entrySet()) {
    		tokens = entry.getValue().split("[ \t]+");
    		for (String token : tokens) {
    			if (token.equalsIgnoreCase(name)) {
    				return entry.getKey();
    			}
    		}
    	}
    	
    	return null;
    }
    
    
	public int getLogLevel() {
		return logLevel;
	}

	public void setLogLevel(int logLevel) {
		this.logLevel = logLevel;
	}

	public long getSerialversionuid() {
		return serialVersionUID;
	}

	public boolean isShowLogin() {
		return showLogin;
	}

	public void setShowLogin(boolean showLogin) {
		this.showLogin = showLogin;
	}

	public boolean isUseLdapLogin() {
		return useLdapLogin;
	}

	public void setUseLdapLogin(boolean useLdapLogin) {
		this.useLdapLogin = useLdapLogin;
	}

	public String getCluster() {
		return cluster;
	}

	public void setCluster(String cluster) {
		this.cluster = cluster;
	}

	public ArrayList<String> getValidClusters() {
		return validClusters;
	}

	public void setValidClusters(ArrayList<String> validClusters) {
		this.validClusters = validClusters;
	}

	public String getApplicationEnvironment() {
		return applicationEnvironment;
	}

	public void setApplicationEnvironment(String applicationEnvironment) {
		this.applicationEnvironment = applicationEnvironment;
	}

	public String getDeployScript() {
		return deployScript;
	}

	public void setDeployScript(String deployScript) {
		this.deployScript = deployScript;
	}
 
	public String getDeployTaxScript() {
		return deployTaxScript;
	}

	public void setDeployTaxScript(String deployTaxScript) {
		this.deployTaxScript = deployTaxScript;
	}

	public boolean getEnableSperrenMenuItem() {
		return enableSperrenMenuItem;
	}

	public void setEnableSperrenMenuItem(boolean enableSperrenMenuItem) {
		this.enableSperrenMenuItem = enableSperrenMenuItem;
	}
 
	public String getVerteileSperrenScript() {
		return verteileSperrenScript;
	}

	public void setVerteileSperrenScript(String verteileSperrenScript) {
		this.verteileSperrenScript = verteileSperrenScript;
	}
 
	public String getAktiviereSperrenScript() {
		return aktiviereSperrenScript;
	}

	public void setAktiviereSperrenScript(String aktiviereSperrenScript) {
		this.aktiviereSperrenScript = aktiviereSperrenScript;
	}

	public String getAktiviereCronScript() {
		return aktiviereCronScript;
	}

	public void setAktiviereCronScript(String aktiviereCronScript) {
		this.aktiviereCronScript = aktiviereCronScript;
	}

	public String getDeaktiviereSperrenScript() {
		return deaktiviereSperrenScript;
	}

	public void setDeaktiviereSperrenScript(String aktiviereSperrenScript) {
		this.deaktiviereSperrenScript = aktiviereSperrenScript;
	}

	public String getDstnLockFileName() {
		return dstnLockFileName;
	}

	public void setDstnLockFileName(String dstnLockFileName) {
		this.dstnLockFileName = dstnLockFileName;
	}

	public String getVerfLockFileName() {
		return verfLockFileName;
	}

	public void setVerfLockFileName(String verfLockFileName) {
		this.verfLockFileName = verfLockFileName;
	}

	public String getMoveParkScript() {
		return moveParkScript;
	}

	public void setMoveParkScript(String moveParkScript) {
		this.moveParkScript = moveParkScript;
	}

	public String getShowSperrenScript() {
		return showSperrenScript;
	}

	public void setShowSperrenScript(String showSperrenScript) {
		this.showSperrenScript = showSperrenScript;
	}

	public String getBereinigeCronScript() {
		return bereinigeCronScript;
	}

	public void setBereinigeCronScript(String bereinigeCronScript) {
		this.bereinigeCronScript = bereinigeCronScript;
	}

	public int getClusterCount() {
		return clusterCount;
	}

	public void setClusterCount(int clusterCount) {
		this.clusterCount = clusterCount;
	}

	public ArrayList<String> getClusterDisplayNames() {
		return clusterDisplayNames;
	}

	public void setClusterDisplayNames(ArrayList<String> clusterDisplayNames) {
		this.clusterDisplayNames = clusterDisplayNames;
	}

	public ArrayList<String> getClusterNames() {
		return clusterNames;
	}

	public void setClusterNames(ArrayList<String> clusterNames) {
		this.clusterNames = clusterNames;
	}

	public ArrayList<String> getClusterHosts() {
		return clusterHosts;
	}

	public void setClusterHosts(ArrayList<String> clusterHosts) {
		this.clusterHosts = clusterHosts;
	}

	public ArrayList<String> getClusterUsers() {
		return clusterUsers;
	}

	public void setClusterUsers(ArrayList<String> clusterUsers) {
		this.clusterUsers = clusterUsers;
	}

	public ArrayList<String[]> getClusterEnvironments() {
		return clusterEnvironments;
	}

	public void setClusterEnvironments(ArrayList<String[]> clusterEnvironments) {
		this.clusterEnvironments = clusterEnvironments;
	}

	public String getBereinigeSperrlisten() {
		return bereinigeSperrlisten;
	}

	public void setBereinigeSperrlisten(String bereinigeSperrlisten) {
		this.bereinigeSperrlisten = bereinigeSperrlisten;
	}

	public String getDefaultViewName() {
		return defaultViewName;
	}

	public void setDefaultViewName(String defaultViewName) {
		this.defaultViewName = defaultViewName;
	}

	public String getDefaultViewObject() {
		return defaultViewObject;
	}

	public void setDefaultViewObject(String defaultViewObject) {
		this.defaultViewObject = defaultViewObject;
	}

	public String getDefaultViewIndex() {
		return defaultViewIndex;
	}

	public void setDefaultViewIndex(String defaultViewIndex) {
		this.defaultViewIndex = defaultViewIndex;
	}

	public boolean isJmsUseJMS() {
		return jmsUseJMS;
	}

	public void setJmsUseJMS(boolean jmsUseJMS) {
		this.jmsUseJMS = jmsUseJMS;
	}

	public boolean isJmsStartBroker() {
		return jmsStartBroker;
	}

	public void setJmsStartBroker(boolean jmsStartBroker) {
		this.jmsStartBroker = jmsStartBroker;
	}

	public ArrayList<String> getUniqueClusterHosts() {
		return uniqueClusterHosts;
	}

	public void setUniqueClusterHosts(ArrayList<String> uniqueClusterHosts) {
		this.uniqueClusterHosts = uniqueClusterHosts;
	}

	public String getClusterDefaultHost() {
		return clusterDefaultHost;
	}

	public void setClusterDefaultHost(String clusterDefaultHost) {
		this.clusterDefaultHost = clusterDefaultHost;
	}

	public boolean isPeriodicAutostart() {
		return periodicAutostart;
	}

	public void setPeriodicAutostart(boolean periodicAutostart) {
		this.periodicAutostart = periodicAutostart;
	}

//	public boolean isEnableHostsListMenuItem() {
//		return enableHostsListMenuItem;
//	}

//	public void setEnableHostsListMenuItem(boolean enableHostsListMenuItem) {
//		this.enableHostsListMenuItem = enableHostsListMenuItem;
//	}

	public String getHostsListTempDir() {
		return hostsListTempDir;
	}

	public void setHostsListTempDir(String hostsListTempDir) {
		this.hostsListTempDir = hostsListTempDir;
	}

	public String getHostsDirName() {
		return hostsDirName;
	}

	public void setHostsDirName(String hostsDirName) {
		this.hostsDirName = hostsDirName;
	}

	public ArrayList<String[]> getClusterViews() {
		return clusterViews;
	}

	public void setClusterViews(ArrayList<String[]> clusterViews) {
		this.clusterViews = clusterViews;
	}

	public String getSitaSucheHost() {
		return sitaSucheHost;
	}

	public void setSitaSucheHost(String sitaSucheHost) {
		this.sitaSucheHost = sitaSucheHost;
	}

	public boolean isEnableProcessStatusMenuItem() {
		return enableProcessStatusMenuItem;
	}

	public void setEnableProcessStatusMenuItem(boolean enableProcessStatusMenuItem) {
		this.enableProcessStatusMenuItem = enableProcessStatusMenuItem;
	}

	public ArrayList<String> getClusterCategories() {
		return clusterCategories;
	}

	public void setClusterCategories(ArrayList<String> clusterCategories) {
		this.clusterCategories = clusterCategories;
	}

	public String getZollSossIP() {
		return zollSossIP;
	}

	public void setZollSossIP(String zollSossIP) {
		this.zollSossIP = zollSossIP;
	}

	public String getZollQSossIP() {
		return zollQSossIP;
	}

	public void setZollQSossIP(String zollQSossIP) {
		this.zollQSossIP = zollQSossIP;
	}

	public String getZollKleyerIP() {
		return zollKleyerIP;
	}

	public void setZollKleyerIP(String zollKleyerIP) {
		this.zollKleyerIP = zollKleyerIP;
	}

	public String getZollQKleyerIP() {
		return zollQKleyerIP;
	}

	public void setZollQKleyerIP(String zollQKleyerIP) {
		this.zollQKleyerIP = zollQKleyerIP;
	}

	public boolean isZollUseQSoss() {
		return zollUseQSoss;
	}

	public void setZollUseQSoss(boolean zollUseQSoss) {
		this.zollUseQSoss = zollUseQSoss;
	}

	public boolean isZollUseQKleyer() {
		return zollUseQKleyer;
	}

	public void setZollUseQKleyer(boolean zollUseQKleyer) {
		this.zollUseQKleyer = zollUseQKleyer;
	}

	public String getZollSossProxy() {
		return zollSossProxy;
	}

	public void setZollSossProxy(String zollSossProxy) {
		this.zollSossProxy = zollSossProxy;
	}

	public String getZollSoss1Proxy() {
		return zollSoss1Proxy;
	}

	public void setZollSoss1Proxy(String zollSoss1Proxy) {
		this.zollSoss1Proxy = zollSoss1Proxy;
	}

	public String getZollKleyerProxy() {
		return zollKleyerProxy;
	}

	public void setZollKleyerProxy(String zollKleyerProxy) {
		this.zollKleyerProxy = zollKleyerProxy;
	}

	public String getZollKleyer1Proxy() {
		return zollKleyer1Proxy;
	}

	public void setZollKleyer1Proxy(String zollKleyer1Proxy) {
		this.zollKleyer1Proxy = zollKleyer1Proxy;
	}


	public HashMap<String, String> getProxies() {
		return proxies;
	}


	public void setProxies(HashMap<String, String> proxies) {
		this.proxies = proxies;
	}


	public HashMap<String, String> getHosts() {
		return hosts;
	}


	public void setHosts(HashMap<String, String> hosts) {
		this.hosts = hosts;
	}



	public String getTestScript() {
		return testScript;
	}



	public void setTestScript(String testScript) {
		this.testScript = testScript;
	}



	public ArrayList<String> getTestParams() {
		return testParams;
	}



	public void setTestParams(ArrayList<String> testParams) {
		this.testParams = testParams;
	}

}
