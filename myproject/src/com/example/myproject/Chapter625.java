package com.example.myproject;

import com.example.myproject.utils.Utils;
import com.vaadin.annotations.Theme;
import com.vaadin.event.Action;
import com.vaadin.event.Action.Handler;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;



/**
 * Book of Vaadin.
 * Vaadin 7 Edition - 7th Revision.
 * 
 **/
@SuppressWarnings("serial")
@Theme("myproject")
//@Theme("base")
//@Theme("chameleon")
//@Theme("liferay")
//@Theme("reindeer")
//@Theme("runo")
//@Theme("valo")
public class Chapter625 extends UI implements Handler {
	public static final long serialVersionUID = 1L;
	
	Panel 	   panel  = new Panel();
	FormLayout layout = new FormLayout();

	Action action_F3 = new ShortcutAction("F3", ShortcutAction.KeyCode.F3, null);
	Action action_F4 = new ShortcutAction("F4", ShortcutAction.KeyCode.F4, null);

	@Override
	protected void init(VaadinRequest request) {
		Utils.log("(Chapter625 init) ");

		layout.setMargin(true);
		layout.setDescription("Formlayout von Chapter6UI");
		panel.setContent(layout);
		setContent(panel);

        
		zabisMenu(layout);
		kapitel6251(layout);
	}

	
	
