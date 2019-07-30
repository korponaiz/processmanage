package com.zolee.uicontrol;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.server.VaadinSession;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.VerticalLayout;
import com.zolee.domain.Employee;
import com.zolee.service.EmployeeService;

@SpringView(name = AdminSettingsPage.NAME)
public class AdminSettingsPage extends AdminPagePattern{

	private static final long serialVersionUID = 1L;
	public static final String NAME = "PasswordChange";

	private EmployeeService employeeService;
	
	@Autowired
	public void setEmployeeService(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	private VerticalLayout mainLayout;
	private PasswordField oldPasswordField;
	private PasswordField newPasswordField;
	private PasswordField new2PasswordField;
	private Button changeButton;
	
	public AdminSettingsPage() {
		
		mainLayout = new VerticalLayout();
		oldPasswordField = new PasswordField("Old password:");
		newPasswordField = new PasswordField("New password 1:");
		new2PasswordField = new PasswordField("New password 2:"); 
		changeButton = new Button("Change");
		
		addComponent(mainLayout);
		mainLayout.addComponents(oldPasswordField, newPasswordField, new2PasswordField, changeButton);
		
		changeButton.addClickListener( e -> changePassword());
	}
	
	private void changePassword() {
		Employee employee = employeeService.findByUserName(VaadinSession.getCurrent().getAttribute("employeename").toString());
		if(checkEmptyField()) {
			Notification.show("Empty Field!!", Notification.Type.ERROR_MESSAGE);
		} else if(!checkNewPasswordIsSame(employee)) {
			Notification.show("Nem egyező jelszavak!", Notification.Type.ERROR_MESSAGE);
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

}
