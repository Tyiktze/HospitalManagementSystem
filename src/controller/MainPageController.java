package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

public class MainPageController {
	@FXML
	private StackPane contentArea;
	@FXML
	private SidebarController sidebarController;
	
	private HospitalManagement hospital = new HospitalManagement();	//Contains the actual array

	@FXML
	public void initialize(){

		sidebarController.setMainController(this);
		
		loadPage("Dashboard.fxml");

	}
	
	public void loadPage(String page){

	    try{

	    	FXMLLoader loader = new FXMLLoader(
	    	            getClass().getResource("/gui/" + page)
	    	        );
	        Parent root = loader.load();
	        
	        Object controller = loader.getController();

	        if (controller instanceof StaffController sc) {
	            sc.setHospitalManagement(hospital);	//pass the array into that specific controller
	        }
	        

	        contentArea.getChildren().clear();
	        contentArea.getChildren().add(root);

	        StackPane.setAlignment(root, javafx.geometry.Pos.CENTER);
	        


	    }
	    catch(Exception e){
	        e.printStackTrace();
	    }

	}
}
