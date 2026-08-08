package controller;

import javafx.fxml.FXML;

import javafx.event.ActionEvent;

public class SidebarController {
	private MainPageController mainController;
	
	public void setMainController(MainPageController controller) {
		this.mainController = controller;
	}

	// Event Listener on Button.onAction
	@FXML
	public void dashboardClicked(ActionEvent event) {
		mainController.loadPage("Dashboard.fxml");

	}
	// Event Listener on Button.onAction
	//open doctor page
	@FXML
	public void doctorsClicked(ActionEvent event) {
		mainController.loadPage("Doctor.fxml");

	}
	// Event Listener on Button.onAction
	@FXML
	public void patientsClicked(ActionEvent event) {
		mainController.loadPage("Patient.fxml");

	}
	// Event Listener on Button.onAction
	@FXML
	public void medicalClicked(ActionEvent event) {
		System.out.println("Medical page");

	}
	// Event Listener on Button.onAction
	@FXML
	public void laboratoriesClicked(ActionEvent event) {
		System.out.println("Laboratories page");

	}
	// Event Listener on Button.onAction
	@FXML
	public void facilitiesClicked(ActionEvent event) {
		System.out.println("Facilities page");

	}
	// Event Listener on Button.onAction
	@FXML
	public void staffClicked(ActionEvent event) {
		mainController.loadPage("Staff.fxml");

	}
	// Event Listener on Button.onAction
	@FXML
	public void exitClicked(ActionEvent event) {
		System.exit(0);

	}
}
