package com.zolee.uicontrol;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.zolee.domain.ProductionHistory;
import com.zolee.service.ProductionHistoryService;

@SpringView(name = AdminPage.NAME)
public class AdminPage extends AdminPagePattern {

	private static final long serialVersionUID = 1L;
	public static final String NAME = "Admin";

	private ProductionHistoryService productionHistoryService;
	
	@Autowired
	public void setProductionHistoryService(ProductionHistoryService productionHistoryService) {
		this.productionHistoryService = productionHistoryService;
	}

	private HorizontalLayout gridLayout;
	private Grid<ProductionHistory> grid;

	public AdminPage() {

		gridLayout = new HorizontalLayout();
		grid = new Grid<>(ProductionHistory.class);
		grid.setColumns("unitSequence" , "productionSequence", "actiontype", "actualdate", "startTime", "stopTime", "employee", "description");
		
		grid.setWidth("1000px");
		grid.setHeight("1000px");
		gridLayout.setSpacing(true);
		gridLayout.setMargin(true);

		addComponent(gridLayout);
		gridLayout.addComponent(grid);

	}
	
	@PostConstruct
	private void init() {
		grid.setItems(productionHistoryService.findAll());
	}

	@Override
	public void enter(ViewChangeEvent event) {
	}

}
