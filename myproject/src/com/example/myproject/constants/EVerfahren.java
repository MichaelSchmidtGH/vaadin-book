package com.example.myproject.constants;

/**
 * Constants for procedure types.<br>
 * 
 * @author schmidt
 * @version 1.0.00
 * @see #ZBE
 * @see #ZBV
 * @see #SAN
 * @see #SAV
 * @see #SAT
 * @see #SAF
 * @see #SAE
 * @see #SKO
 * @see #SWV
 * @see #SAS
 * @see #ZAV
 * @see #ZVV
 * @see #ZSZ
 * @see #TUF
 * @see #TUZ
 * @see #TBE
 * @see #TVS
 * @see #LAE
 * @see #LVE
 * @see #LVV
 * @see #LVA
 * @see #LUE
 * @see #LEZ
 * @see #LBA
 * @see #AVV
 * @see #AAV
 * @see #AEZ
 * @see #CHE
 * @see #CHN
 * @see #CIR
 * @see #EXP
 * @see #EXT
 */
public enum EVerfahren {
	
	/**
	 * Summarische Anmeldung - Vorzeitige Anmeldung.
	 */
	SAV(),
	
	/**
	 * Summarische Anmeldung - Anmeldung nach Gestellung.
	 */
	SAN(),
	
	/**
	 * Summarische Anmeldung - Aufteilung.
	 */
	SAT(),
	
	/**
	 * Summarische Anmeldung - Antrag auf Fristverlängerung.
	 */
	SAF(),
	
	/**
	 * Summarische Anmeldung - Änderung Verfügungsberechtigter.
	 */
	SAE(),
	
	/**
	 * Summarische Anmeldung - Konsolidierung.
	 */
	SKO(),
	
	/**
	 * Summarische Anmeldung - Wiederausfuhr Versand 444-448.
	 */
	SWV(),
	
	/**
	 * Summarische Anmeldung - Änderung des Spezifischen Ordnungsbegriffs.
	 */
	SAS(),
	
	/**
	 * Freier Verkehr - Normalverfahren (Zollbehandlung) - Einzelzollanmeldung.
	 */
	ZBE(),

	/**
	 * Freier Verkehr - Normalverfahren (Zollbehandlung) - Vorzeitige Einzelzollanmeldung.
	 */
	ZBV(),
	
	/**
	 * Freier Verkehr - Vereinfachte Verfahren (Sammelzoll) - Vorzeitige vereinfachte Zollanmeldung.
	 */
	ZAV(),

	/**
	 * Freier Verkehr - Vereinfachte Verfahren (Sammelzoll) - Vereinfachte Zollanmeldung.
	 */
	ZVV(),

	/**
	 * Freier Verkehr - Vereinfachte Verfahren (Sammelzoll) - Ergänzende Zollanmeldung.
	 */
	ZSZ(),

	/**
	 * Versand - Anmeldung (Überführung, Überwachung).
	 */
	TUF(), TUZ(),

	/**
	 * Versand - Beendigung.
	 */
	TBE(),

	/**
	 * Versand - Transit / Verwaltung von Sicherheiten.
	 */
	TVS(),

	/**
	 * Zolllager - Überführung Normalverfahren - Einzelzollanmeldung.
	 */
	LAE(),

	/**
	 * Zolllager - Überführung Normalverfahren - Vorzeitige Einzelzollanmeldung.
	 */
	LVE(),

	/**
	 * Zolllager - Überführung vereinfachte Verfahren - Vorzeitige Vereinfachte Zollanmeldung.
	 */
	LVV(),

	/**
	 * Zolllager - Überführung vereinfachte Verfahren - Vereinfachte Zollanmeldung.
	 */
	LVA(),

	/**
	 * Zolllager - Überführung vereinfachte Verfahren - BA (Bestand Abmeldung?).
	 */
	LBA(),

	/**
	 * Zolllager - Überführung vereinfachte Verfahren - Lager Übergang.
	 */
	LUE(),

	/**
	 * Zolllager - Überführung vereinfachte Verfahren - Beendigung.
	 */
	LEZ(),

	/**
	 * AV/UV - Überführung Vereinfachte Verfahren - Vorzeitige vereinfachte Zollanmeldung.
	 */
	AVV(),

	/**
	 * AV/UV - Überführung Vereinfachte Verfahren - Vereinfachte Zollanmeldung.
	 */
	AAV(),

	/**
	 * AV/UV - Überführung - Ergänzende Zollanmeldung.
	 */
	AEZ(),

	/**
	 * Ausfuhr - Export - Überführung, Erledigung.
	 */
	EXP(),

	/**
	 * Ausfuhr - Exit - Überwachung.
	 */
	EXT(),

	/**
	 * Ausfuhr - Query - Statusauskunft.
	 */
	EXQ(),

	/**
	 * EDEC - Ausfuhr NCTS.
	 */
	CHE(),

	/**
	 * EDEC - NCTS-OUT.
	 */
	CHN(),

	/**
	 * EDEC - Import.
	 */
	CIR(),

	/**
	 * EDEC - Export.
	 */
	CIS(),

	

	/**
	 * Dummy end item.  
	 */
	XXX(); 
	
	
	
}
