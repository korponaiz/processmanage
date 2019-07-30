package com.zolee.uicontrol;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinSession;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.zolee.authenticating.Authentication;
import com.zolee.service.EmployeeService;

@SpringView(name = LoginPage.NAME)
public class LoginPage extends VerticalLayout implements View{

	private static final long serialVersionUID = 1L;
	public static final String NAME = "";

	private Authentication authenticationService;
	
	@Autowired
	public void setAuthenticationService(Authentication authenticationService) {
		this.authenticationService = authenticationService;
	}
	
	private EmployeeService employeeService;
	
	@Autowired
	public void setEmployeeService(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	private TextField username;
	private PasswordField password;
	private Panel panel;
	private FormLayout content;
	private Button okButton;

	public LoginPage() {
		
		panel = new Panel("Login");
		content = new FormLayout();
		username = new TextField("Username");
		password = new PasswordField("Password");
		okButton = new Button("Login");
		username.setWidth("200px");
		password.setWidth("200px");
		okButton.setWidth("200px");

		panel.setSizeUndefined();
		addComponent(panel);
		content.addComponents(username, password, okButton);
		content.setSizeUndefined();
		content.setMargin(true);
		panel.setContent(content);
		setComponentAlignment(panel, Alignment.MIDDLE_CENTER);

		okButton.setClickShortcut(KeyCode.ENTER);
		
		okButton.addClickListener( e -> authenticate());
	}
	
	private void authenticate() {
		if(authenticationService.authenticate(username.getValue(), password.getValue()) && employeeService.findByUserName(username.getValue()).getRight().equals("admin")){
			VaadinSession.getCurrent().setAttribute("employeename", username.getValue());
			VaadinSession.getCurrent().setAttribute("employeeright", "admin");
			getUI().getNavigator().addView(AdminPage.NAME, AdminPage.class);
			getUI().getNavigator().addView(EditEmployeesPage.NAME, EditEmployeesPage.class);
			getUI().getNavigator().addView(EditUnitsPage.NAME, EditUnitsPage.class);
			getUI().getNavigator().addView(EditWorkstationsPage.NAME, EditWorkstationsPage.class);
			getUI().getNavigator().addView(EditUnitSequencePage.NAME, EditUnitSequencePage.class);
			getUI().getNavigator().addView(EditProductionSequencePage.NAME, EditProductionSequencePage.class);
			getUI().getNavigator().addView(AdminSettingsPage.NAME, AdminSettingsPage.class);
			getUI().getNavigator().addView(AllHistoryPage.NAME, AllHistoryPage.class);
			getUI().getNavigator().setErrorView(LoginPage.class);
			Page.getCurrent().setUriFragment("!"+AdminPage.NAME);
		}else if(authenticationService.authenticate(username.getValue(), password.getValue()) && employeeService.findByUserName(username.getValue()).getRight().equals("user")) {
			VaadinSession.getCurrent().setAttribute("employeename", username.getValue());
			VaadinSession.getCurrent().setAttribute("employeeright", "user");
			getUI().getNavigator().addView(UserPage.NAME, UserPage.class);
			getUI().getNavigator().addView(UserSettingsPage.NAME, UserSettingsPage.class);
			getUI().getNavigator().setErrorView(LoginPage.class);
			Page.getCurrent().setUriFragment("!"+UserPage.NAME);
		}else{
			Notification.show("Invalid credentials", Notification.Type.ERROR_MESSAGE);
		}
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		
	}
}
