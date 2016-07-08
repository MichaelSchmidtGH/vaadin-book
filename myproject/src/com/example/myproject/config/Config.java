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
package com.example.myproject.config;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import com.example.myproject.exceptions.ConfigException;
import com.example.myproject.utils.Prop;
import com.example.myproject.utils.Utils;


/**
 * Konfigurationsparameter auf der Server-Seite.
 * 
 * @author  MS
 * @version 1.0.00
 */

public final class Config { 
    private static String 				iniPath             	 = "conf";		  		// Pfad zur INI-Datei
    private static String 				iniFile             	 = "ccwebui.ini";    	// Name der INI-Datei
    private static String 				iniFileEnvironment     	 = "environment.ini";   // Name der INI-Datei für das Application-Environment
    
    // Parameter aus der Konfigurationsdatei.ini
    private static boolean  			showLogin         		 = false;		  	// LoginDialog anzeigen?
    private static boolean				useLdapLogin      		 = false;			// Login über LDAP oder eigene AppUser-Tabelle?
    private static boolean				checkDirectories		 = true;			// Verzeichnise auf Existenz prüfen und ggfls. anlegen?
    private static boolean				checkSperrParams		 = true;			// Parameter fuer ZollStellen- und Verfahrenssperren prüfen?
    private static boolean				checkHostsParams		 = true;			// Parameter fuer /etc/hosts prüfen?
    private static boolean				checkClusterParams		 = true;			// Parameter fuer Cluster prüfen?
    private static boolean				checkTimesParams		 = true;			// Parameter fuer Durchsatzstatitik prüfen?
    private static boolean				checkDeployParams		 = true;			// Parameter fuer Stammdatenverteilung prüfen?
    private static boolean				checkDatabaseParams		 = true;			// Parameter fuer Datenbanken prüfen?

    private static String   			applicationId     		 = null;		  	// Kennung der Anwendung
    private static String   			applicationLocale 		 = null;         	// Eine Locale im Format xx_XX. Z.B. de_DE.
    private static String   			applicationEnvironment	 = null;         	// Ablaufumgebung "development" oder "production"
    private static boolean 				isEclipse 			     = true;
    private static boolean 				isDevelopment		     = false;
    private static boolean 				isProduction		   	 = false;
    private static boolean  			logDebug          		 = true;		  // Log-Meldungen auf Stdout ausgeben
    private static int      			logLevel          		 = 0;			  // Log-Level
    private static boolean  			logSQL            		 = false;		  // SQL Anfragen loggen
    private static boolean  			logJMS            		 = false;		  // JMS Nachrichteninhalte loggen
    private static boolean  			logDriverManager		 = false;		  // DriverManager Ausgaben loggen
    
    // Diese Maske wird nach dem Login angezeigt
    private static String				defaultViewName			 = null;				// Name der Default-View
    private static String				defaultViewObject		 = null;				// Name incl. Packages der Default-View Klasse
    private static String				defaultViewIndex		 = null;				// Index der Default-View im DeckPanel
    
    // Mail-Parameter
    private static String   			mailHost     			 = null;            // Mail-Host                    
    private static String   			mailAltHost  			 = null;            // Alternativer Mail-Host                    
    private static String   			mailFrom     			 = null;            // Absender-Adresse
    private static String   			mailFromName 			 = null;            // Name des Absenders
    private static String   			mailSubject  			 = null;            // Subject für Fehlermails
    private static String   			mailTo       			 = null;            // Empf�nger-Adressen
    private static String   			mailCc       			 = null;            // CC-Adressen
    private static String   			mailBcc      			 = null;            // BCC-Adressen
    private static boolean  			mailDebug    			 = false;           // Mail-API Debug-Ausgaben?
    
    // Datenbank-Parameter
    private static ArrayList<String>	dbtype          		 = null;         	// Datenbank-Typ (MySql, Oracle, ...)
    private static ArrayList<String>	dbuser          		 = null;         	// Datenbank-User
    private static ArrayList<String>   	dbpasswd        		 = null;         	// Datenbank-User-Passwort
    private static ArrayList<String>   	dbdriver        		 = null;         	// Datenbank-Treiber
    private static ArrayList<String>   	dbconnect       		 = null;         	// Datenbank-Connectstring
    private static String   			dbPrefetchSize  		 = null;        	// Datenbank-Prefetchsize
    private static boolean  			dbSetQueryTimeout 		 = false;      		// Soll ein Timeout für Datenbankstatements gesetzt werden?
    private static int      			dbQueryTimeout  		 = 0;            	// Timeout für Datenbankstatements in Sekunden
    private static ArrayList<String>	databases				 = null;			// Liste mit physikalischen Datenbanknamen
    private static int					numberOfDatabases		 = 0;				// Anzahl der Datenbanken
    private static ArrayList<String>	dbname					 = null;			// Liste mit logischen Datenbanknamen
    private static HashMap<String, Integer>	dbindex				 = null;			// Index-Nummern der Datenbanken, Key = logischer Datenbankname
    
    // LDAP-Parameter
    private static String				ldapDomain			  	 = "csf.local";		 // LDAP Domain
    private static String				ldapDomainController1 	 = "dc01.csf.local"; // Erster LDAP Domain Controller URL (ohne Protokoll (LDAP://))
    private static String				ldapDomainController2	 = "dc02.csf.local"; // Fallback LDAP Domain Controller URL (ohne Protokoll (LDAP://))
    
    // Cluster-Parameter
    private static String				cluster					 = "02";			// Vorbelegung für Feld user_cluster
    private static ArrayList<String>	validClusters			 = null;			// Liste der gültigen Clusternummern
    
    // Skripte für Stammdatenverteilung und Sperren
    private static String 				deployScript			 = null;			// Skript zur Stammdatenverteilung
    private static String				deployTaxScript			 = null;        	// Skript für die Verteilung der EMCS-Tax Stammdaten
    private static String				unloadTimesScript		 = null;        	// Skript zum Entaden der *_poll_dat Timestamps aus hsnd bzw. hrec
    private static boolean				enableSperrenMenuItem	 = true;			// Soll der Menüpunkt für Sperren aktiviert werden?
    private static String				verteileSperrenScript	 = null;        	// Skript zum Verteilen von Zollstellen- und Verfahrenssperren
    private static String				aktiviereSperrenScript	 = null;        	// Skript zum Verteilen und Aktivieren von 
    																				// 			  Zollstellen- und Verfahrenssperren
    private static String				aktiviereCronScript		 = null;    		// Skript zum Eintragen von Sperren in die Crontab
    private static String				deaktiviereSperrenScript = null;        	// Skript zum Deaktivieren von Zollstellen- und Verfahrenssperren
    private static String				moveParkScript 			 = null;        	// Skript zum Verschieben der geparkten Dateien in die 
    																				// Arbeitsverzeichnisse nach dem Beeenden aller Sperren
    private static String				showSperrenScript		= null;				// Skript zum Anzeigen der Sperren auf den gewählten Servern 
    private static String				bereinigeCronScript		= null;				// Skript zum Löschen alter Sperren aus der Crontab 
    private static String				bereinigeSperrlisten	= null;				// Skript zum Löschen alter Sperrkonfigurationen 

    private static String 				testScript			 	= null;				// Skript zum Testen
    private static ArrayList<String>	testParams			 	= null;				// Parameter für das Test-Skript
    
    // Verzeichnisse
    private static String				tempDirName				 = null;			// Verzeichnisname für temporäre Dateien
    private static File					tempDir					 = null;			// Verzeichnis für temporäre Dateien
    
    private static String				dstnLockFileName		 = null;			// Dateiname       für Dienststellensperren
    private static String				dstnLockDirName			 = null;			// Verzeichnisname für Dienststellensperren
    private static File					dstnLockDir				 = null;			// Verzeichnis     für Dienststellensperren
    private static String				verfLockFileName		 = null;			// Dateiname 	   für Verfahrenssperren
    private static String				verfLockDirName			 = null;			// Verzeichnisname für Verfahrenssperren
    private static File					verfLockDir				 = null;			// Verzeichnis     für Verfahrenssperren
    
    // Parameter der Clearing-Center-Cluster
    private static int					clusterCount			 = 0;				// Anzahl der Cluster-Konfigurationen (cluster.#.xxx)
    private static ArrayList<String>	clusterDisplayNames		 = null;			// Anzeigenamen der konfigurierten Cluster (cluster.#.displayName)
    private static ArrayList<String>	clusterNames		 	 = null;			// Logische Namen der konfigurierten Cluster (cluster.#.name)
    private static ArrayList<String>	clusterHosts		 	 = null;			// Hostnamen der konfigurierten Cluster (cluster.#.host)
    private static ArrayList<String>	clusterUsers			 = null;			// Benutzernamen der konfigurierten Cluster (cluster.#.user)
    private static ArrayList<String[]>	clusterEnvironments		 = null;			// Ablaufumgebungen in denen die Clusterkonfiguration angezeigt wird 
    private static ArrayList<String[]>	clusterViews			 = null;			// Views auf denen die Clusterkonfiguration angezeigt wird 
    private static ArrayList<String>	clusterCategories	 	 = null;			// Bereich zu dem der Cluster gehört (dev, demo, prod, zoll, ...)
    																				// wird (cluster.#.environments)
    private static ArrayList<String>	uniqueClusterHosts		 = null;			// Liste eindeutiger Hostnamen der konfigurierten Cluster 
    																				// (Subset von clusterHosts) 
    private static String				clusterDefaultHost		 = "cc12";			// Default Cluster Hostname
    
