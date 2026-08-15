package controller;

import application.AppContext;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.util.Duration;
import utils.DateAndTime;

import java.util.ArrayList;


import model.Patient;
import services.PatientService;

public class DashboardController {

    @FXML private Label dateLabel;
    @FXML private Label totalPatientsLabel;
    @FXML private Label availableBedsLabel;
    @FXML private Label occupancyRateLabel;
    private model.Dashboard dashboard;
    private PatientService patientService;
    @FXML public void initialize() {
        // Set the initial date and time
        dateLabel.setText(DateAndTime.getFormattedDateTime());

        // Create a Timeline that follows the real world time
        Timeline clock = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            dateLabel.setText(DateAndTime.getFormattedDateTime());
        }));
        
        clock.setCycleCount(Animation.INDEFINITE); // Keep running indefinitely
        clock.play();// Start the clock
    
        this.patientService = AppContext.getInstance().getPatientService();
        displayPatients();
        displayAvailableBeds();
    }
    
    public void displayPatients() {
        if (patientService != null) {
            ArrayList<Patient> list = patientService.getPatients();
            
            // 2. Update the Label with the total list size!
            totalPatientsLabel.setText(String.valueOf(list.size()));
        } 
        else {
            totalPatientsLabel.setText("0");
        }
    }


	public void displayAvailableBeds() {
		if (patientService != null) {
			
		}
		else {
			availableBedsLabel.setText("100");
		}
	}
}
