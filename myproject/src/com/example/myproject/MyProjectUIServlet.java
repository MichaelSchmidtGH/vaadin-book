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


@WebServlet(value = "/*", asyncSupported = true)
@VaadinServletConfiguration(productionMode = false, ui = MyprojectUI.class, widgetset="com.vaadin.DefaultWidgetSet")
public class MyProjectUIServlet extends VaadinServlet implements SessionInitListener, SessionDestroyListener {
	public static final long serialVersionUID = 1L;
	
	@Override
	protected void servletInitialized() throws ServletException {
		super.servletInitialized();
		Utils.log("(MyprojectUIServlet servletInitialized) ");

		VaadinServletService servletService = getService();
		Utils.log("(MyprojectUIServlet servletInitialized) base directory = " + servletService.getBaseDirectory());
		servletService.addSessionInitListener(this);
		
		
		VaadinServlet servlet = getCurrent();
		Utils.log("(MyprojectUIServlet servletInitialized) servlet name = " + servlet.getServletName());
		
		Enumeration<String> paramNames = getInitParameterNames();
		Utils.log("(MyprojectUIServlet servletInitialized) paramNames = " + paramNames);
		while (paramNames.hasMoreElements()) {
			Utils.log("(MyprojectUIServlet servletInitialized) init parameter name: " + paramNames.nextElement());
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
			Utils.log("(MyprojectUIServlet service) init parameter name: " + paramNames.nextElement());
		}
	}
	
	
	@Override
	public void sessionInit(SessionInitEvent event) {
		Utils.log("(MyprojectUIServlet sessionInit) ");
	}
	@Override
	public void sessionDestroy(SessionDestroyEvent event) {
		Utils.log("(MyprojectUIServlet sessionDestroy) ");
	}
}

