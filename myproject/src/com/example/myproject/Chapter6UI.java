package com.example.myproject;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.Locale;

import com.example.myproject.utils.Utils;
import com.vaadin.annotations.Theme;
import com.vaadin.server.AbstractErrorMessage.ContentMode;
import com.vaadin.server.ErrorMessage.ErrorLevel;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.UserError;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.InlineDateField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
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
		

		// BEGIN - Exkurs Umwandlung UTF-8 nach Latin-1 und zurück
		// Funktioniert so nicht. Funktionierendes Beispiel suchen. 
		CharsetEncoder encoder = Charset.forName("UTF-8").newEncoder();
		CharBuffer in  = CharBuffer.allocate(100);
		in.put("äöüÄÖÜß!§$%&/()=?\\}][{²@µ_-.:,;#'+*~´`|<>");
		ByteBuffer out = ByteBuffer.allocate(100);
		
		encoder.reset();
		Utils.log("(Chapter6UI kapitel63) in.remaining  = " + in.remaining());
		Utils.log("(Chapter6UI kapitel63) out.remaining = " + out.remaining());
		encoder.encode(in, out, true);
		encoder.flush(out);
		Utils.log("(Chapter6UI kapitel63) in.remaining  = " + in.remaining());
		Utils.log("(Chapter6UI kapitel63) out.remaining = " + out.remaining());
		
		in.rewind();
		Utils.log("(Chapter6UI kapitel63) in  (UTF-8) byte  = " + in.get());

		out.rewind();
		Utils.log("(Chapter6UI kapitel63) out.remaining = " + out.remaining());
		byte[] dst = new byte[100];
		out.get(dst);
		String outString = new String(dst);
		Utils.log("(Chapter6UI kapitel63) outString = " + outString);
//		while(out.hasRemaining()) {
//			Utils.log("(Chapter6UI kapitel63) out (Latin1) byte = " + out.get());
//		}
		
		
		CharsetDecoder decoder = Charset.forName("UTF-8").newDecoder();
		// END - Exkurs Umwandlung UTF-8 nach Latin-1 und zurück
		
	}
	
}