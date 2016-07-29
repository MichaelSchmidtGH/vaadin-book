package com.example.myproject;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinServlet;


@WebServlet(value = "/Chapter6/MenuBar/*", asyncSupported = true)
@VaadinServletConfiguration(productionMode = false, ui = Chapter625.class, widgetset="com.vaadin.DefaultWidgetSet")
public class Chapter625Servlet extends VaadinServlet {
	public static final long serialVersionUID = 1L;
}
