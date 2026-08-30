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
