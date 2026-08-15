package controller;

import application.AppContext;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import utils.DateAndTime;

import java.util.ArrayList;
import java.util.List;

import model.Patient;
import model.Staff;
import model.Dashboard;
import model.Medical;
import services.MedicalService;
import services.PatientService;
import services.StaffService;

public class DashboardController {

    @FXML private Label dateLabel;
    @FXML private Label totalPatientsLabel;
    @FXML private Label availableBedsLabel;
    @FXML private Label totalStaffLabel;
    private Dashboard dashboard;
    private PatientService patientService;
    private MedicalService medicalService;
    private StaffService staffService;
    
    @FXML private ProgressBar age0to18Bar;
    @FXML private ProgressBar age19to40Bar;
    @FXML private ProgressBar age41to65Bar;
    @FXML private ProgressBar age65PlusBar;

    @FXML private Label age0to18PercentageLabel;
    @FXML private Label age19to40PercentageLabel;
    @FXML private Label age41to65PercentageLabel;
    @FXML private Label age65PlusPercentageLabel;
    
    @FXML private TableView<Medical> criticalInventory;
    @FXML private TableColumn<Medical, String> itemColumn;
    @FXML private TableColumn<Medical, String> countColumn;
    
    @FXML public void initialize() {
        dateLabel.setText(DateAndTime.getFormattedDateTime());// Set the initial date and time
        Timeline clock = new Timeline(new KeyFrame(Duration.seconds(1), event -> {  // Create a timeline that follows the real world time
            dateLabel.setText(DateAndTime.getFormattedDateTime());
        }));
        
        clock.setCycleCount(Animation.INDEFINITE); // Keep running indefinitely
        clock.play();// Start the clock
        
        this.patientService = AppContext.getInstance().getPatientService();
        this.medicalService = AppContext.getInstance().getMedicalService();
        this.staffService = AppContext.getInstance().getStaffService();
        ArrayList<Patient> patientList = patientService.getPatients();
        List<Medical> medicalList = medicalService.getAll();
        ArrayList<Staff> staffList = staffService.getStaff();
        this.dashboard = new Dashboard();
        dashboard.updateDashboard(patientList, medicalList,staffList);
        
        totalPatientsLabel.setText(String.valueOf(dashboard.getTotalPatients()));
        availableBedsLabel.setText(String.valueOf(dashboard.getAvailableBeds()));
        totalStaffLabel.setText(String.valueOf(dashboard.getTotalStaff()));
        
        int[] ageGroupCount = dashboard.getAgeGroupCount();
        double age0to18 = (double) ageGroupCount[0] / dashboard.getTotalPatients();
        double age19to40 = (double) ageGroupCount[1] / dashboard.getTotalPatients();
        double age41to65 = (double) ageGroupCount[2] / dashboard.getTotalPatients();
        double age65Plus = (double) ageGroupCount[3] / dashboard.getTotalPatients();
        
        age0to18Bar.setProgress(age0to18); //e.g. 0.20
        age19to40Bar.setProgress(age19to40);
        age41to65Bar.setProgress(age41to65);
        age65PlusBar.setProgress(age65Plus);
        
        age0to18PercentageLabel.setText(String.format("%.0f%%", age0to18 * 100));
	    age19to40PercentageLabel.setText(String.format("%.0f%%", age19to40 * 100));
	    age41to65PercentageLabel.setText(String.format("%.0f%%", age41to65 * 100));
	    age65PlusPercentageLabel.setText(String.format("%.0f%%", age65Plus * 100));
	        	
	    itemColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
	    countColumn.setCellValueFactory(new PropertyValueFactory<>("count"));
	    criticalInventory.setItems(
	    	    FXCollections.observableArrayList(
	    	    dashboard.getCriticalInventory()
	    	    ));
    }
    
        
    
       
        
}