    private static boolean				periodicAutostart		 = false;			// Periodischen Datenabruf (z. B. bei Monitoringmasken) starten, 
    																				// sobald die Maske angezeigt wird oder manueller Start erforderlich.
//  private static boolean				enableHostsListMenuItem	 = true;			// Soll der Menüpunkt für die Bearbeitung der /etc/hosts aktiviert werden?
    private static String				hostsListTempDirName	 = null;			// Name des Verzeichnises in das die /etc/hosts zum Bearbeiten kopiert wird
    private static File					hostsListTempDir		 = null;			// Verzeichnis in das die /etc/hosts zum Bearbeiten kopiert wird
    private static String				hostsDirName	 		 = null;			// Name des Verzeichnises das die Datei "hosts" enthält

    private static String				sitaSucheHost			 = null;			// Hostname des Servers auf dem die SITA-Suche läuft (z. Zt. cccom)
    private static String				sitaSucheSkript			 = null;			// Skript, welches die Suche in den SITA-Dateien vornimmt
    private static String				sitaFilesList			 = null;			// Liste mit von der SITA-Suche gefundenen Dateien

    private static boolean				enableProcessStatusMenuItem	= true;			// Soll der Menüpunkt für die Anzeige der Prozessstatus aktiviert werden?

    private static String				zollSossIP				= "172.16.6.100"; 		// IP/Name des Rechners in Sossenheim
    private static String				zollQSossIP				= "192.168.174.129"; 	// IP/Name des Rechners in Sossenheim bei gesetzter Hostroute
    private static String				zollKleyerIP			= "172.16.6.101";		// IP/Name des Rechners in der Kleyerstrasse
    private static String				zollQKleyerIP			= "192.168.174.130";	// IP/Name des Rechners in der Kleyerstrasse bei gesetzter Hostroute
    private static boolean				zollUseQSoss			= false;				// Querverbindung zum Soss-Rechner nutzen (wenn Hostroute gesetzt) 
    private static boolean				zollUseQKleyer			= false;				// Querverbindung zum Kleyer-Rechner nutzen (wenn Hostroute gesetzt)
    private static String				zollSossProxy			= null;					// Proxy um den Soss-Rechner zu erreichen
    private static String				zollQSossProxy			= null;					// Proxy um den QSoss-Rechner zu erreichen
    private static String				zollSoss1Proxy			= null;					// Proxy um den Soss1-Rechner zu erreichen
    private static String				zollKleyerProxy			= null;					// Proxy um den Kleyer-Rechner zu erreichen
    private static String				zollQKleyerProxy		= null;					// Proxy um den QKleyer-Rechner zu erreichen
    private static String				zollKleyer1Proxy		= null;					// Proxy um den Kleyer1-Rechner zu erreichen
    private static HashMap<String, String> proxies 				= new HashMap<String, String>();
    
    // Parameter des aktuellen Benutzers
//    private static AppUserDTO		currentUserDTO        = new AppUserDTO("schmidt", true, true, false);
    private static String  			currentUser           = "schmidt";
    private static boolean 			currentUserIsAdmin 	  = true;
    private static boolean 			currentUserIsReadOnly = false;

    
    // Parameter fuer JMS
    private static boolean           jmsUseJMS           = false; // Use JMS as a communication method 
    private static boolean           jmsStartBroker      = false; // Start an embedded JMS broker
    private static int               jmsConsumerThreads  = 1;     // Number of JMS comnsumer thread to start 
    private static int               jmsMaxTimeout       = 10;    // Maximum time in seconds for all queues to shutdown 
    private static ArrayList<String> jmsInboundQueues    = null;  // Namen der JMS Eingangsqueues
    private static ArrayList<String> jmsInboundUrls      = null;  // URLs der JMS Broker der jew. Eingangsqueue
    private static ArrayList<String> jmsInboundUsers     = null;  // Usernamen der JMS Broker der jew. Eingangsqueue
    private static ArrayList<String> jmsInboundPasswords = null;  // Passwörter der JMS Broker der jew. Eingangsqueue
    private static ArrayList<String> jmsOutboundQueues   = null;  // Namen der JMS Ausgangsqueues
    private static ArrayList<String> jmsOutboundUrls      = null;  // URLs der JMS Broker der jew. Ausgangsqueue
    private static ArrayList<String> jmsOutboundUsers     = null;  // Usernamen der JMS Broker der jew. Ausgangsqueue
    private static ArrayList<String> jmsOutboundPasswords = null;  // Passwörter der JMS Broker der jew. Ausgangsqueue
    private static boolean           jmsWriteOutFiles    = true;  // Gesendete Nachrichten auf Platte schreiben?
    private static String            jmsOutPath          = null;  // Absoluter Pfad für Ausgangsdateien
    private static boolean           jmsWriteInFiles     = true;  // Empfangene Nachrichten auf Platte schreiben?
    private static String            jmsInPath           = null;  // Absoluter Pfad für Eingangsdateien
    
    
    private Config() {
    }
    
    /**
     * Lädt die Konfigurationsdaten aus einer *.ini-Datei.
     * Prüft die Daten auf Mussangaben, vorhandene Dateien und Pfade.
     * Legt Pfade ggfls. neu an.
     * Weist alle Parameter internen Variablen zu.
     * 
     * @param configPath Pfad zur *.ini-Datei
     * @param name Name der *.ini-Datei
     * @return true, wenn die Datei gelesen werden konnte und alle Prüfungen sind OK, sonst false
     * @throws ConfigException 
     */
    public static boolean configure(String configPath, String name) throws ConfigException {
//        logger = Logger.getLogger("ccdb");
        Utils.log("(Config configure) configPath = " + configPath);
//        logger.debug("(Config configure) configPath = " + configPath);

        if (configPath != null) {
            iniPath = configPath;
        }
        if (name != null) {
            iniFile = name;
        }
        
//        int error = loadEnvironmentConfiguration();
////        if (error == Prop.ERR_NO_ERROR) {
//        if (error == Prop.ERR_NO_ERROR || error == Prop.ERR_ALREADY_LOADED) {
//            error = loadConfiguration();
//	        if (error == Prop.ERR_NO_ERROR) {
//	            readConfiguration();
//	            showConfiguration();
//	            return checkConfiguration();
//	        } else {
//	        	if (error != Prop.ERR_ALREADY_LOADED) {
//	        		throw new ConfigException("Config loadConfiguration meldet Fehler# " + error);
//	        	} 
//	        }
//        } else {
////        	if (error != Prop.ERR_ALREADY_LOADED) {
//        		throw new ConfigException("Config loadEnvironmentConfiguration meldet Fehler# " + error);
////        	}
//        }
        
//    	Hosts.readHosts();
//    	Hosts.showHosts();
        
        int ret = loadEnvironmentConfiguration();
        if (ret == Prop.ERR_NO_ERROR) {
        	// Environment-Konfiguration wurde (erneut) gelesen
        	// Dann Konfiguration auf jeden Fall (neu) einlesen
            ret = loadConfiguration(true);
	        if (ret == Prop.ERR_NO_ERROR || ret == Prop.ERR_ALREADY_LOADED) {
	            readConfiguration();
	            showConfiguration();
	            return checkConfiguration();
	        } else {
	        	throw new ConfigException("Config loadConfiguration meldet Fehler# " + ret);
	        }
        } else if (ret == Prop.ERR_ALREADY_LOADED) {
        	// Environment-Konfiguration war schon geladen und wurde nicht geändert
        	// Dann Konfiguration nur einlesen falls geändert
            ret = loadConfiguration(false);
            // Konfiguration wurde zum ersten mal gelesen oder wurde geändert 
	        if (ret == Prop.ERR_NO_ERROR) {
	            readConfiguration();
	            showConfiguration();
	            return checkConfiguration();
	        } else {
	        	if (ret != Prop.ERR_ALREADY_LOADED) {
	        		throw new ConfigException("Config loadConfiguration meldet Fehler# " + ret);
	        	}
	        }
        }
        
        return true;
    }
    
    /**
     * Lädt die Konfigurationsdaten erneut aus der *.ini-Datei.
     * Prüft die Daten auf Mussangaben, vorhandene Dateien und Pfade.
     * Legt Pfade ggfls. neu an.
     * Weist alle Parameter internen Variablen zu.
     * 
     * @param path Pfad zur *.ini-Datei
     * @param name Name der *.ini-Datei
     * @return true, wenn die Datei gelesen werden konnte und alle Prüfungen sind OK, sonst false
     * @throws ConfigException 
     */
    public static boolean reconfigure(String path, String name) throws ConfigException {
//        logger = Logger.getLogger("ccdb");
        if (path != null) {
            iniPath = path;
        }
        if (name != null) {
            iniFile = name;
        }
        if (reloadConfiguration()) {
            readConfiguration();
            showConfiguration();
            return checkConfiguration();
        } else {
            return false;
        }
    }

    
    /**
     * Loggt die geladenen Konfigurationsparameter.
     */
    public static void showConfiguration() {
		doShowConfiguration();
    }

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
    
