package com.example.myproject.constants;

/**
 * Datenbanken mit denen gearbeitet wird.
 * 
 * @author schmidt
 */
public enum EDatabases {
	/**
	 * Master-Datenbank.
	 * Dort sind die Stammdaten des Clearing-Centers hinterlegt.
	 */
	MASTER(0),
	
	
	/**
	 * Statistik-Datenbank.
	 * Dort sind Nachrichten, die durch das Clearing-Centers gegangen sind, gespeichert.
	 */
	STATISTIK(1);
	
	
	private EDatabases(int databaseIndex) { 
		this.databaseIndex = databaseIndex;
	}
	
	private int databaseIndex = 0;
	
	public int getIndex() {
		return databaseIndex;
	}
	
	
	
}
