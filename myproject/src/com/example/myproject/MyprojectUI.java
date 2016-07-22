package com.example.myproject;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import javax.imageio.ImageIO;

import com.example.myproject.utils.Utils;
import com.vaadin.annotations.Theme;
import com.vaadin.server.BrowserWindowOpener;
import com.vaadin.server.ClassResource;
import com.vaadin.server.FileResource;
import com.vaadin.server.Page;
import com.vaadin.server.StreamResource;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.UserError;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.Position;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Link;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;



/**
 * Book of Vaadin.
 * Vaadin 7 Edition - 4th Revision.
 **/
@SuppressWarnings("serial")
@Theme("myproject")
//@Theme("base")
//@Theme("chameleon")
//@Theme("liferay")
//@Theme("reindeer")
//@Theme("runo")
//@Theme("valo")
public class MyprojectUI extends UI {
	public static final long serialVersionUID = 1L;
	
	FormLayout layout = new FormLayout();


	@Override
	protected void init(VaadinRequest request) {
		Utils.log("(MyprojectUI init) ");

		layout.setMargin(true);
		setContent(layout);

		Label label = new Label("Hello, your fragment is " + getPage().getUriFragment());
        layout.addComponent(label);		
        
        Map<String, String[]> parameter = request.getParameterMap();
        for (Entry<String, String[]> entry : parameter.entrySet()) {
        	Utils.log("(MyprojectUI init) entry key   = " + entry.getKey());
        	Utils.log("(MyprojectUI init) entry value = " + Arrays.asList(entry.getValue()));
        }
        
        
        
        
        VaadinSession session = VaadinSession.getCurrent();
        Collection<UI> uis = session.getUIs();
        Utils.log("(MyprojectUI init) uis.size() = " +uis.size());
        for (Iterator<UI> iterator = uis.iterator(); iterator.hasNext();) {
			UI ui = iterator.next();
			Utils.log("(MyprojectUI init) UI caption     = " +  ui.getCaption());
			Utils.log("(MyprojectUI init) UI description = " +  ui.getDescription());
		}
        
        
        
		kapitel25(layout);
		
		kapitel42(layout);
		kapitel422(layout);
		kapitel424(layout);
		
		kapitel431(layout);
		kapitel432(layout);
		kapitel433(layout);
		kapitel434(layout);
		
		kapitel442(layout);
		kapitel443(layout);
		kapitel444(layout);
		kapitel445(layout);
		
		kapitel451(layout);
		
		kapitel46(layout);
		kapitel461(layout);
		kapitel462(layout);
		
		kapitel477(layout);
	}

	
	