    /* *********************************************************
     * Private methods
     * *********************************************************/
    private static int loadEnvironmentConfiguration() throws ConfigException {
		int error = Prop.load(iniPath, iniFileEnvironment, false);
        return error;
    }
    
    private static int loadConfiguration(boolean force) throws ConfigException {
		int error = Prop.load(iniPath, iniFile, force);
        return error;
    }
    
    private static boolean reloadConfiguration() throws ConfigException {
		int error = Prop.reload(iniPath, iniFile);
        return error == 0;
    }


    private static void readConfiguration() {
    	readApplicationParameters();

        logDebug              	= Prop.getBooleanProperty("log.debug", "true");
        logLevel              	= Prop.getIntProperty("log.level", "0");
        logSQL                	= Prop.getBooleanProperty("log.logSQL", "false");
        logJMS                	= Prop.getBooleanProperty("log.logJMS", "false");
        logDriverManager       	= Prop.getBooleanProperty("log.logDriverManager", "false");
        
        defaultViewName        	= Prop.getProperty("default.view.name", "Stammdaten.User.UserListe");
        defaultViewObject      	= Prop.getProperty("default.view.object", "de.kewill.ccwebui.client.MainView.userListView");
        defaultViewIndex       	= Prop.getProperty("default.view.index", "USER_LIST_VIEW");
        
        mailHost              	= Prop.getProperty("mail.host");
        mailAltHost           	= Prop.getProperty("mail.alt.host");
        mailFrom              	= Prop.getProperty("mail.from");
        mailFromName          	= Prop.getProperty("mail.fromName");
        mailSubject           	= Prop.getProperty("mail.subject");
        mailTo                	= Prop.getProperty("mail.to", "");
        mailCc                	= Prop.getProperty("mail.cc", "");
        mailBcc               	= Prop.getProperty("mail.bcc", "");
        mailDebug             	= Prop.getBooleanProperty("mail.debug", "false");

    	databases      		 	= getArrayListFromNumberedEntry("db.database.#");
    	numberOfDatabases		= databases.size();
    	dbname      		 	= getArrayListFromNumberedEntry("db.name.#");
    	dbindex = new HashMap<String, Integer>();
    	for (int i = 0; i < numberOfDatabases; i++) {
    		dbindex.put(dbname.get(i), i);
    	}
        dbtype                	= getArrayListFromNumberedEntry("db.type.#");
        dbuser                	= getArrayListFromNumberedEntry("db.user.#");
        dbpasswd              	= getArrayListFromNumberedEntry("db.passwd.#");
        dbdriver              	= getArrayListFromNumberedEntry("db.driver.#");
    	dbconnect             	= getArrayListFromNumberedEntry("db.connect.#." + applicationEnvironment);
    	addMysqlParametersToConnectString();
        dbPrefetchSize        	= Prop.getProperty("db.prefetchsize");
        dbSetQueryTimeout     	= Prop.getBooleanProperty("db.setQueryTimeout", "false");
        dbQueryTimeout        	= Prop.getIntProperty("db.queryTimeout", "0");
        
        ldapDomain            	= Prop.getProperty("ldap.domain", "csf.local");
        ldapDomainController1 	= Prop.getProperty("ldap.domainController.1", "dc01.csf.local");
        ldapDomainController2 	= Prop.getProperty("ldap.domainController.2", "dc02.csf.local");
//        ldapSearchBy          = Prop.getProperty("ldap.searchBy", "username");
        
        cluster				  	= Prop.getProperty("user.cluster", "02");
        validClusters		  	= getArrayList("user.validClusters", "01 02 06 08 09");
        
        showLogin             	 = Prop.getBooleanProperty("login.show.dialog." + applicationEnvironment, "true");
        useLdapLogin          	 = Prop.getBooleanProperty("login.use.ldap." + applicationEnvironment, "true");
        checkDirectories     	 = Prop.getBooleanProperty("check.directories", "true");
        checkSperrParams     	 = Prop.getBooleanProperty("check.sperrParams", "true");
        checkHostsParams     	 = Prop.getBooleanProperty("check.hostsParams", "true");
        checkClusterParams     	 = Prop.getBooleanProperty("check.clusterParams", "true");
        checkTimesParams     	 = Prop.getBooleanProperty("check.timesParams", "true");
        checkDeployParams     	 = Prop.getBooleanProperty("check.deployParams", "true");
        checkDatabaseParams    	 = Prop.getBooleanProperty("check.databaseParams", "true");
        deployScript		  	 = Prop.getProperty("deploy.script." + applicationEnvironment);
        deployTaxScript		  	 = Prop.getProperty("deploy.tax.script." + applicationEnvironment);

        unloadTimesScript	  	 = Prop.getProperty("unload.times.script." + applicationEnvironment);
        enableSperrenMenuItem 	 = Prop.getBooleanProperty("sperren.enable.menuItem." + applicationEnvironment);
        verteileSperrenScript 	 = Prop.getProperty("sperren.deploy.script." + applicationEnvironment);
        aktiviereSperrenScript 	 = Prop.getProperty("sperren.activate.script." + applicationEnvironment);
        aktiviereCronScript 	 = Prop.getProperty("sperren.activate.cron.script." + applicationEnvironment);
        deaktiviereSperrenScript = Prop.getProperty("sperren.deactivate.script." + applicationEnvironment);
        moveParkScript			 = Prop.getProperty("sperren.movepark.script." + applicationEnvironment);
        showSperrenScript		 = Prop.getProperty("sperren.show.script." + applicationEnvironment);
        bereinigeCronScript		 = Prop.getProperty("sperren.bereinige.cron.script." + applicationEnvironment);
        bereinigeSperrlisten	 = Prop.getProperty("sperren.bereinige.sperrlisten." + applicationEnvironment);
        testScript				 = Prop.getProperty("test.script." + applicationEnvironment);
        testParams	 			 = getArrayList("test.params." + applicationEnvironment, null);

    	tempDirName            	 = Prop.getProperty("temp.dir.name", "temp");
    	dstnLockFileName		 = Prop.getProperty("dstn.lock.file.name", "dstn_sperrliste.txt");
    	dstnLockDirName			 = Prop.getProperty("dstn.lock.dir.name." + applicationEnvironment);
    	verfLockFileName		 = Prop.getProperty("verf.lock.file.name", "no_send_tab");
    	verfLockDirName			 = Prop.getProperty("verf.lock.dir.name." + applicationEnvironment);
    	
//    	clusterCount			 = Prop.getIntProperty("cluster.count");
//    	Utils.log("(Config readConfiguration) clusterCount = " + clusterCount);
    	clusterCount			 = getCountOfNumberedEntries("cluster.#.name");
    	Utils.log("(Config readConfiguration) clusterCount = " + clusterCount);
    	clusterDisplayNames		 = getArrayListFromNumberedEntry("cluster.#.displayName", clusterCount);
    	clusterNames		 	 = getArrayListFromNumberedEntry("cluster.#.name", clusterCount);
    	clusterHosts       		 = getArrayListFromNumberedEntry("cluster.#.host", clusterCount);
    	uniqueClusterHosts = new ArrayList<String>();
    	for (String host : clusterHosts) {
    		if (!uniqueClusterHosts.contains(host)) {
    			uniqueClusterHosts.add(host);
    		}
    	}
    	clusterUsers       		 = getArrayListFromNumberedEntry("cluster.#.user", clusterCount);
    	clusterEnvironments		 = getArrayListStringArrayFromNumberedEntry("cluster.#.environments", clusterCount);
    	clusterViews			 = getArrayListStringArrayFromNumberedEntry("cluster.#.views", clusterCount);
    	clusterCategories		 = getArrayListFromNumberedEntry("cluster.#.category", clusterCount);
        clusterDefaultHost		 = Prop.getProperty("cluster.default.host." + applicationEnvironment);
        periodicAutostart		 = Prop.getBooleanProperty("periodic.autostart", "false");
        
//        enableHostsListMenuItem  = Prop.getBooleanProperty("hosts.enable.menuItem." + applicationEnvironment);
        hostsListTempDirName	 = Prop.getProperty("hosts.temp.dir." + applicationEnvironment);
        hostsDirName	 		 = Prop.getProperty("hosts.dir." + applicationEnvironment);

        sitaSucheHost	 		 = Prop.getProperty("sita.suche.host." + applicationEnvironment, "cccom");
        sitaSucheSkript		 	 = Prop.getProperty("sita.suche.script." + applicationEnvironment);
        sitaFilesList		 	 = Prop.getProperty("sita.files.list." + applicationEnvironment);

        enableProcessStatusMenuItem = Prop.getBooleanProperty("process.status.enable.menuItem." + applicationEnvironment);

        
        zollSossIP		 	 	= Prop.getProperty("zoll.SossIP." + applicationEnvironment);
        zollQSossIP		 	 	= Prop.getProperty("zoll.QSossIP." + applicationEnvironment);
        zollUseQSoss			= Prop.getBooleanProperty("zoll.useQSoss." + applicationEnvironment, "false");
        zollKleyerIP	 	 	= Prop.getProperty("zoll.KleyerIP." + applicationEnvironment);
        zollQKleyerIP	 	 	= Prop.getProperty("zoll.QKleyerIP." + applicationEnvironment);
        zollUseQKleyer			= Prop.getBooleanProperty("zoll.useQKleyer." + applicationEnvironment, "false");
        zollSossProxy	 	 	= Prop.getProperty("zoll.Soss.proxy." + applicationEnvironment);
        zollQSossProxy	 	 	= Prop.getProperty("zoll.QSoss.proxy." + applicationEnvironment);
        zollSoss1Proxy	 	 	= Prop.getProperty("zoll.Soss1.proxy." + applicationEnvironment);
        zollKleyerProxy	 	 	= Prop.getProperty("zoll.Kleyer.proxy." + applicationEnvironment);
        zollQKleyerProxy	  	= Prop.getProperty("zoll.QKleyer.proxy." + applicationEnvironment);
        zollKleyer1Proxy	 	= Prop.getProperty("zoll.Kleyer1.proxy." + applicationEnvironment);
        proxies.put("soss", zollSossProxy);
        proxies.put("qsoss", zollQSossProxy);
        proxies.put("soss1", zollSoss1Proxy);
        proxies.put("kleyer", zollKleyerProxy);
        proxies.put("qkleyer", zollQKleyerProxy);
        proxies.put("kleyer1", zollKleyer1Proxy);
        
        jmsUseJMS            = Prop.getBooleanProperty("jms.useJMS", "false");
        jmsStartBroker       = Prop.getBooleanProperty("jms.broker.start", "false");
        jmsConsumerThreads   = Prop.getIntProperty("jms.consumer.threads", "1");
        jmsMaxTimeout        = Prop.getIntProperty("jms.maxTimeout", "10");
        jmsInboundQueues     = fillArrayList("jms.inbound.queue");
        jmsInboundUrls       = fillArrayList("jms.inbound.broker.url");
        jmsInboundUsers      = fillArrayList("jms.inbound.broker.user");
        jmsInboundPasswords  = fillArrayList("jms.inbound.broker.password");
        jmsOutboundQueues    = fillArrayList("jms.outbound.queue");
        jmsOutboundUrls      = fillArrayList("jms.outbound.broker.url");
        jmsOutboundUsers     = fillArrayList("jms.outbound.broker.user");
        jmsOutboundPasswords = fillArrayList("jms.outbound.broker.password");
        jmsWriteOutFiles     = Prop.getBooleanProperty("jms.out.writeFiles", "false");
        jmsOutPath           = Prop.getProperty("jms.out.path");
        jmsWriteInFiles      = Prop.getBooleanProperty("jms.in.writeFiles", "false");
        jmsInPath            = Prop.getProperty("jms.in.path");
    }
    
