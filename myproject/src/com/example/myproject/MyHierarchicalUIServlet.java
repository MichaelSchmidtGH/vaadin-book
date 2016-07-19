package com.example.myproject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import com.example.myproject.utils.Utils;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinServlet;

@WebServlet(value = {"/MyHierarchicalUI/*"}, asyncSupported = true)
@VaadinServletConfiguration(productionMode = false, ui = MyHierarchicalUI.class)
public class MyHierarchicalUIServlet extends VaadinServlet {
	public static final long serialVersionUID = 1L;

	@Override
	protected void servletInitialized() throws ServletException {
		super.servletInitialized();
		Utils.log("(MyHierarchicalUIServlet servletInitialized) ");
	}

}
