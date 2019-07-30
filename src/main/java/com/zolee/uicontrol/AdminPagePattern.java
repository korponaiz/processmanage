package com.zolee.uicontrol;

import com.vaadin.navigator.View;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class AdminPagePattern extends HorizontalLayout implements View{

	private static final long serialVersionUID = 1L;

	private VerticalLayout controlButtonLayout;
	private Label currentUser;
	private Button adminPageButton;
	private Button editEmployeePageButton; 
	private Button editWorkstationPageButton; 
	private Button editUnitsPageButton; 
	private Button editUnitSequencePageButton;
	private Button editProductionSequencePageButton;
	private Button settingsButton;
	private Button logoutButton;
	private Button allHistoryButton;
	private String controlButtonsWidth;

	public AdminPagePattern() {

		controlButtonLayout = new VerticalLayout();
		currentUser = new Label("" );
		adminPageButton = new Button("Main Page");
		editEmployeePageButton = new Button("Employees");
		editWorkstationPageButton = new Button("Workstations");
		editUnitsPageButton = new Button("Units");
		editUnitSequencePageButton = new Button("UnitSequences");
		editProductionSequencePageButton = new Button("Prod. Sequences");
		allHistoryButton = new Button("All History");
		settingsButton = new Button("Settings");
		logoutButton = new Button("Logout");
		controlButtonsWidth = "160px";

		currentUser.setValue("Current user : " + VaadinSession.getCurrent().getAttribute("employeename").toString());

		
		adminPageButton.setWidth(controlButtonsWidth);
		editEmployeePageButton.setWidth(controlButtonsWidth);
		editWorkstationPageButton.setWidth(controlButtonsWidth);
		editUnitsPageButton.setWidth(controlButtonsWidth);
		editUnitSequencePageButton.setWidth(controlButtonsWidth);
		editProductionSequencePageButton.setWidth(controlButtonsWidth);
		settingsButton.setWidth(controlButtonsWidth);
		allHistoryButton.setWidth(controlButtonsWidth);
		logoutButton.setWidth(controlButtonsWidth);

		addComponent(controlButtonLayout);
		controlButtonLayout.addComponents(currentUser, logoutButton, adminPageButton, settingsButton, editEmployeePageButton, 
				editWorkstationPageButton, editUnitsPageButton, editUnitSequencePageButton, editProductionSequencePageButton, allHistoryButton);
		currentUser.setValue("Current user : " + VaadinSession.getCurrent().getAttribute("employeename").toString());
		adminPageButton.addClickListener( e -> Page.getCurrent().setUriFragment("!"+AdminPage.NAME));
		editEmployeePageButton.addClickListener( e -> Page.getCurrent().setUriFragment("!"+EditEmployeesPage.NAME));
		editWorkstationPageButton.addClickListener( e -> Page.getCurrent().setUriFragment("!"+EditWorkstationsPage.NAME));
		editUnitsPageButton.addClickListener( e -> Page.getCurrent().setUriFragment("!"+EditUnitsPage.NAME));
		editUnitSequencePageButton.addClickListener(e -> Page.getCurrent().setUriFragment("!"+EditUnitSequencePage.NAME));
		editProductionSequencePageButton.addClickListener(e -> Page.getCurrent().setUriFragment("!"+EditProductionSequencePage.NAME));
		allHistoryButton.addClickListener(e -> Page.getCurrent().setUriFragment("!"+AllHistoryPage.NAME));
		settingsButton.addClickListener( e -> Page.getCurrent().setUriFragment("!"+AdminSettingsPage.NAME));
		logoutButton.addClickListener( e -> logout());

	}

	private void logout() {
		getUI().getNavigator().removeView(AdminPage.NAME);
		getUI().getNavigator().removeView(EditEmployeesPage.NAME);
		getUI().getNavigator().removeView(EditWorkstationsPage.NAME);
		getUI().getNavigator().removeView(EditUnitsPage.NAME);
		getUI().getNavigator().removeView(EditUnitSequencePage.NAME);
		getUI().getNavigator().removeView(EditProductionSequencePage.NAME);
		getUI().getNavigator().removeView(AdminSettingsPage.NAME);
		getUI().getNavigator().removeView(AllHistoryPage.NAME);
		VaadinSession.getCurrent().setAttribute("employeename", null);
		Page.getCurrent().setUriFragment("");
	}

}