	private static void readApplicationParameters() {
        applicationId         	= Prop.getProperty("application.id", "Not set!");
        applicationLocale     	= Prop.getProperty("application.locale", "de_DE");
        applicationEnvironment	= Prop.getProperty("application.environment", "production");
		if (applicationEnvironment.equalsIgnoreCase("eclipse")) {
			isEclipse = true;
			isDevelopment = false;
			isProduction = false;
		}
		if (applicationEnvironment.equalsIgnoreCase("development")) {
			isEclipse = false;
			isDevelopment = true;
			isProduction = false;
		}
		if (applicationEnvironment.equalsIgnoreCase("production")) {
			isEclipse = false;
			isDevelopment = false;
			isProduction = true;
		}
	}
	
    private static void addMysqlParametersToConnectString() {
    	for (int databaseIndex = 0; databaseIndex < numberOfDatabases; databaseIndex++) {
    		if (dbtype.get(databaseIndex).equalsIgnoreCase("MYSQL")) {
            	String param = null;
            	int i = 1;
            	do {
            		param = Prop.getProperty("db.mysql.parameter." + i);
            		if (param != null) {
            			Utils.log("(Config addMysqlParametersToConnectString) dbconnect = " + dbconnect.get(databaseIndex));
            			if (i == 1) {
            				dbconnect.set(databaseIndex, dbconnect.get(databaseIndex) + "?" + param);
            			} else {
                    		dbconnect.set(databaseIndex, dbconnect.get(databaseIndex) + "&" + param);
            			}
                		i++;
            		}
            	} while (param != null);
    		}
    	}
    }
    
    
    /**
     * Zählt die Anzahl der Einträge von nummerierten Einträgen in der Konfig-Datei.
     * Die Methode zählt die Einträge in der Konfig-Datei, deren Schlüssel einem bestimmten Muster/Template mit einer Laufnummer entpricht.
     * Die Laufnummer steht an der Stelle im Schlüssel an der im Template ein "#" steht.
     * Es werden alle Einträge ab der Laufnummer 1 aufsteigend gesucht.
     * Die Nummerierung der Einträge muss lückenlos sein, da das Zählen beendet wird, sobald für eine Nummer kein Eintrag gefunden wird.
     * 
     * @param template 	Template der einzulesenden Konfig-Einträge.
     *  				
     * @return Anzahl der Einträge die mit dem Muster/Template übereinstimmen 
     */
    private static int getCountOfNumberedEntries(String template) {
    	String[] tokens = template.split("#");
    	String value = null;
    	int anz = 0;
    	while (true) {
    		anz++;
	    	String configKey = tokens[0] + anz + tokens[1]; 
	    	value = Prop.getProperty(configKey);
	    	if (value == null) {
	    		anz--;
	    		break;
	    	}
    	}
    	
    	return anz;
    }
    
    /**
     * Füllen einer Arraylist mit Strings aus nummerierten Einträgen in der Konfig-Datei.
     * 
     * @param template 			Template der einzulesenden Konfig-Einträge.
     *  						Die Laufnummer steht an der Stelle im Schlüssel an der im Template ein "#" steht.
     * @param  numberOfEntries	Anzahl der konfigurierten Einträge
     * @return Die mit den zu dem Template gehörenden Werten gefüllte ArrayList. 
     */
    private static ArrayList<String> getArrayListFromNumberedEntry(String template, int numberOfEntries) {
    	ArrayList<String> valueList = new ArrayList<String>();
    	// Dummy an Index 0 einfügen, weil die Numerierung ab 1 anfängt
    	// Sonst gibt es unten beim add in die ArrayList eine IndexOutOfBoundsException
    	valueList.add(0, "dummy");
    	
    	String[] tokens = template.split("#");
    	for (int i = 1; i <= numberOfEntries; i++) {
	    	String configKey = tokens[0] + i + tokens[1]; 
	    	valueList.add(i, Prop.getProperty(configKey));
    	}
    	return valueList;
    }
    
    /**
     * Füllen einer Arraylist mit Strings aus nummerierten Einträgen in der Konfig-Datei.
     * 
     * @param template 			Template der einzulesenden Konfig-Einträge.
     *  						Die Laufnummer steht an der Stelle im Schlüssel an der im Template ein "#" steht.
     * @return Die mit den zu dem Template gehörenden Werten gefüllte ArrayList. 
     */
    private static ArrayList<String> getArrayListFromNumberedEntry(String template) {
    	//"db.connect.#." + applicationEnvironment
    	//"db.connect.#.eclipse"
    	ArrayList<String> valueList = new ArrayList<String>();
    	String[] tokens = template.split("#");
		String configKey = null;
    	String value = null;
    	int i = 0;
    	do {
    		i++;
    		if (tokens.length > 1) {
    			configKey = tokens[0] + i + tokens[1];
    		} else {
    			configKey = tokens[0] + i;
    		}
	    	value = Prop.getProperty(configKey);
	    	if (value != null) {
		    	Utils.log("(Config getArrayListFromNumberedEntry) " + configKey + " = " + value);
		    	valueList.add(i - 1, Prop.getProperty(configKey));
	    	}
    	} while (value != null);
    	return valueList;
    }
    
    
    /**
     * Füllen einer Arraylist mit String Arrays aus nummerierten Einträgen in der Konfig-Datei.
     * 
     * @param template 			Template der einzulesenden Konfig-Einträge.
     *  						Die Laufnummer steht an der Stelle im Schlüssel an der im Template ein "#" steht.
     * @param  numberOfEntries	Anzahl der konfigurierten Einträge
     * @return Die mit den zu dem Template gehörenden Werten gefüllte ArrayList. 
     */
    private static ArrayList<String[]> getArrayListStringArrayFromNumberedEntry(String template, int numberOfEntries) {
    	ArrayList<String[]> valueList = new ArrayList<String[]>();
    	// Dummy an Index 0 einfügen, weil die Numerierung ab 1 anfängt
    	// Sonst gibt es unten eine IndexOutOfBoundsException
    	valueList.add(0, new String[]{"dummy"});
    	
    	String[] tokens = template.split("#");
    	for (int i = 1; i <= numberOfEntries; i++) {
	    	String configKey = tokens[0] + i + tokens[1];
	    	String value = Prop.getProperty(configKey);
	    	String[] valueTokens = value.split("[, ;\t]");
	    	ArrayList<String> validValues = new ArrayList<String>();
	    	for (int j = 0; j < valueTokens.length; j++) {
	    		if (!Utils.isStringEmpty(valueTokens[j])) {
	    			validValues.add(valueTokens[j]);
	    		}
	    	}
	    	String[] returnValues = new String[validValues.size()];
	    	for (int j = 0; j < validValues.size(); j++) {
	    		returnValues[j] = validValues.get(j);
	    	}
	    	valueList.add(i, returnValues);
    	}
    	return valueList;
    }
    
