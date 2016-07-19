package com.example.myproject;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinServlet;


@WebServlet(value = {"/VAADIN/*"}, asyncSupported = true)
@VaadinServletConfiguration(productionMode = false, ui = MyprojectUI.class)
public class MyVaadinServlet extends VaadinServlet {
	public static final long serialVersionUID = 1L;
}