	// Kapitel 2.5 Creating and running a project in Eclipse
	private void kapitel25(FormLayout layout) {
		Button button = new Button("Click Me");
		
		
		// Ein Widget kann mehrere Listener für das selbe Ereignis haben
		
		button.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				layout.addComponent(new Label("Thank you for clicking!"));
			}
		});
		button.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				// Notification with default settings for a warning
				Notification notif = new Notification("Warning\nArea of reindeer husbandry", null, Notification.Type.WARNING_MESSAGE, false);

				// Customize it
				notif.setDelayMsec(20000);
				notif.setPosition(Position.BOTTOM_RIGHT);
				notif.setStyleName("mystyle");
				notif.setIcon(new ThemeResource("img/Warning_2.png"));

				// Show it in the page
				notif.show(Page.getCurrent());
			}
		});
		layout.addComponent(button);
		
	}
	
	
	// Kapitel 4.2 Building the UI
	private void kapitel42(FormLayout layout) {
		// UI Beispiel MyHierarchicalUI in eigenem Browserfenster
		Button hierarchicalUIButton = new Button("Hierarchical UI");
		BrowserWindowOpener opener = new BrowserWindowOpener(MyHierarchicalUI.class);
		opener.setFeatures("target=_top,resizable,status=1");
		opener.extend(hierarchicalUIButton);
		layout.addComponent(hierarchicalUIButton);
		
	}
	
	
	// Kapitel 4.2.2 Compositing Components
	private void kapitel422(FormLayout layout) {
		class MyView extends CustomComponent {
			TextField	entry 	= new TextField("Enter this");
			Label		display	= new Label("See this");
			Button		click	= new Button("Click This");
			
			public MyView() {
				Layout layout = new FormLayout();
				
				entry.setComponentError(new UserError("Bad value"));
				click.setComponentError(new UserError("Bad Click"));
				layout.addComponent(entry);
				layout.addComponent(display);
				layout.addComponent(click);
				
				setCompositionRoot(layout);
				
				setSizeFull();
			}
		}

		MyView myView = new MyView();
		layout.addComponent(myView);
	}
	
	
	// Kapitel 4.2.4 Accessing UI, Page, Session and Service
	private void kapitel424(FormLayout layout) {
		
		// Set the default locale of the UI
		UI.getCurrent().setLocale(new Locale("de_DE"));
		
		// Set the page title (window or tab caption)
		Page.getCurrent().setTitle("My Page");
		
		// Set a session attribute
		VaadinSession.getCurrent().setAttribute("myattrib", "hello");
		Utils.log("(MyprojectUI init) myattrib = " + VaadinSession.getCurrent().getAttribute("myattrib"));
		
		// Access the HTTP service parameters
		File basedir = VaadinService.getCurrent().getBaseDirectory();
		Utils.log("(MyprojectUI init) basedir  = " + basedir);
	}
	
	
	// Kapitel 4.3.1 Using Anonymous Classes
	private void kapitel431(FormLayout layout) {
		final Button button2 = new Button("Click It!", new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				if (event.getButton().getCaption().equals("Done!")) {
					event.getButton().setCaption("Click It!");
				} else {
					event.getButton().setCaption("Done!");
				}
			}
		});
		layout.addComponent(button2);

	}

	// Kapitel 4.3.2 Handling events in Java 8
	private void kapitel432(FormLayout layout) {
		layout.addComponent(new Button("Click Me!", event -> event.getButton().setCaption("You made me click!")));
		
		
		class Java8Buttons extends CustomComponent {
			public Java8Buttons() {
				setCompositionRoot(new HorizontalLayout(
						new Button("OK", this::ok),
						new Button("Cancel", this::cancel)));
			}
			
			public void ok(ClickEvent event) {
				event.getButton().setCaption("OK!");
			}
			public void cancel(ClickEvent event) {
				event.getButton().setCaption("Not OK!");
			}
		}
		
		
		layout.addComponent(new Java8Buttons());
	}
	
	
	// Kapitel 4.3.3 Implementing a listener in a regular class
	private void kapitel433(FormLayout layout) {
		class MyComposite extends CustomComponent implements Button.ClickListener {
			Button button;
			
			public MyComposite() {
				HorizontalLayout hLayout = new HorizontalLayout();
				button = new Button("Do not push this");
				button.addClickListener(this);
				hLayout.addComponent(button);
				
				setCompositionRoot(hLayout);
			}
			
			
			@Override
			public void buttonClick(ClickEvent event) {
				button.setCaption("Do not push this again");
			}
		}
		
		layout.addComponent(new MyComposite());
		
	}

		
	// Kapitel 4.3.4 Differentiating between event sources
	private void kapitel434(FormLayout layout) {
		class TheButtons extends CustomComponent implements Button.ClickListener {
			Button onebutton;
			Button toobutton;
			
			public TheButtons() {
				onebutton = new Button("Button one", this);
				toobutton = new Button("A Button too", this);
				
				Layout root = new HorizontalLayout();
				root.addComponent(onebutton);
				root.addComponent(toobutton);
				
				setCompositionRoot(root);
			}
			
			
			@Override
			public void buttonClick(ClickEvent event) {
				if (event.getButton() == onebutton) {
					onebutton.setCaption("Pushed one");
				} else if (event.getButton() == toobutton) {
					toobutton.setCaption("Pushed too");
				}
			}
		}
		
		layout.addComponent(new TheButtons());
		
	}

	

	// Kapitel 4.4.2 File resources
	private void kapitel442(FormLayout layout) {
		String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
		layout.addComponent(new Label(basepath));
		
		FileResource resource = new FileResource(new File(basepath + "/WEB-INF/images/image.png"));
		
		Image image = new Image("Image from file", resource);
		
		Link link = new Link("Link to the image file", resource);
		
		layout.addComponent(image);
		layout.addComponent(link);
	}
	
	// Kapitel 4.4.3 ClassLoader resources
	private void kapitel443(FormLayout layout) {
		// Die Adresse der Resource muss relativ zur Adresse der UI-Klasse (hier MyprojectUI) angegeben werden.
		ClassResource resource = new ClassResource("images/smiley.jpg");
		ClassResource resource2 = new ClassResource("images/image.png");
		
		Image image = new Image("Image1", resource);
		Image image2 = new Image("Image2", resource2);
		
		layout.addComponent(image);
		layout.addComponent(image2);
	}
	
	
	// Kapitel 4.4.4 Theme resources
	private void kapitel444(FormLayout layout) {
		// A theme resource in the current theme ("myproject")
		// Located in VAADIN/themes/myproject/img/Warning_2.jpg
		ThemeResource resource = new ThemeResource("img/Warning_2.png");
		
		// Use the resource
		Image image = new Image("My Theme Image", resource);
		
		layout.addComponent(image);
	}
	
	// Kapitel 4.4.5 Stream resources
	private void kapitel445(FormLayout layout) {
		class MyImageSource implements StreamResource.StreamSource {
			ByteArrayOutputStream 	imageBuffer = null;
			int 					reloads 	= 0;
			
			// We need to implement this method that returns the resource as stream
			public InputStream getStream() {
				// Create an image and draw something on it
				BufferedImage image = new BufferedImage(200, 200, BufferedImage.TYPE_INT_RGB);
				Graphics drawable = image.getGraphics();
				
				drawable.setColor(Color.lightGray);
				drawable.fillRect(0, 0, 200, 200);
				drawable.setColor(Color.yellow);
				drawable.fillOval(25, 25, 150, 150);
				drawable.setColor(Color.blue);
				drawable.drawRect(0, 0, 199, 199);
				drawable.setColor(Color.black);
				drawable.drawString("Reloads = " + reloads, 75, 100);
				
				reloads = reloads + 1;
				
				try {
					// Write the image to a buffer
					imageBuffer = new ByteArrayOutputStream();
					ImageIO.write(image,  "png", imageBuffer);
//					imageBuffer.close();
					
					// Return a stream from the buffer
					return new ByteArrayInputStream(imageBuffer.toByteArray());
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				return null;
			}
		}
		
		// Create an instance of our stream source
		StreamResource.StreamSource imageSource = new MyImageSource();
		
		// Create a resource that uses the stream source and give it a name.
		// The constructor will automatically register the resource in the application.
		StreamResource resource = new StreamResource(imageSource, "myimage.png");
		
		// Create an image component that gets its contents from the resource
		layout.addComponent(new Image("Image title", resource));
		layout.addComponent(new Image("Image title 2", resource));
	}
	
	// Kapitel 4.5.1 Error indicator and message
	private void kapitel451(FormLayout layout) {
		TextField	textfield 	= new TextField();
		Button		button		= new Button("Click Here");
		
		textfield.setComponentError(new UserError("Bad value"));
		button.setComponentError(new UserError("Bad Click"));
		
		layout.addComponent(textfield);
		layout.addComponent(button);
	}


	// Kapitel 4.6 Notifications
	private void kapitel46(FormLayout layout) {
		Button button1 = new Button("Message Example");
		button1.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				Notification.show("This is the caption", "This is the description", Notification.Type.WARNING_MESSAGE);
			}
		});
		
		Button button2 = new Button("Message HTML");
		button2.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				new Notification("This is a warning", "<br/>This ist the <b>last</b> warning", Notification.Type.WARNING_MESSAGE, true).show(Page.getCurrent());

			}
		});
		
		Button button3 = new Button("Message Newline");
		button3.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				new Notification("This is a warning", "\r\nThis ist the last warning", Notification.Type.WARNING_MESSAGE, false).show(Page.getCurrent());

			}
		});
		
		Button button4 = new Button("Message HTML Description");
		button4.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				new Notification(null, "This is a warning<br/>This ist the <b>last</b> warning", Notification.Type.WARNING_MESSAGE, true).show(Page.getCurrent());

			}
		});
		
		Button button5 = new Button("Message Newline Caption");
		button5.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				new Notification("This is a warning\r\nThis ist the last warning", null, Notification.Type.WARNING_MESSAGE, false).show(Page.getCurrent());

			}
		});
		
		
		HorizontalLayout buttonLayout1 = new HorizontalLayout();
		buttonLayout1.addComponent(button1);
		buttonLayout1.addComponent(button2);
		buttonLayout1.addComponent(button3);
		buttonLayout1.addComponent(button4);
		buttonLayout1.addComponent(button5);
		
		layout.addComponent(buttonLayout1);
		
	}


	// Kapitel 4.6.1 Notification Type
	private void kapitel461(FormLayout layout) {
		Button humanizedButton = new Button("HUMANIZED_MESSAGE");
		humanizedButton.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				Notification.show("Caption HUMANIZED_MESSAGE", "Description HUMANIZED_MESSAGE", Notification.Type.HUMANIZED_MESSAGE);
			}
		});
		
		Button warningButton = new Button("WARNING_MESSAGE");
		warningButton.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				Notification.show("Caption WARNING_MESSAGE", "Description WARNING_MESSAGE", Notification.Type.WARNING_MESSAGE);
			}
		});
		

		Button errorButton = new Button("ERROR_MESSAGE");
		errorButton.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				Notification.show("Caption HUMANIZED_ERROR_MESSAGE", "Description ERROR_MESSAGE", Notification.Type.ERROR_MESSAGE);
			}
		});
		
		Button assistiveButton = new Button("ASSISTIVE_NOTIFICATION");
		assistiveButton.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				// Notification.Type.ASSISTIVE_NOTIFICATION funktioniert hier nicht:
				// only accessible for assistive devices, invisible for visual users
				Notification.show("Caption ASSISTIVE_NOTIFICATION", "Description ASSISTIVE_NOTIFICATION", Notification.Type.ASSISTIVE_NOTIFICATION);
			}
		});
		
		Button trayButton = new Button("TRAY_NOTIFICATION");
		trayButton.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				Notification.show("Caption TRAY_NOTIFICATION", "Description TRAY_NOTIFICATION", Notification.Type.TRAY_NOTIFICATION);
			}
		});
		
		
		
		HorizontalLayout buttonLayout2 = new HorizontalLayout();
		buttonLayout2.addComponent(humanizedButton);
		buttonLayout2.addComponent(warningButton);
		buttonLayout2.addComponent(errorButton);
		buttonLayout2.addComponent(assistiveButton);
		buttonLayout2.addComponent(trayButton);
		
		layout.addComponent(buttonLayout2);
		
	}


	// Kapitel 4.6.2 Styling with CSS 
	private void kapitel462(FormLayout layout) {
		Button button = new Button("Notification + CSS");
		
		button.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				// Notification with default settings for a warning
				Notification notif = new Notification("Warning\nArea of reindeer husbandry", null, Notification.Type.WARNING_MESSAGE, false);

				// Customize it
				notif.setDelayMsec(20000);
				notif.setPosition(Position.BOTTOM_RIGHT);
				notif.setStyleName("mystyle");
				notif.setIcon(new ThemeResource("img/Warning_2.png"));

				// Show it in the page
				notif.show(Page.getCurrent());
			}
		});
		
		layout.addComponent(button);
	}


	// Kapitel 4.7.7 Closing a session 
	private void kapitel477(FormLayout layout) {
		Button button = new Button("Redirect");
		
		button.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				// Redirect from the page
				getUI().getPage().setLocation("/myproject/MyHierarchicalUI");
				
				// Close the Session
				getSession().close();
			}
		});
		
		layout.addComponent(button);
	}



}