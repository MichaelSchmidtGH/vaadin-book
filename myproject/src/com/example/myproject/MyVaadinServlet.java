package com.example.myproject;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;

import com.example.myproject.utils.Utils;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.SessionDestroyEvent;
import com.vaadin.server.SessionDestroyListener;
import com.vaadin.server.SessionInitEvent;
import com.vaadin.server.SessionInitListener;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.VaadinServletService;


@WebServlet(value = {"/VAADIN/*"}, asyncSupported = true)
@VaadinServletConfiguration(productionMode = false, ui = MyprojectUI.class, widgetset="com.vaadin.DefaultWidgetSet")
public class MyVaadinServlet extends VaadinServlet implements SessionInitListener, SessionDestroyListener {
	public static final long serialVersionUID = 1L;
	
	@Override
	protected void servletInitialized() throws ServletException {
		super.servletInitialized();
		Utils.log("(MyVaadinServlet servletInitialized) ");

		VaadinServletService servletService = getService();
		Utils.log("(MyVaadinServlet servletInitialized) base directory = " + servletService.getBaseDirectory());
		servletService.addSessionInitListener(this);
		
		
		VaadinServlet servlet = getCurrent();
		Utils.log("(MyVaadinServlet servletInitialized) servlet name = " + servlet.getServletName());
		
		Enumeration<String> paramNames = getInitParameterNames();
		Utils.log("(MyVaadinServlet servletInitialized) paramNames = " + paramNames);
		while (paramNames.hasMoreElements()) {
			Utils.log("(MyVaadinServlet servletInitialized) init parameter name: " + paramNames.nextElement());
		}
	}
	
	@Override
	public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
		super.service(request, response);
//		Utils.log("(MyprojectUI.Servlet service) ");
//		Enumeration<String> enumer = request.getAttributeNames();
//		while(enumer.hasMoreElements()) {
//			Utils.log("(MyprojectUI.Servlet service) " + enumer.nextElement());
//		}
		request.getParameterNames();
		Enumeration<String> paramNames = request.getParameterNames();
		while (paramNames.hasMoreElements()) {
			Utils.log("(MyVaadinServlet service) init parameter name: " + paramNames.nextElement());
		}
	}
	
	
	@Override
	public void sessionInit(SessionInitEvent event) {
		Utils.log("(MyVaadinServlet sessionInit) ");
	}
	@Override
	public void sessionDestroy(SessionDestroyEvent event) {
		Utils.log("(MyVaadinServlet sessionDestroy) ");
	}
}

