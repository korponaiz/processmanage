package com.zolee.uicontrol;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.navigator.View;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinSession;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.VerticalLayout;
import com.zolee.domain.Employee;
import com.zolee.service.EmployeeService;

@SpringView(name = UserSettingsPage.NAME)
public class UserSettingsPage extends HorizontalLayout implements View {

	private static final long serialVersionUID = 1L;
	public static final String NAME ="UserSettings";
	
	private EmployeeService employeeService;
	
	@Autowired
	public void setEmployeeService(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	private VerticalLayout controlButtonLayout;
	private VerticalLayout mainLayout;
	private Button logoutButton;
	private Button homeButton;
	private Button settingsButton;
	private Button changeButton;
	private PasswordField oldPasswordField;
	private PasswordField newPasswordField;
	private PasswordField new2PasswordField;

	public UserSettingsPage() {
		
		controlButtonLayout = new VerticalLayout();
		mainLayout = new VerticalLayout();
		settingsButton = new Button("Settings");
		logoutButton = new Button("Logout");
		homeButton= new Button("Home");
		oldPasswordField = new PasswordField("Old password:");
		newPasswordField = new PasswordField("New password 1:");
		new2PasswordField = new PasswordField("New password 2:"); 
		changeButton = new Button("Change");

		logoutButton.setWidth("200px");
		settingsButton.setWidth("200px");
		homeButton.setWidth("200px");

		addComponents(controlButtonLayout, mainLayout);
		controlButtonLayout.addComponents(logoutButton, homeButton, settingsButton);
		mainLayout.addComponents(oldPasswordField, newPasswordField, new2PasswordField, changeButton);

		homeButton.addClickListener( e -> Page.getCurrent().setUriFragment("!"+UserPage.NAME));
		settingsButton.addClickListener( e -> Page.getCurrent().setUriFragment("!"+UserSettingsPage.NAME));
		changeButton.addClickListener( e -> changePassword());
		logoutButton.addClickListener( e -> logout());
	}
	
	private void changePassword() {
		Employee employee = employeeService.findByUserName(VaadinSession.getCurrent().getAttribute("employeename").toString());
		if(checkEmptyField()) {
			Notification.show("Empty Field!!", Notification.Type.ERROR_MESSAGE);
		} else if(!checkNewPasswordIsSame(employee)) {
			Notification.show("Password not match!", Notification.Type.ERROR_MESSAGE);
		}
		else {
			employee.setPassword(newPasswordField.getValue());
			employeeService.save(employee);
		}
	}

	private boolean checkNewPasswordIsSame(Employee employee) {
		if((employee.getPassword().equals(oldPasswordField.getValue())) && 
				(newPasswordField.getValue().equals(new2PasswordField.getValue()))) {
			return true;
		}
		return false;
	}
	
	private boolean checkEmptyField() {
		if(oldPasswordField.getValue().equals("") || newPasswordField.getValue().equals("") || 
				new2PasswordField.getValue().equals("")) {
			return true;
		}
		return false;
	}

	private void logout() {
		getUI().getNavigator().removeView(UserPage.NAME);
		getUI().getNavigator().removeView(UserSettingsPage.NAME);
		VaadinSession.getCurrent().setAttribute("employeename", null);
		Page.getCurrent().setUriFragment("");
	}

}
