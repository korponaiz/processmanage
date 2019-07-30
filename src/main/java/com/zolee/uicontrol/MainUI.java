package com.zolee.uicontrol;

import javax.servlet.annotation.WebServlet;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.Page.UriFragmentChangedEvent;
import com.vaadin.server.Page.UriFragmentChangedListener;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.UI;

@SuppressWarnings("deprecation")
@Theme("valo")
@SpringUI
public class MainUI extends UI {

	private static final long serialVersionUID = 1L;
	
	private SpringViewProvider springViewProvider;
	
	@Autowired
	public void setSpringViewProvider(SpringViewProvider springViewProvider) {
		this.springViewProvider = springViewProvider;
	}

	private Navigator navigator;
	
	@Override
	protected void init(VaadinRequest request) {
		
		navigator = new Navigator(this, this);
		navigator.addProvider(springViewProvider);
		
		getNavigator().addView(LoginPage.NAME, LoginPage.class);
		getNavigator().setErrorView(LoginPage.class);

		Page.getCurrent().addUriFragmentChangedListener(new UriFragmentChangedListener() {
			
			@Override
			public void uriFragmentChanged(UriFragmentChangedEvent event) {
				controller(event.getUriFragment());
			}
		});
		
		controller("");
	}

	private void controller(String way) {
		if(getSession().getAttribute("employeename") != null && getSession().getAttribute("employeeright").equals("admin")){
			getNavigator().addView(AdminPage.NAME, AdminPage.class);
			getNavigator().addView(EditEmployeesPage.NAME, EditEmployeesPage.class);
			getNavigator().addView(EditUnitsPage.NAME, EditUnitsPage.class);
			getNavigator().addView(EditWorkstationsPage.NAME, EditWorkstationsPage.class);
			getNavigator().addView(EditUnitSequencePage.NAME, EditUnitSequencePage.class);
			getNavigator().addView(EditProductionSequencePage.NAME, EditProductionSequencePage.class);
			getNavigator().addView(AdminSettingsPage.NAME, AdminSettingsPage.class);
			getNavigator().addView(AllHistoryPage.NAME, AllHistoryPage.class);
			if(way.equals("!EditEmployees")) {
				getNavigator().navigateTo(EditEmployeesPage.NAME);
			}else if(way.equals("!EditUnits")) {
				getNavigator().navigateTo(EditUnitsPage.NAME);
			}else if(way.equals("!EditWorkstations")) {
				getNavigator().navigateTo(EditWorkstationsPage.NAME);
			}else if(way.equals("!EditUnitSequence")) {
				getNavigator().navigateTo(EditUnitSequencePage.NAME);
			}else if(way.equals("!EditProductionSequence")) {
				getNavigator().navigateTo(EditProductionSequencePage.NAME);
			}else if(way.equals("!PasswordChange")){
				getNavigator().navigateTo(AdminSettingsPage.NAME);
			}else if(way.equals("!AllHistory")){
				getNavigator().navigateTo(AllHistoryPage.NAME);
			}else{
				getNavigator().navigateTo(AdminPage.NAME);
			}
		}else if(getSession().getAttribute("employeename") != null && getSession().getAttribute("employeeright").equals("user")) {
			getNavigator().addView(UserPage.NAME, UserPage.class);
			getNavigator().addView(UserSettingsPage.NAME, UserSettingsPage.class);
			if(way.equals("!UserSettings")) {
				getNavigator().navigateTo(UserSettingsPage.NAME);
			}else{
				getNavigator().navigateTo(UserPage.NAME);
			}
		}else{
			getNavigator().navigateTo(LoginPage.NAME);
		}
		
	}
	
	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = MainUI.class)
	public static class Servlet extends VaadinServlet {
	}

}
