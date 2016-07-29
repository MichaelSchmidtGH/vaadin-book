package com.example.myproject;

import java.io.Serializable;
import java.util.Locale;

import com.example.myproject.utils.Utils;
import com.vaadin.annotations.Theme;
import com.vaadin.client.ui.Icon;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Validator;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.server.AbstractErrorMessage.ContentMode;
import com.vaadin.server.ErrorMessage.ErrorLevel;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.Sizeable;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.UserError;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.Position;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.AbstractSelect;
import com.vaadin.ui.AbstractSelect.ItemCaptionMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.InlineDateField;
import com.vaadin.ui.Label;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.Notification;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.Upload;
import com.vaadin.ui.VerticalLayout;



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
public class Chapter6UI extends UI {
	public static final long serialVersionUID = 1L;
	
	FormLayout layout = new FormLayout();


	@Override
	protected void init(VaadinRequest request) {
		Utils.log("(Chapter6UI init) ");

		layout.setMargin(true);
		layout.setDescription("Formlayout von Chapter6UI");
		setContent(layout);

        
		kapitel621(layout);
		kapitel63(layout);
		kapitel64(layout);
		kapitel65(layout);
		
	}

	
	
	// Kapitel 6.2.1 Component Interface 
	private void kapitel621(FormLayout layout) {
		class AttachExample extends CustomComponent {
			@Override
			public void attach() {
				super.attach();			// Must call.
				
				// Now we know who ultimately owns us.
				Utils.log("(Chapter6UI.kapitel621(...).AttachExample attach) parent = " + getParent().getDescription());
			}
		}
		
		layout.addComponent(new AttachExample());
	}
	

