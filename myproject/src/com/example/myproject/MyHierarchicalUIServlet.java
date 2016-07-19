package com.example.myproject;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinServlet;

@WebServlet(value = {"/MyHierarchicalUI/*"}, asyncSupported = true)
@VaadinServletConfiguration(productionMode = false, ui = MyHierarchicalUI.class)
public class MyHierarchicalUIServlet extends VaadinServlet {
	public static final long serialVersionUID = 1L;
}