	// Kapitel 6.25.1 Creating a Menu 
	private void kapitel6251(FormLayout layout) {
		// A feedback component
		final Label selection = new Label("-");


		// Define a common menu command for all the menu items.
		MenuBar.Command mycommand = new MenuBar.Command() {
			public void menuSelected(MenuItem selectedItem) {
				selection.setValue("Ordered a " + selectedItem.getText() + " from menu.");
			}
		};

		
		// Die Menüleiste
		MenuBar barmenu = new MenuBar();

		// A top-level menu item that opens a submenu
		MenuItem drinks = barmenu.addItem("Beverages", null, null);
		
		// Submenu item with a sub-submenu
		MenuItem hots = drinks.addItem("Hot", null, null);
		hots.addItem("Tea",	new ThemeResource("../bookExamples/icons/tea-16px.png"), mycommand);
		hots.addItem("Coffee",	new ThemeResource("../bookExamples/icons/coffee-16px.png"), mycommand);

		// Another submenu item with a sub-submenu
		MenuItem colds = drinks.addItem("Cold", null, null);
		colds.addItem("Milk", null, mycommand);
		colds.addItem("Weissbier", null, mycommand);
		
		// Another top-level item
		MenuItem snacks = barmenu.addItem("Snacks", null, null);
		snacks.addItem("Weisswurst", null, mycommand);
		snacks.addItem("Bratwurst", null, mycommand);
		snacks.addItem("Currywurst", null, mycommand);
		
		// Yet another top-level item
		MenuItem servs = barmenu.addItem("Services", null, null);
		servs.addItem("Car Service", null, mycommand);
		
	
		// Komponenten dem Layout hinzufügen
		layout.addComponent(barmenu);
		layout.addComponent(selection);
	}

	
	// ZABIS-Menü 
	private void zabisMenu(FormLayout layout) {
		// Define a common menu command for all the menu items.
		MenuBar.Command mycommand = new MenuBar.Command() {
			public void menuSelected(MenuItem selectedItem) {
				Notification.show(selectedItem.getText(), "ausgewählt", Notification.Type.WARNING_MESSAGE);
			}
		};

		
		// Die Menüleiste
		MenuBar mainMenu = new MenuBar();

		// Das ZABIS-Menue
	    MenuItem dMainMZabis    = mainMenu.addItem("ZABIS", null, null);
	    MenuItem dMainMZabisMO1 = dMainMZabis.addItem("Beenden", null, mycommand);		// KEY='message(''MSGTERM.F3'')' 
	    
	    
	    // Das Stammdaten-Menue
	    // #NOT-HISTORIE-CODE-START
	    MenuItem dMainMStamm	= mainMenu.addItem("Stammdaten", null);
	    {
	    	MenuItem dMainMStammMKunden = dMainMStamm.addItem("Kunden", null);
	        {
		    	MenuItem dMainMStammMKundenMO1 = dMainMStammMKunden.addItem("Erfassung", mycommand);
		    	MenuItem dMainMStammMKundenMO2 = dMainMStammMKunden.addItem("Statusliste", mycommand);
	        }
	    	MenuItem dMainMStammMO1	= dMainMStamm.addItem("Kurse", mycommand);
	    	MenuItem dMainMStammMO2	= dMainMStamm.addItem("Textkonserven", mycommand);
	    	MenuItem dMainMStammMO3 = dMainMStamm.addItem("Sachbearbeiter", mycommand);
	    	MenuItem dMainMStammMO4 = dMainMStamm.addItem("Finanzämter", mycommand);
	    	MenuItem dMainMStammMArtikel = dMainMStamm.addItem("Artikel", null);
	        {
		    	MenuItem dMainMStammMArtikelMO1 = dMainMStammMArtikel.addItem("Erfassen", mycommand);
		    	MenuItem dMainMStammMArtikelMO2 = dMainMStammMArtikel.addItem("Statusliste", mycommand);
		    	MenuItem dMainMStammMArtikelMO3 = dMainMStammMArtikel.addItem("Einlesen", mycommand);
	        }
	    	MenuItem dMainMStammMSortiment = dMainMStamm.addItem("Sortiment/Stückliste", null);
	        {
		    	MenuItem dMainMStammMSortimentMO1 = dMainMStammMSortiment.addItem("Erfassen", mycommand);
		    	MenuItem dMainMStammMSortimentMO2 = dMainMStammMSortiment.addItem("Statusliste", mycommand);
	        }
	    	MenuItem dMainMStammMO5 = dMainMStamm.addItem("Mandanten", mycommand);
	    	MenuItem dMainMStammMO6 = dMainMStamm.addItem("Nummernkreise", mycommand);
	    	MenuItem dMainMStammMS1 = dMainMStamm.addSeparator();
	    	MenuItem dMainMStammMO7 = dMainMStamm.addItem("Flughäfen", mycommand);
	    	MenuItem dMainMStammMO8 = dMainMStamm.addItem("Länder", mycommand);
	    	MenuItem dMainMStammMO9 = dMainMStamm.addItem("Dienststellen", mycommand);
	    	MenuItem dMainMStammM10 = dMainMStamm.addItem("NCTS-Dienststellen", mycommand);
	    	MenuItem dMainMStammM11 = dMainMStamm.addItem("Dienststellen/Flughafen Zuordnung", mycommand);
	    	MenuItem dMainMStammMCodes = dMainMStamm.addItem("UN-Location-Codes", null);
	        {
		    	MenuItem dMainMStammMCodesMO1 = dMainMStammMCodes.addItem("UN/LOCODE", mycommand);
		    	MenuItem dMainMStammMCodesMO2 = dMainMStammMCodes.addItem("ISO Ländercode", mycommand);
		    	MenuItem dMainMStammMCodesMO3 = dMainMStammMCodes.addItem("Löschhafencodes", mycommand);
		    	// nur für den Support: Hafencodes laden
		    	MenuItem dMainMStammMCodesMO4 = dMainMStammMCodes.addItem("Daten aktualisieren", mycommand);
	        }
	    	MenuItem dMainMStammMS2 = dMainMStamm.addSeparator();
	    	MenuItem dMainMStammM12 = dMainMStamm.addItem("Kundenanbindung", mycommand);
	    	MenuItem dMainMStammM13 = dMainMStamm.addItem("Stammdateien laden", mycommand);
	    	MenuItem dMainMStammM14 = dMainMStamm.addItem("Stammdateien entladen", mycommand);
	    }
	    // #NOT-HISTORIE-CODE-END
	    
	    
		// Komponenten dem Layout hinzufügen
		layout.addComponent(mainMenu);
	}

	
	
	// Actions konfigurieren
	private void setUpActionHandlers(Panel panel) {
		panel.addActionHandler(this);
	}
	
	/**
	* Retrieve actions for a specific component. This method
	* will be called for each object that has a handler; in
	* this example just for login panel. The returned action
	* list might as well be static list.
	*/
	public Action[] getActions(Object target, Object sender) {
		Utils.log("(Chapter625 getActions) ");
		return new Action[] { action_F3, action_F4 };
	}
	
	/**
	* Handle actions received from keyboard. This simply directs
	* the actions to the same listener methods that are called
	* with ButtonClick events.
	*/
	public void handleAction(Action action, Object sender,
		Object target) {
		if (action == action_F3) {
			actionF3Handler();
		}
		if (action == action_F4) {
			actionF4Handler();
		}
	}
	
	public void actionF3Handler() {
		Notification.show("F3 - Beenden aufgerufen", Notification.Type.ERROR_MESSAGE);
	}
	
	public void actionF4Handler() {
		Notification.show("F4 gedrückt", Notification.Type.HUMANIZED_MESSAGE);
	}



}