    /**
     * Füllen einer ArrayList aus einem String.
     * Die Werte im String können durch Leerzeichen, Tab, Komma oder Semikolon getrennt sein. 
     *  
     * @param configKey Schlüssel aus der Konfigurationsdatei, dessen Wete gelesen werden sollen.
     * @param defaultValue Standartwert falls der Schlüssel in der Konfig-Datei lerr ist oder fehlt. 
     * @return Liste der Werte aus dem angegebenen Schlüssel
     */
    private static ArrayList<String> getArrayList(String configKey, String defaultValue) {
    	ArrayList<String> valueList = new ArrayList<String>();
    	String temp = Prop.getProperty(configKey, defaultValue);
    	if (temp == null) {
    		return null;
    	}
//    	String[] values = temp.split(" \t,;");
    	String[] values = temp.split("[ \t,;]");
    	for (String value : values) {
    		Utils.log("(Config getArrayList) value = " + value);
    		if (Utils.isStringEmpty(value)) {
    			continue;
    		}
    		valueList.add(value);
    	}
    	Utils.log("(Config getArrayList) valueList = " + valueList);
    	return valueList;
    	
    }
    
    private static ArrayList<String> fillArrayList(String configKey) {
        ArrayList<String> arrayList = new ArrayList<String>();
        int i = 1;
        String temp = null;
        arrayList.clear();
        while (true) {
            temp = Prop.getProperty(configKey + "." + i);
            if (temp != null) {
                arrayList.add(temp);
                i++;
            } else {
                break;
            }
        }
        return arrayList;
    }
    
//    private static ArrayList<Boolean> fillBooleanArrayList(String configKey) {
//        ArrayList<Boolean> arrayList = new ArrayList<Boolean>();
//        int i = 1;
//        String temp = null;
//        arrayList.clear();
//        while (true) {
//            temp = Prop.getProperty(configKey + "." + i);
//            if (temp != null) {
//                arrayList.add(Boolean.parseBoolean(temp.trim()));
//                i++;
//            } else {
//                break;
//            }
//        }
//        return arrayList;
//    }
    
//    private static ArrayList<Integer> fillIntegerArrayList(String configKey) {
//        ArrayList<Integer> arrayList = new ArrayList<Integer>();
//        int i = 1;
//        String temp = null;
//        arrayList.clear();
//        while (true) {
//            temp = Prop.getProperty(configKey + "." + i);
//            if (temp != null) {
//                arrayList.add(Integer.parseInt(temp.trim()));
//                i++;
//            } else {
//                break;
//            }
//        }
//        return arrayList;
//    }
    
    private static boolean checkConfiguration() throws ConfigException {
        boolean rc = true;
        String errorMessage = null;

        Utils.setDebug(logDebug);
        Utils.setLogLevel(logLevel);
        
        if (checkDirectories) {
			tempDir = Utils.checkDir(tempDirName, "Temp-Verzeichnis");
	        if (tempDir == null) {
	        	errorMessage = "Das Temp-Directory (>" + tempDirName + "<) kann nicht angelegt werden.";
	        	rc = false; 
	        }
        }

        if (checkSperrParams) {
        	checkSperrParams();
        }

        if (checkDatabaseParams) {
	        if (dbdriver == null) {
	        	errorMessage = "In der Konfigurationsdatei (ccwebui.ini) wurde kein Datenbanktreiber (db.driver) angegeben.";
	            rc = false; 
	        }
	        
	        if (dbconnect == null) { 
	            errorMessage = "In der Konfigurationsdatei (ccwebui.ini) wurde kein Datenbank-Connectstring " +
	            			   "(db.dbconnect." + applicationEnvironment + ") angegeben.";
	            rc = false; 
	        }
	
	        if (databases.size() == 0) {
	            errorMessage = "In der Konfigurationsdatei (ccwebui.ini) wurde kein(e) Datenbankname(n) (db.database.#) angegeben.";
	            rc = false; 
	        }
        }
        
        if (checkDeployParams) {
	        if (deployScript == null) { 
	            errorMessage = "In der Konfigurationsdatei (ccwebui.ini) wurde kein Stammdaten-Verteilskript " +
	            			   "(deploy.script." + applicationEnvironment + ") angegeben.";
	            rc = false; 
	        }
	
	        if (deployTaxScript == null) { 
	            errorMessage = "In der Konfigurationsdatei (ccwebui.ini) wurde kein Tax-Stammdaten-Verteilskript " +
	            			   "(deploy.tax.script." + applicationEnvironment + ") angegeben.";
	            rc = false; 
	        }
        }

        if (checkTimesParams) {
	        if (unloadTimesScript == null) { 
	            errorMessage = "In der Konfigurationsdatei (ccwebui.ini) wurde kein Entladeskript für hsnd/hrec-Zeiten " +
	            			   "(unload.times.script." + applicationEnvironment + ") angegeben.";
	            rc = false; 
	        }
        }

        if (checkClusterParams) {
	    	if (clusterCount == 0) {
	            errorMessage = "In der Konfigurationsdatei (ccwebui.ini) wurde keine Anzahl der konfigurierten Cluster (cluster.count) angegeben.";
	            rc = false; 
	    	} else {
		    	for (int i = 1; i <= clusterCount; i++) {
			    	if (clusterDisplayNames.get(i) == null) {
			            errorMessage = "In der Konfigurationsdatei (ccwebui.ini) wurde kein DisplayName für Clusterkonfiguration " + i + 
			            			   "(cluster." + i + ".displayName) angegeben.";
			            rc = false; 
			    	}
			    	if (clusterNames.get(i) == null) {
			            errorMessage = "In der Konfigurationsdatei (ccwebui.ini) wurde kein Name für Clusterkonfiguration " + i + 
			            			   "(cluster." + i + ".name) angegeben.";
			            rc = false; 
			    	}
			    	if (clusterHosts.get(i) == null) {
			            errorMessage = "In der Konfigurationsdatei (ccwebui.ini) wurde kein Host für Clusterkonfiguration " + i + 
			            		       " (cluster." + i + ".host) angegeben.";
			            rc = false; 
			    	}
			    	if (clusterUsers.get(i) == null) {
			            errorMessage = "In der Konfigurationsdatei (ccwebui.ini) wurde kein User für Clusterkonfiguration " + i + 
			            		       " (cluster." + i + ".user) angegeben.";
			            rc = false; 
			    	}
			    	if (clusterEnvironments.get(i) == null) {
			            errorMessage = "In der Konfigurationsdatei (ccwebui.ini) wurde keine Ablaufumgebung für Clusterkonfiguration " + i + 
			            		       " (cluster." + i + ".environments) angegeben.";
			            rc = false; 
			    	}
			    	if (clusterViews.get(i) == null) {
			            errorMessage = "In der Konfigurationsdatei (ccwebui.ini) wurde keine View für Clusterkonfiguration " + i + 
			            		       " (cluster." + i + ".views) angegeben.";
			            rc = false; 
			    	}
			    	if (clusterCategories.get(i) == null) {
			            errorMessage = "In der Konfigurationsdatei (ccwebui.ini) wurde kein Bereich (dev, demo, prod, zoll, ...) "
			            			 	+ "für Clusterkonfiguration " + i + "(cluster." + i + ".displayName) angegeben.";
			            rc = false; 
			    	}
		    	}
	    	}
        }

    	if (checkHostsParams) {
	        if (hostsDirName == null) { 
	            errorMessage = "In der Konfigurationsdatei (ccwebui.ini) wurde kein Verzeichnis der /etc/hosts " +
	            			   "(hosts.dir." + applicationEnvironment + ") angegeben.";
	            rc = false; 
	        } 
	        
	        if (hostsListTempDirName == null) { 
	            errorMessage = "In der Konfigurationsdatei (ccwebui.ini) wurde kein Verzeichnis zum Bearbeiten der /etc/hosts " +
	            			   "(hosts.temp.dir." + applicationEnvironment + ") angegeben.";
	            rc = false; 
	        } else {
		    	hostsListTempDir = Utils.checkDir(hostsListTempDirName, "Hosts Temp-Verzeichnis");
		        if (hostsListTempDir == null) {
		        	errorMessage = "Das Temp-Directory (>" + hostsListTempDirName + "<) kann nicht angelegt werden.";
		        	rc = false; 
		        }
	        }
    	}

    	if (rc == false) {
            Utils.log("(Config checkConfiguration) " + errorMessage);
            throw new ConfigException(errorMessage);
        }
        
        return rc;
    }
    
