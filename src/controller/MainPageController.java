package controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import utils.DateAndTime;

public class MainPageController {
	@FXML
	private StackPane contentArea;
	@FXML
	private SidebarController sidebarController;
	@FXML 
	private Label dateLabel;

	@FXML
	public void initialize(){

		sidebarController.setMainController(this);
		loadPage("Dashboard.fxml");
		
		dateLabel.setText(DateAndTime.getFormattedDateTime());// Set the initial date and time
        Timeline clock = new Timeline(new KeyFrame(Duration.seconds(1), event -> {  // Create a timeline that follows the real world time
            dateLabel.setText(DateAndTime.getFormattedDateTime());
        }));
        
        clock.setCycleCount(Animation.INDEFINITE); // Keep running indefinitely
        clock.play();// Start the clock
	}
	
	public void loadPage(String page){

	    try{

	    	FXMLLoader loader = new FXMLLoader(
	    	            getClass().getResource("/gui/" + page)
	    	        );
	        Parent root = loader.load();
	        
	        String css = getClass().getResource("/css/application.css").toExternalForm();
	        if (!root.getStylesheets().contains(css)) {
	            root.getStylesheets().add(css);
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
