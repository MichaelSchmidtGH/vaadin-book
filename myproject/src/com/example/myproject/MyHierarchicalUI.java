package com.example.myproject;



import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import com.example.myproject.utils.Utils;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.Tree;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class MyHierarchicalUI extends UI {

	@WebServlet(value = {"/MyHierarchicalUI/*"}, asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = MyHierarchicalUI.class)
	public static class Servlet extends VaadinServlet {
		@Override
		protected void servletInitialized() throws ServletException {
			super.servletInitialized();
			Utils.log("(MyHierarchicalUI.Servlet servletInitialized) ");
		}
	}

	@Override
	protected void init(VaadinRequest request) {
		// The root of the component hierarchy
		VerticalLayout content = new VerticalLayout();
		content.setSizeFull();										// Use entire window
		setContent(content);
		
		// Add some components
		content.addComponent(new Label("Hello"));
		
		// Layout inside Layout
		HorizontalLayout hor = new HorizontalLayout();
		hor.setSizeFull();											// Use all available space
		
		// A couple of horizontally layout components
		Tree tree = new Tree("My Tree", createTreeContent());
		hor.addComponent(tree);
		
		Table table = new Table("My Table", generateContent());
		table.setSizeFull();
		hor.addComponent(table);
		hor.setExpandRatio(table, 1); 								// Expand to fill
		
		content.addComponent(hor);
		content.setExpandRatio(hor, 1); 							// Expand to fill
	}

	
    public static IndexedContainer generateContent() {
        IndexedContainer result = new IndexedContainer();
        result.addContainerProperty("Name", String.class, null);
        result.addContainerProperty("City", String.class, null);
        result.addContainerProperty("Year", Integer.class, null);
        
        addContent(result);
        return result;
    }
    
	@SuppressWarnings("unchecked")
	public static void addContent(IndexedContainer container) {
        String[] firstnames = new String[]{"Isaac", "Ada", "Charles", "Douglas"};
        String[] lastnames  = new String[]{"Newton", "Lovelace", "Darwin", "Adams"};
        String[] cities     = new String[]{"London", "Oxford", "Innsbruck", "Turku"};
        for (int i=0; i<100; i++) {
            Object itemId = container.addItem();
            String name = firstnames[(int) (Math.random()*4)] + " " + lastnames[(int) (Math.random()*4)];
            container.getItem(itemId).getItemProperty("Name").setValue(name);
            String city = cities[(int) (Math.random()*4)];
            container.getItem(itemId).getItemProperty("City").setValue(city);
            Integer year = 1800 + (int) (Math.random()*200);
            container.getItem(itemId).getItemProperty("Year").setValue(year);
        }
    }
    


	public static HierarchicalContainer createTreeContent() {
        final Object[] inventory = new Object[] {
                "root",
                "+5 Quarterstaff (blessed)",
                "+3 Elven Dagger (blessed)",
                "+5 Helmet (greased)",
                new Object[] {"Sack",
                        "Pick-Axe",
                        "Lock Pick",
                        "Tinning Kit",
                        "Potion of Healing (blessed)",
                },
                new Object[] {"Bag of Holding",
                        "Potion of Invisibility",
                        "Magic Marker",
                        "Can of Grease (blessed)",
                },
                new Object[] {"Chest",
                        "Scroll of Identify",
                        "Scroll of Genocide",
                        "Towel",
                        new Object[] {"Large Box",
                                "Bugle",
                                "Oil Lamp",
                                "Figurine of Vaadin",
                                "Expensive Camera",
                        },
                        "Tin Opener",
                },
        };
        HierarchicalContainer container = new HierarchicalContainer();
        
        // A property that holds the caption is needed for ITEM_CAPTION_MODE_PROPERTY
        container.addContainerProperty("caption", String.class, "");
        
        new Object() {
            @SuppressWarnings("unchecked")
			public void put(Object[] data, Object parent, HierarchicalContainer container) {
                for (int i=1; i<data.length; i++) {
                    if (data[i].getClass() == String.class) {
                        // Support both ITEM_CAPTION_MODE_ID and ITEM_CAPTION_MODE_PROPERTY
                        container.addItem(data[i]);
                        container.getItem(data[i]).getItemProperty("caption").setValue(data[i]);
                        container.setParent(data[i], parent);
                        container.setChildrenAllowed(data[i], false);
                    } else {// It's an Object[]
                        Object[] sub = (Object[]) data[i];
                        String name = (String) sub[0];

                        // Support both ITEM_CAPTION_MODE_ID and ITEM_CAPTION_MODE_PROPERTY
                        container.addItem(name);
                        container.getItem(name).getItemProperty("caption").setValue(name);
                        put(sub, name, container);
                        container.setParent(name, parent);
                    }
                }
            }
        }.put(inventory, null, container);
        
        return container;
    }



}