    private static void checkSperrParams() throws ConfigException {
    	boolean rc = true;
    	String errorMessage = null;
        if (dstnLockDirName == null) {
        	errorMessage = "In der Konfigurationsdatei (ccwebui.ini) wurde kein Verzeichnis für Dienststellensperren " +
        				   "(dstn.lock.dir.name." + applicationEnvironment + ") angegeben.";
            rc = false; 
        } else {
    		dstnLockDir = Utils.checkDir(dstnLockDirName, "Dienststellensperren-Verzeichnis");
            if (dstnLockDir == null) {
            	errorMessage = "Das Dienststellensperren-Verzeichnis (>" + dstnLockDirName + "<) kann nicht angelegt werden.";
            	rc = false; 
            }
        }

        if (verfLockDirName == null) {
        	errorMessage = "In der Konfigurationsdatei (ccwebui.ini) wurde kein Verzeichnis für Verfahrenssperren " +
        				   "(verf.lock.dir.name." + applicationEnvironment + ") angegeben.";
            rc = false; 
        } else {
            verfLockDir = Utils.checkDir(verfLockDirName, "Verfahrenssperren-Verzeichnis");
            if (verfLockDir == null) {
            	errorMessage = "Das Verfahrenssperren-Verzeichnis (>" + verfLockDirName + "<) kann nicht angelegt werden.";
            	rc = false; 
            }
        }
    	
        if (verteileSperrenScript == null) { 
            errorMessage = "In der Konfigurationsdatei (ccwebui.ini) wurde kein Skript zur Verteilung von Zollstellen- und Verfahrenssperren " +
            			   "(sperren.deploy.script." + applicationEnvironment + ") angegeben.";
            rc = false; 
        }

        if (aktiviereSperrenScript == null) { 
            errorMessage = "In der Konfigurationsdatei (ccwebui.ini) wurde kein Skript zur Aktivierung von Zollstellen- und Verfahrenssperren " +
            			   "(sperren.activate.script." + applicationEnvironment + ") angegeben.";
            rc = false; 
        }

        if (aktiviereCronScript == null) { 
            errorMessage = "In der Konfigurationsdatei (ccwebui.ini) wurde kein Skript zur Eintragen der Sperren in die Crontab " +
            			   "(sperren.activate.cron.script." + applicationEnvironment + ") angegeben.";
            rc = false; 
        }

        if (deaktiviereSperrenScript == null) { 
            errorMessage = "In der Konfigurationsdatei (ccwebui.ini) wurde kein Skript zur Deaktivierung von Zollstellen- und Verfahrenssperren " +
            			   "(sperren.deactivate.script." + applicationEnvironment + ") angegeben.";
            rc = false; 
        }

        if (moveParkScript == null) { 
            errorMessage = "In der Konfigurationsdatei (ccwebui.ini) wurde kein Skript zum Verschieben der geparkten Dateien nach dem " +
            			   "Deaktivieren aller Sperren (sperren.movepark.script." + applicationEnvironment + ") angegeben.";
            rc = false; 
        }

        if (showSperrenScript == null) { 
            errorMessage = "In der Konfigurationsdatei (ccwebui.ini) wurde kein Skript zum Anzeigen der Sperren " +
            			   "(sperren.show.script." + applicationEnvironment + ") angegeben.";
            rc = false; 
        }
        
        if (bereinigeCronScript == null) { 
            errorMessage = "In der Konfigurationsdatei (ccwebui.ini) wurde kein Skript zum Bereinigen der Crontab " +
            			   "(sperren.bereinige.cron.script." + applicationEnvironment + ") angegeben.";
            rc = false; 
        }

        if (bereinigeSperrlisten == null) { 
            errorMessage = "In der Konfigurationsdatei (ccwebui.ini) wurde kein Skript zum Bereinigen der Sperrkonfigurationen " +
            			   "(sperren.bereinige.sperrlisten" + applicationEnvironment + ") angegeben.";
            rc = false; 
        }

    	if (rc == false) {
            Utils.log("(Config checkConfiguration) " + errorMessage);
            throw new ConfigException(errorMessage);
        }
    }
    
    
    
    /**
     * Loggt die geladenen Konfigurationsparameter.
     */
    private static void doShowConfiguration() {
		String configFileEnv = iniPath.trim() + System.getProperty("file.separator") + iniFileEnvironment.trim();
		String configFile    = iniPath.trim() + System.getProperty("file.separator") + iniFile.trim();
		Utils.log("(Config showConfiguration) Runtime Environment file    = " + configFileEnv);
        Utils.log("(Config showConfiguration) applicationEnvironment      = " + applicationEnvironment);
		Utils.log("(Config showConfiguration) configuration file          = " + configFile);
        Utils.log("(Config showConfiguration) showLogin                   = " + showLogin);
        Utils.log("(Config showConfiguration) useLdapLogin                = " + useLdapLogin);
        Utils.log("(Config showConfiguration) checkDirectories            = " + checkDirectories);
        Utils.log("(Config showConfiguration) checkSperrParams            = " + checkSperrParams);
        Utils.log("(Config showConfiguration) checkHostsParams            = " + checkHostsParams);
        Utils.log("(Config showConfiguration) checkClusterParams          = " + checkClusterParams);
        Utils.log("(Config showConfiguration) checkTimesParams            = " + checkTimesParams);
        Utils.log("(Config showConfiguration) checkDeployParams           = " + checkDeployParams);
        Utils.log("(Config showConfiguration) checkDatabaseParams         = " + checkDatabaseParams);
        Utils.log("(Config showConfiguration) applicationId               = " + applicationId);
        Utils.log("(Config showConfiguration) applicationLocale           = " + applicationLocale);
		Utils.log("(Config showConfiguration) logDebug                    = " + logDebug);
		Utils.log("(Config showConfiguration) logLevel                    = " + logLevel);
		Utils.log("(Config showConfiguration) logSQL                      = " + logSQL);
		Utils.log("(Config showConfiguration) logJMS                      = " + logJMS);
		Utils.log("(Config showConfiguration) logDriverManager            = " + logDriverManager);
		Utils.log("(Config showConfiguration) defaultViewName             = " + defaultViewName);
		Utils.log("(Config showConfiguration) defaultViewObject           = " + defaultViewObject);
		Utils.log("(Config showConfiguration) defaultViewIndex            = " + defaultViewIndex);
        Utils.log("(Config showConfiguration) mailHost                    = " + mailHost);
        Utils.log("(Config showConfiguration) mailAltHost                 = " + mailAltHost);
        Utils.log("(Config showConfiguration) mailFrom                    = " + mailFrom);
        Utils.log("(Config showConfiguration) mailFromName                = " + mailFromName);
        Utils.log("(Config showConfiguration) mailSubject                 = " + mailSubject);
        Utils.log("(Config showConfiguration) mailTo                      = " + mailTo);
        Utils.log("(Config showConfiguration) mailCc                      = " + mailCc);
        Utils.log("(Config showConfiguration) mailBcc                     = " + mailBcc);
        Utils.log("(Config showConfiguration) mailDebug                   = " + mailDebug);
		logArray(dbtype,    "dbtype", 20);
		logArray(dbuser,    "dbuser", 20);
		logArray(dbpasswd,  "dbpasswd", 18);
		logArray(dbdriver,  "dbdriver", 18);
        logArray(databases,	"databases", 17);
		Utils.log("(Config showConfiguration) numberOfDatabases          = " + numberOfDatabases);
        logArray(dbname,	"dbname", 20);
    	for (int i = 0; i < numberOfDatabases; i++) {
    		Utils.log("(Config showConfiguration) dbindex " + dbname.get(i) + " = " + dbindex.get(dbname.get(i)));
    	}
		logArray(dbconnect, "dbconnect", 17);
        Utils.log("(Config showConfiguration) dbPrefetchSize              = " + dbPrefetchSize);
        Utils.log("(Config showConfiguration) dbSetQueryTimeout           = " + dbSetQueryTimeout);
        Utils.log("(Config showConfiguration) dbQueryTimeout              = " + dbQueryTimeout);
        Utils.log("(Config showConfiguration) ldapDomain                  = " + ldapDomain);
        Utils.log("(Config showConfiguration) ldapDomainController1       = " + ldapDomainController1);
        Utils.log("(Config showConfiguration) ldapDomainController2       = " + ldapDomainController2);
        Utils.log("(Config showConfiguration) cluster                     = " + cluster);
        Utils.log("(Config showConfiguration) validClusters               = " + validClusters);
        Utils.log("(Config showConfiguration) deployScript                = " + deployScript);
        Utils.log("(Config showConfiguration) deployTaxScript             = " + deployTaxScript);
        Utils.log("(Config showConfiguration) unloadTimesScript           = " + unloadTimesScript);
        Utils.log("(Config showConfiguration) enableSperrenMenuItem       = " + enableSperrenMenuItem);
        Utils.log("(Config showConfiguration) verteileSperrenSkript       = " + verteileSperrenScript);
        Utils.log("(Config showConfiguration) aktiviereSperrenSkript      = " + aktiviereSperrenScript);
        Utils.log("(Config showConfiguration) aktiviereCronSkript         = " + aktiviereCronScript);
        Utils.log("(Config showConfiguration) deaktiviereSperrenSkript    = " + deaktiviereSperrenScript);
        Utils.log("(Config showConfiguration) moveParkSkript              = " + moveParkScript);
        Utils.log("(Config showConfiguration) showSperrenSkript           = " + showSperrenScript);
        Utils.log("(Config showConfiguration) bereinigeCronScript         = " + bereinigeCronScript);
        Utils.log("(Config showConfiguration) bereinigeSperrlisten        = " + bereinigeSperrlisten);
        Utils.log("(Config showConfiguration) testScript        		  = " + testScript);
        Utils.log("(Config showConfiguration) testParams        		  = " + testParams);
        Utils.log("(Config showConfiguration) tempDirName                 = " + tempDirName);
        Utils.log("(Config showConfiguration) dstnLockFileName            = " + dstnLockFileName);
        Utils.log("(Config showConfiguration) dstnLockDirName             = " + dstnLockDirName);
        Utils.log("(Config showConfiguration) verfLockFileName            = " + verfLockFileName);
        Utils.log("(Config showConfiguration) verfLockDirName             = " + verfLockDirName);
        Utils.log("(Config showConfiguration) clusterCount                = " + clusterCount);
        for (int i = 1; i <= clusterCount; i++) {
        	logArrayEntry(i, clusterDisplayNames, "cluster.#.displayName", 7);
        	logArrayEntry(i, clusterNames       , "cluster.#.name"       , 14);
        	logArrayEntry(i, clusterHosts       , "cluster.#.host"       , 14);
        	logArrayEntry(i, clusterUsers       , "cluster.#.user"       , 14);
        	logArrayEntryList(i, clusterEnvironments, "cluster.#.environments", 6);
        	logArrayEntryList(i, clusterViews,    "cluster.#.views"      , 13);
        	logArrayEntry(i, clusterCategories  , "cluster.#.category"   , 10);
        }
        logArray(uniqueClusterHosts,	 "uniqueClusterHost", 0);
        Utils.log("(Config showConfiguration) clusterDefaultHost          = " + clusterDefaultHost);
        Utils.log("(Config showConfiguration) periodicAutostart           = " + periodicAutostart);
//        Utils.log("(Config showConfiguration) enableHostsListMenuItem    = " + enableHostsListMenuItem);
        Utils.log("(Config showConfiguration) hostsListTempDirName        = " + hostsListTempDirName);
        Utils.log("(Config showConfiguration) hostsDirName                = " + hostsDirName);

        Utils.log("(Config showConfiguration) sitaSucheHost               = " + sitaSucheHost);
        Utils.log("(Config showConfiguration) sitaSucheSkript             = " + sitaSucheSkript);
        Utils.log("(Config showConfiguration) sitaFilesList               = " + sitaFilesList);

        Utils.log("(Config showConfiguration) enableProcessStatusMenuItem = " + enableProcessStatusMenuItem);

        Utils.log("(Config showConfiguration) zollSossIP                  = " + zollSossIP);
        Utils.log("(Config showConfiguration) zollQSossIP                 = " + zollQSossIP);
        Utils.log("(Config showConfiguration) zollUseQSoss                = " + zollUseQSoss);
        Utils.log("(Config showConfiguration) zollKleyerIP                = " + zollKleyerIP);
        Utils.log("(Config showConfiguration) zollQKleyerIP               = " + zollQKleyerIP);
        Utils.log("(Config showConfiguration) zollUseQKleyer              = " + zollUseQKleyer);
        Utils.log("(Config showConfiguration) zollSossProxy               = " + zollSossProxy);
        Utils.log("(Config showConfiguration) zollQSossProxy              = " + zollQSossProxy);
        Utils.log("(Config showConfiguration) zollSoss1Proxy              = " + zollSoss1Proxy);
        Utils.log("(Config showConfiguration) zollKleyerProxy             = " + zollKleyerProxy);
        Utils.log("(Config showConfiguration) zollQKleyerProxy            = " + zollQKleyerProxy);
        Utils.log("(Config showConfiguration) zollKleyer1Proxy            = " + zollKleyer1Proxy);
        Utils.log("(Config showConfiguration) proxies                     = " + proxies);

        Utils.log("(Config showConfiguration) jmsUseJMS                   = " + jmsUseJMS);
        Utils.log("(Config showConfiguration) jmsStartBroker              = " + jmsStartBroker);
        Utils.log("(Config showConfiguration) jmsConsumerThreads          = " + jmsConsumerThreads);
        Utils.log("(Config showConfiguration) jmsMaxTimeout               = " + jmsMaxTimeout);
        logArray(jmsInboundQueues,       "jmsInboundQueue", 11);
        logArray(jmsInboundUrls,         "jmsInboundUrl", 13);
        logArray(jmsInboundUsers,        "jmsInboundUser", 12);
        logArray(jmsInboundPasswords,    "jmsInboundPassword", 8);
        logArray(jmsOutboundQueues,      "jmsOutboundQueue", 10);
        logArray(jmsOutboundUsers,       "jmsOutboundUser", 12);
        logArray(jmsOutboundUrls,        "jmsOutboundUrl", 12);
        logArray(jmsOutboundPasswords,   "jmsOutboundPassword", 2);
        Utils.log("(Config showConfiguration) jmsWriteOutFiles            = " + jmsWriteOutFiles);
        Utils.log("(Config showConfiguration) jmsOutPath                  = " + jmsOutPath);
        Utils.log("(Config showConfiguration) jmsWriteInFiles             = " + jmsWriteInFiles);
        Utils.log("(Config showConfiguration) jmsInPath                   = " + jmsInPath);
    }

