package com.example.myproject;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinServlet;


@WebServlet(value = "/Chapter6/*", asyncSupported = true)
@VaadinServletConfiguration(productionMode = false, ui = Chapter6UI.class, widgetset="com.vaadin.DefaultWidgetSet")
public class Chapter6UIServlet extends VaadinServlet {
	public static final long serialVersionUID = 1L;
}