	// Kapitel 6.3 Common Component Features 
	private void kapitel63(FormLayout layout) {
		
		// 6.3.1 Caption
		TextField name = new TextField("Name");
		layout.addComponent(name);
		
		TextField formName = new TextField("Caption im FormLayout");
		layout.addComponent(formName);
		
		VerticalLayout verticalLayout = new VerticalLayout();
		TextField verticalName = new TextField("Caption im VerticalLayout");
		verticalLayout.addComponent(verticalName);
		layout.addComponent(verticalLayout);
		
		
		// 6.3.2 Description and Tooltips
		Button button1 = new Button("A Button");
		button1.setDescription("This is the tooltip");
		UserError error = new UserError("UserError", ContentMode.TEXT, ErrorLevel.INFORMATION);
		button1.setComponentError(error);
		layout.addComponent(button1);

		Button button2 = new Button("A Button with a rich text tooltip");
		button2.setDescription(
				"<h2><img src=\"../VAADIN/themes/sampler/icons/comment_yellow.gif\"/>"+
						"A richtext tooltip</h2>"+
						"<ul>"+
						" <li>Use rich formatting with HTML</li>"+
						" <li>Include images from themes</li>"+
						" <li>etc.</li>"+
						"</ul>");				
		layout.addComponent(button2);
		
		
		// 6.3.3 Enabled
		HorizontalLayout buttonPanel = new HorizontalLayout();
		Button enabled = new Button("Enabled");
		enabled.setEnabled(true);	// The default
		buttonPanel.addComponent(enabled);
		
		Button disabled = new Button("Disabled");
		disabled.setEnabled(false);
		disabled.setDescription("Disabled Button");
		buttonPanel.addComponent(disabled);
		
		layout.addComponent(buttonPanel);
		
		
		// 6.3.4 Icon
		// Component with an icon from a custom theme
		TextField name1 = new TextField("Name");
		name1.setIcon(new ThemeResource("icons/user.png"));
		layout.addComponent(name1);
		// Component with an icon from another theme ('runo')
		Button ok = new Button("OK");
		ok.setIcon(new ThemeResource("../runo/icons/16/ok.png"));
		
		// TextField mit einem Icon aus einer Sammlung von FontIcons
		layout.addComponent(ok);
		TextField name2 = new TextField("Name");
		name2.setIcon(FontAwesome.USER);
		layout.addComponent(name2);
		
		
		// 6.3.5 Locale
		// Component for which the locale is meaningful
		InlineDateField date = new InlineDateField("Datum");
		// German language specified with ISO 639-1 language
		// code and ISO 3166-1 alpha-2 country code.
		date.setLocale(new Locale("de", "DE"));
		date.setResolution(Resolution.DAY);
		layout.addComponent(date);
		
		InlineDateField date2 = new InlineDateField("Date");
		date2.setLocale(Locale.ENGLISH);
		date2.setResolution(Resolution.DAY);
		layout.addComponent(date2);
		
		// 6.3.6 Readonly
		TextField readwrite = new TextField("Read-Write");
		readwrite.setValue("You can change this");
		readwrite.setReadOnly(false); // The default
		layout.addComponent(readwrite);
		
		TextField readonly = new TextField("Read-Only");
		readonly.setValue("You can't touch this!");
		readonly.setReadOnly(true);
		layout.addComponent(readonly);
		
		try {
			readonly.setValue("new value");
		} catch (Property.ReadOnlyException e) {
			Utils.log("(Chapter6UI kapitel63) Property.ReadOnlyException beim Setzen von Feld \"readonly\" aufgetreten");
		}
		
		// 6.3.7 Style Name
		Label label = new Label("This text has a lot of style");
		label.addStyleName("mystyle");
		layout.addComponent(label);
		Utils.log("(Chapter6UI kapitel63) styles = " + label.getStyleName());
		
		// 6.3.8 Visible
		TextField visible1 = new TextField("Next comes an invisible TextField");
		layout.addComponent(visible1);

		TextField invisible = new TextField("No-see-um");
		invisible.setValue("You can't see this!");
		invisible.setVisible(false);
		layout.addComponent(invisible);

		TextField visible2 = new TextField("After the invisible TextField");
		layout.addComponent(visible2);
		
		Button visibleButton = new Button("Make it visible");
		visibleButton.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				invisible.setVisible(true);
			}
		});
		layout.addComponent(visibleButton);
		
		Button invisibleButton = new Button("Make it invisible");
		invisibleButton.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				invisible.setVisible(false);
			}
		});
		layout.addComponent(invisibleButton);
		
		// 6.3.9 Sizing Components
		TextField mycomponent = new TextField("mycomponent");
		layout.addComponent(mycomponent);
		mycomponent.setWidth(100, Sizeable.UNITS_PERCENTAGE);
		mycomponent.setWidth(400, Sizeable.UNITS_PIXELS);
		mycomponent.setWidth("80%");
		mycomponent.setHeight("200px");
		
		// 6.3.10 Managing Input Focus
		Upload u = new Upload();
		layout.addComponent(u);
		u.setTabIndex(2);				// Bekommt den Fokus erst nach allen anderen Feldern (Subdialog/Subpanel?)
		
		TextField tab1 = new TextField("Tab 1");
		TextField tab2 = new TextField("Tab 2");
		TextField tab3 = new TextField("Tab 3");
		tab1.setTabIndex(1);
		tab2.setTabIndex(2);
		tab3.setTabIndex(3);
		layout.addComponent(tab1);
		layout.addComponent(tab2);
		layout.addComponent(tab3);
	}
	
	// Kapitel 6.4 Field Components
	private void kapitel64(FormLayout layout) {
		// 6.4.1 Field Interface
		TextField field1 = new TextField("Textfeld 1 (required)");
		field1.setRequired(true);
		field1.setRequiredError("Hier muss ein Wert eingegeben werden!");
		layout.addComponent(field1);
		
		// 6.4.4 Field Buffering
		field1.setBuffered(true);
		field1.setValidationVisible(true);
		TextField field2 = new TextField("Textfeld 2 (data source for field 1)");
		layout.addComponent(field2);
		field1.setPropertyDataSource(field2);
		field2.setValue("Value");
		Button commit = new Button("Commit", new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				try {
					field1.commit();
				} catch (InvalidValueException e) {
					Notification.show(e.getMessage());
				}
				Utils.log("(Chapter6UI.kapitel64(...).new ClickListener() {...} buttonClick) value = " + field2.getValue());
			}
		});
		layout.addComponent(commit);
		
		// 6.4.5 Field Validation
		TextField field = new TextField("Name 1");
		field.addValidator(new StringLengthValidator("The name must be 1-10 letters (was {0})", 1, 10, true));
		field.setNullRepresentation("");
		field.setNullSettingAllowed(false);
		field.setBuffered(true);
		field.setValidationVisible(true);
		field.setInvalidCommitted(false);
		Button commit1 = new Button("Commit 1", new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				try {
					Utils.log("(Chapter6UI.kapitel64(...).new ClickListener() {...} buttonClick 1) vor commit");
					Utils.log("(Chapter6UI.kapitel64(...).new ClickListener() {...} buttonClick 1) value = >" + field.getValue() + "<");
					field.validate();
					field.commit();
					Utils.log("(Chapter6UI.kapitel64(...).new ClickListener() {...} buttonClick 1) nach commit");
				} catch (InvalidValueException e) {
					Notification.show(e.getMessage());
				}
			}
		});
		layout.addComponent(field);
		layout.addComponent(commit1);
		
		// Automatic Validation
		TextField field3 = new TextField("Name");
		field3.addValidator(new StringLengthValidator("The name must be 1-10 letters (was {0})", 1, 10, true));
		field3.setImmediate(true);
		field3.setNullRepresentation("");
		field3.setNullSettingAllowed(true);
		layout.addComponent(field3);
		
		
		// Explicit Validation
		// A field with automatic validation disabled
		final TextField field4 = new TextField("Name");
		field4.setNullRepresentation("");
		field4.setNullSettingAllowed(true);
		field4.setValidationVisible(false);
		layout.addComponent(field4);
		// Define validation as usual
		field4.addValidator(new StringLengthValidator("The name must be 1-10 letters (was {0})", 1, 10, true));
		// Run validation explicitly
		Button validate = new Button("Validate");
		validate.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				field4.setValidationVisible(false);
				try {
					field4.validate();
				} catch (InvalidValueException e) {
					Notification.show(e.getMessage());
					field4.setValidationVisible(true);
				}
			}
		});
		layout.addComponent(validate);
		
		
		// Implementing a Custom Validator
		class MyValidator implements Validator {
			@Override
			public void validate(Object value) throws InvalidValueException {
				if (!(value instanceof String && ((String)value).equals("hello"))) {
					throw new InvalidValueException("You're impolite");
				}
			}
		}
		final TextField field5 = new TextField("Say hello");
		field5.addValidator(new MyValidator());
		field5.setImmediate(true);
		layout.addComponent(field5);
	}

	// Kapitel 6.5 Selection Components
	private void kapitel65(FormLayout layout) {
		// 6.5.1 Binding Selection Components to Data
		// Have a container data source of some kind
		IndexedContainer container = new IndexedContainer();
		container.addContainerProperty("name", String.class, null);
		Item item = container.addItem("Name 1");			// "Name 1" = Item-ID und Default-Caption
		item.getItemProperty("name").setValue("1");
		item = container.addItem("Name 2");
		item.getItemProperty("name").setValue("2");
		item = container.addItem("Name 3");
		item.getItemProperty("name").setValue("3");
		
		//...
		// Create a selection component bound to the container
		OptionGroup group = new OptionGroup("My Select", container);
		group.select("Name 1");
		layout.addComponent(group);

		// 6.5.2 Adding New Items
		// Create a selection component
		ComboBox select = new ComboBox("My ComboBox");
		// Add items with given item IDs
		select.addItem("Mercury");		// "Mercury" = Item-ID und Default-Caption
		select.addItem("Venus");
		select.addItem("Earth");
		layout.addComponent(select);
		
		// Create a selection component
		ComboBox select2 = new ComboBox("My Select");
		// Add an item with a generated ID
		Object itemId = select2.addItem();
		select2.setItemCaption(itemId, "The Sun");
		// Select the item
		select2.setValue(itemId);
		layout.addComponent(select2);
		
		// 6.5.3 Item Captions
		// ItemCaptionMode = EXPLICIT_DEFAULTS_ID
		// Create a selection component
		ComboBox select3 = new ComboBox("Moons of Mars");
		select3.setItemCaptionMode(ItemCaptionMode.EXPLICIT_DEFAULTS_ID);	// EXPLICIT_DEFAULTS_ID ist Default
		// Use the item ID also as the caption of this item
		select3.addItem(new Integer(1));
		// Set item caption for this item explicitly
		select3.addItem(2); // same as "new Integer(2)"
		select3.setItemCaption(2, "Deimos");
		layout.addComponent(select3);
		
		
		// ItemCaptionMode = ID
		ComboBox select4 = new ComboBox("Inner Planets");
		select4.setItemCaptionMode(ItemCaptionMode.ID);
		// A class that implements toString()
		class PlanetId extends Object implements Serializable {
			String planetName;
			PlanetId (String name) {
				planetName = name;
			}
			public String toString () {
				return "The Planet " + planetName;
			}
		}
		// Use such objects as item identifiers
		String planets[] = {"Mercury", "Venus", "Earth", "Mars"};
		for (int i = 0; i < planets.length; i++) {
			select4.addItem(new PlanetId(planets[i]));
		}
		layout.addComponent(select4);
		
		
		// ItemCaptionMode = PROPERTY
		ComboBox select5 = captionproperty();
		layout.addComponent(select5);
		
	
		// 6.5.4 Getting and Setting Selection
		Button selectButton = new Button("Select Mars", event -> {
			Planet oldValue = (Planet) select5.getValue();
			
			Notification notif = new Notification("Alter Wert: " + oldValue.getName());
			notif.setPosition(Position.BOTTOM_CENTER);
			notif.setDelayMsec(1000);
			notif.show(Page.getCurrent());
			
			BeanItemContainer<Planet> c = (BeanItemContainer<Planet>) select5.getContainerDataSource();
			Planet p = c.getIdByIndex(3);
			select5.setValue(p);
			if (select5.isReadOnly()) {
				select5.setReadOnly(false);
			} else {
				select5.setReadOnly(true);
			}
		});
		layout.addComponent(selectButton);
		
		
		// 6.5.5 Handling Selection Changes
		select5.addValueChangeListener(event -> {
			Planet itemId1 = (Planet) event.getProperty().getValue();	// The ItemID is a Planet instance
			Utils.log("(Chapter6UI kapitel65) itemId = " + itemId1);
			if (itemId1 != null) {
				Notification.show(itemId1.getName());
				Utils.log("(Chapter6UI kapitel65) Neuer Wert = " + itemId1.getName());
			}
		});
		
		
		// 6.5.6. Allowing Adding New Items
		select.setNewItemsAllowed(true);
		select.setImmediate(true);
		select2.setNewItemsAllowed(true);
		select2.setImmediate(true);
		select3.setNewItemsAllowed(true);
		select3.setImmediate(true);
		select4.setNewItemsAllowed(true);
		select4.setImmediate(true);
		select5.setNewItemsAllowed(true);
		select5.setImmediate(true);
		select5.setNewItemHandler(new AbstractSelect.NewItemHandler() {
			@Override
			public void addNewItem(String newItemCaption) {
				if (select5.isReadOnly()) {
					Notification.show("Dropdown ist ReadOnly!", Notification.Type.WARNING_MESSAGE);
					return;
				}
				int size = select5.size();
				select5.addItem(new Planet(size, newItemCaption));
			}
		});
		
		
		// 6.5.7. Multiple Selection
		// A selection component with some items
		ListSelect select6 = new ListSelect("My Selection");
		select6.addItems("Mercury", "Venus", "Earth", "Mars", "Jupiter", "Saturn", "Uranus", "Neptune");
		// Multiple selection mode
		select6.setMultiSelect(true);
		// Feedback on value changes
		select6.addValueChangeListener(
		new Property.ValueChangeListener() {
			public void valueChange(ValueChangeEvent event) {
				// Some feedback
				String value = event.getProperty().getValue().toString();
				Notification.show("Selected: " + value, Notification.Type.WARNING_MESSAGE);
			}
		});
		select6.setImmediate(true);
		layout.addComponent(select6);

	
	
		// 6.5.8. Item Icons
		group.addContainerProperty("icon", ThemeResource.class, new ThemeResource("icons/user.png"));
		group.setItemIconPropertyId("icon");
		group.setItemIcon("Name 1", new ThemeResource("../sampler/icons/comment_yellow.gif"));
	
	}

	
	
	// Fortsetzung Kapitel 6.5.3 Item Captions
	/** A bean with a "name" property. */
	public class Planet implements Serializable {
		private static final long serialVersionUID = 1L;
		
		private int id;
		private String name;
		public Planet(int id, String name) {
			this.id = id;
			this.name = name;
		}
		public String getName() {
			return name;
		}
		public Integer getId() {
			return id;
		}
	}
	private ComboBox captionproperty() {
		// Have a bean container to put the beans in
		BeanItemContainer<Planet> container = new BeanItemContainer<Planet>(Planet.class);
		// Put some example data in it
		container.addItem(new Planet(1, "Mercury"));
		Planet venus = new Planet(2, "Venus");
		container.addItem(venus);
		container.addItem(new Planet(3, "Earth"));
		container.addItem(new Planet(4, "Mars"));
		// Create a selection component bound to the container
		ComboBox select = new ComboBox("Planets", container);
		// Set the caption mode to read the caption directly
		// from the 'name' property of the bean
		select.setItemCaptionMode(ItemCaptionMode.PROPERTY);
		select.setItemCaptionPropertyId("name");
		select.setValue(venus);
		return select;
	}
	
	
	
}