    // template ist der Name des Konfigurationseintrags mir einem "#" anstelle der Laufnummer
    private static void logArrayEntry(int index, ArrayList<String> list, String template, int spaces) {
        StringBuffer sb = new StringBuffer(spaces);
        for (int i = 0; i < spaces; i++) {
            sb.append(' ');
        }
        String spaceString = sb.toString();
    	String[] tokens = template.split("#");
       	String entryName = tokens[0] + index + tokens[1]; 
        Utils.log("(Config logArrayEntry)     " + entryName + spaceString + "= " + list.get(index));
    }
    
    // template ist der Name des Konfigurationseintrags mir einem "#" anstelle der Laufnummer
    private static void logArrayEntryList(int index, ArrayList<String[]> list, String template, int spaces) {
        StringBuffer sb = new StringBuffer(spaces);
        for (int i = 0; i < spaces; i++) {
            sb.append(' ');
        }
        String spaceString = sb.toString();
    	String[] tokens = template.split("#");
       	String entryName = tokens[0] + index + tokens[1];
       	String[] values = list.get(index);
       	for (int i = 0; i < values.length; i++) {
            Utils.log("(Config logArrayEntryList) " + entryName + spaceString + "= " + values[i]);
       	}
    }
    
    private static void logArray(ArrayList<String> list, String entryName, int spaces) {
        StringBuffer sb = new StringBuffer(spaces);
        for (int i = 0; i < spaces; i++) {
            sb.append(' ');
        }
        String spaceString = sb.toString();
        int i = 0;
        for (String entry : list) {
            i++;
            Utils.log("(Config logArray)          " + entryName + "." + i + spaceString + "= " + entry);
        }
    }
    
//    private static void logBooleanArray(ArrayList<Boolean> list, String entryName, int spaces) {
//        StringBuffer sb = new StringBuffer(spaces);
//        for (int i = 0; i < spaces; i++) {
//            sb.append(' ');
//        }
//        String spaceString = sb.toString();
//        int i = 0;
//        for (Boolean entry : list) {
//            i++;
//            Utils.log("(Config showConfiguration)   " + entryName + "." + i + spaceString + "= " + entry);
//        }
//    }
    
//    private static void logIntegerArray(ArrayList<Integer> list, String entryName, int spaces) {
//        StringBuffer sb = new StringBuffer(spaces);
//        for (int i = 0; i < spaces; i++) {
//            sb.append(' ');
//        }
//        String spaceString = sb.toString();
//        int i = 0;
//        for (Integer entry : list) {
//            i++;
//            Utils.log("(Config showConfiguration)   " + entryName + "." + i + spaceString + "= " + entry);
//        }
//    }
    
    
    
    
    /* *****************************************************
     * Getter und Setter
     * *****************************************************/
    public static boolean isShowLogin() {
        return showLogin;
    }
    public static String getApplicationId() {
        return applicationId;
    }
    public static String getApplicationLocale() {
        return applicationLocale;
    }
    public static String getApplicationEnvironment() {
        return applicationEnvironment;
    }
    public static boolean getLogDebug() {
        return logDebug;
    }
    public static int getLogLevel() {
        return logLevel;
    }
    public static boolean getLogSQL() {
        return logSQL;
    }
    public static boolean getLogJMS() {
        return logJMS;
    }
    
    public static String getTempDirName() {
        return tempDirName;
    }
    public static File getTempDir() {
        return tempDir;
    }

	public static String getDbname(int databaseIndex) {
		return dbname.get(databaseIndex);
	}

	public static String getDbtype(int databaseIndex) {
		return dbtype.get(databaseIndex);
	}

    public static String getDbconnect(int databaseIndex) {
        return dbconnect.get(databaseIndex);
    }

    public static int getDbindex(String name) {
        return dbindex.get(name);
    }

    public static String getDbdriver(int databaseIndex) {
    	if (dbdriver == null || dbdriver.size() == 0) {
    		return null;
    	} else {
    		return dbdriver.get(databaseIndex);
    	}
    }

    public static String getDbpasswd(int databaseIndex) {
        return dbpasswd.get(databaseIndex);
    }
    
    public static String getDbuser(int databaseIndex) {
        return dbuser.get(databaseIndex);
    }
    public static String getDbPrefetchSize() {
        return dbPrefetchSize;
    }

    public static boolean isDbSetQueryTimeout() {
        return dbSetQueryTimeout;
    }

    public static int getDbQueryTimeout() {
        return dbQueryTimeout;
    }
    
    public static String getMailBcc() {
        return mailBcc.trim();
    }

    public static String getMailCc() {
        return mailCc.trim();
    }

    public static String getMailFrom() {
        return mailFrom;
    }

    public static String getMailFromName() {
        return mailFromName;
    }

    public static String getMailSubject() {
        return mailSubject;
    }

    public static String getMailHost() {
        return mailHost;
    }

    public static String getMailAltHost() {
        return mailAltHost;
    }

    public static String getMailTo() {
        return mailTo.trim();
    }

    public static boolean getMailDebug() {
        return mailDebug;
    }

	public static String getLdapDomain() {
		return ldapDomain;
	}

	public static String getLdapDomainController1() {
		return ldapDomainController1;
	}

	public static String getLdapDomainController2() {
		return ldapDomainController2;
	}

//	public static String getLdapSearchBy() {
//		return ldapSearchBy;
//	}

	public static boolean isUseLdapLogin() {
		return useLdapLogin;
	}

	public static String getCluster() {
		return cluster;
	}

	public static ArrayList<String> getValidClusters() {
		return validClusters;
	}

	public static String getDeployScript() {
		return deployScript;
	}

	public static String getDeployTaxScript() {
		return deployTaxScript;
	}

	public static String getUnloadTimesScript() {
		return unloadTimesScript;
	}

	public static String getDstnLockDirName() {
		return dstnLockDirName;
	}

	public static File getDstnLockDir() {
		return dstnLockDir;
	}

	public static String getVerfLockDirName() {
		return verfLockDirName;
	}

	public static File getVerfLockDir() {
		return verfLockDir;
	}

	public static String getDstnLockFileName() {
		return dstnLockFileName;
	}

	public static String getVerfLockFileName() {
		return verfLockFileName;
	}

	public static boolean getEnableSperrenMenuItem() {
		return enableSperrenMenuItem;
	}

	public static String getVerteileSperrenScript() {
		return verteileSperrenScript;
	}

	public static String getAktiviereSperrenScript() {
		return aktiviereSperrenScript;
	}

	public static String getAktiviereCronScript() {
		return aktiviereCronScript;
	}

	public static String getDeaktiviereSperrenScript() {
		return deaktiviereSperrenScript;
	}

	public static String getMoveParkScript() {
		return moveParkScript;
	}

	public static String getShowSperrenScript() {
		return showSperrenScript;
	}

	public static String getBereinigeCronScript() {
		return bereinigeCronScript;
	}

	public static int getClusterCount() {
		return clusterCount;
	}

	public static ArrayList<String> getClusterDisplayNames() {
		return clusterDisplayNames;
	}

	public static ArrayList<String> getClusterNames() {
		return clusterNames;
	}

	public static ArrayList<String> getClusterHosts() {
		return clusterHosts;
	}

	public static ArrayList<String> getClusterUsers() {
		return clusterUsers;
	}

	public static ArrayList<String[]> getClusterEnvironments() {
		return clusterEnvironments;
	}

	public static String getBereinigeSperrlisten() {
		return bereinigeSperrlisten;
	}

	public static String getTestScript() {
		return testScript;
	}

	public static ArrayList<String> getTestParams() {
		return testParams;
	}

//	public static AppUserDTO getCurrentUserDTO() {
//		return currentUserDTO;
//	}
//
//	public static void setCurrentUserDTO(AppUserDTO currentUserDTO) {
//		Config.currentUserDTO = currentUserDTO;
//	}

	public static String getCurrentUser() {
		return currentUser;
	}

	public static void setCurrentUser(String currentUser) {
		Config.currentUser = currentUser;
	}

	public static boolean isCurrentUserIsAdmin() {
		return currentUserIsAdmin;
	}

	public static void setCurrentUserIsAdmin(boolean currentUserIsAdmin) {
		Config.currentUserIsAdmin = currentUserIsAdmin;
	}

	public static boolean isCurrentUserIsReadOnly() {
		return currentUserIsReadOnly;
	}

	public static void setCurrentUserIsReadOnly(boolean currentUserIsReadOnly) {
		Config.currentUserIsReadOnly = currentUserIsReadOnly;
	}

	public static String getDefaultViewName() {
		return defaultViewName;
	}

	public static String getDefaultViewObject() {
		return defaultViewObject;
	}

	public static String getDefaultViewIndex() {
		return defaultViewIndex;
	}

	public static boolean isEclipse() {
		return isEclipse;
	}

	public static boolean isDevelopment() {
		return isDevelopment;
	}

	public static boolean isProduction() {
		return isProduction;
	}

	public static boolean isJmsUseJMS() {
		return jmsUseJMS;
	}

	public static boolean isJmsStartBroker() {
		return jmsStartBroker;
	}

	public static int getJmsConsumerThreads() {
		return jmsConsumerThreads;
	}

	public static int getJmsMaxTimeout() {
		return jmsMaxTimeout;
	}

	public static ArrayList<String> getJmsInboundQueues() {
		return jmsInboundQueues;
	}

	public static ArrayList<String> getJmsInboundUrls() {
		return jmsInboundUrls;
	}

	public static ArrayList<String> getJmsInboundUsers() {
		return jmsInboundUsers;
	}

	public static ArrayList<String> getJmsInboundPasswords() {
		return jmsInboundPasswords;
	}

	public static ArrayList<String> getJmsOutboundQueues() {
		return jmsOutboundQueues;
	}

	public static ArrayList<String> getJmsOutboundUrls() {
		return jmsOutboundUrls;
	}

	public static ArrayList<String> getJmsOutboundUsers() {
		return jmsOutboundUsers;
	}

	public static ArrayList<String> getJmsOutboundPasswords() {
		return jmsOutboundPasswords;
	}

	public static boolean isJmsWriteOutFiles() {
		return jmsWriteOutFiles;
	}

	public static String getJmsOutPath() {
		return jmsOutPath;
	}

	public static boolean isJmsWriteInFiles() {
		return jmsWriteInFiles;
	}

	public static String getJmsInPath() {
		return jmsInPath;
	}

	public static ArrayList<String> getUniqueClusterHosts() {
		return uniqueClusterHosts;
	}

	public static String getClusterDefaultHost() {
		return clusterDefaultHost;
	}

	public static boolean isPeriodicAutostart() {
		return periodicAutostart;
	}

	public static String getHostsListTempDirName() {
		return hostsListTempDirName;
	}
	public static File getHostsListTempDir() {
		return hostsListTempDir;
	}

	public static String getHostsDirName() {
		return hostsDirName;
	}

	public static ArrayList<String> getDatabases() {
		return databases;
	}

	public static int getNumberOfDatabases() {
		return numberOfDatabases;
	}

	public static ArrayList<String[]> getClusterViews() {
		return clusterViews;
	}

	public static boolean isLogDriverManager() {
		return logDriverManager;
	}

	public static boolean isCheckDirectories() {
		return checkDirectories;
	}

	public static void setCheckDirectories(boolean checkDirectories) {
		Config.checkDirectories = checkDirectories;
	}

	public static boolean isCheckSperrParams() {
		return checkSperrParams;
	}

	public static void setCheckSperrParams(boolean checkSperrParams) {
		Config.checkSperrParams = checkSperrParams;
	}

	public static String getIniPath() {
		return iniPath;
	}

	public static String getIniFile() {
		return iniFile;
	}

	public static String getIniFileEnvironment() {
		return iniFileEnvironment;
	}

	public static boolean isCheckHostsParams() {
		return checkHostsParams;
	}

	public static boolean isCheckClusterParams() {
		return checkClusterParams;
	}

	public static boolean isCheckTimesParams() {
		return checkTimesParams;
	}

	public static boolean isCheckDeployParams() {
		return checkDeployParams;
	}

	public static boolean isCheckDatabaseParams() {
		return checkDatabaseParams;
	}

	public static String getSitaSucheHost() {
		return sitaSucheHost;
	}

	public static String getSitaSucheSkript() {
		return sitaSucheSkript;
	}

	public static String getSitaFilesList() {
		return sitaFilesList;
	}

	public static boolean isEnableProcessStatusMenuItem() {
		return enableProcessStatusMenuItem;
	}

	public static ArrayList<String> getClusterCategories() {
		return clusterCategories;
	}

	public static void setClusterCategories(ArrayList<String> clusterCategories) {
		Config.clusterCategories = clusterCategories;
	}

	public static String getZollSossIP() {
		return zollSossIP;
	}

	public static String getZollQSossIP() {
		return zollQSossIP;
	}

	public static String getZollKleyerIP() {
		return zollKleyerIP;
	}

	public static String getZollQKleyerIP() {
		return zollQKleyerIP;
	}

	public static boolean isZollUseQSoss() {
		return zollUseQSoss;
	}

	public static boolean isZollUseQKleyer() {
		return zollUseQKleyer;
	}

	public static String getZollSossProxy() {
		return zollSossProxy;
	}

	public static String getZollSoss1Proxy() {
		return zollSoss1Proxy;
	}

	public static String getZollKleyerProxy() {
		return zollKleyerProxy;
	}

	public static String getZollKleyer1Proxy() {
		return zollKleyer1Proxy;
	}

	public static HashMap<String, String> getProxies() {
		return proxies;
	}

	public static String getZollQSossProxy() {
		return zollQSossProxy;
	}

	public static String getZollQKleyerProxy() {
		return zollQKleyerProxy;
	}

//	public static boolean isEnableHostsListMenuItem() {
//		return enableHostsListMenuItem;
//	}

}
