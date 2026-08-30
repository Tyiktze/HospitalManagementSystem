package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import application.AppContext;
import javafx.event.ActionEvent;
import model.User;
import services.LoginService;

public class LoginController {
	@FXML
	private TextField txtUsername;
	@FXML
	private TextField txtPassword;
	@FXML
	private Label checkMsg;
	
	private LoginService loginService;
	
	public void initialize() {
		this.loginService = AppContext.getInstance().getLoginService();
	}

	// Event Listener on Button.onAction
	@FXML
	public void btnLoginClicked(ActionEvent event) {
		String username = txtUsername.getText();
		String password = txtPassword.getText();
		
		User user = loginService.authenticate(username, password);
		checkMsg.setText("Validating...");
		
		if (user != null) {
			checkMsg.setText("Login Success!");
			try {
				Parent root = FXMLLoader.load(
					getClass().getResource("/gui/MainPage.fxml")
				);
				
				Stage stage = (Stage) txtUsername.getScene().getWindow();
				Scene scene = new Scene(root);
				scene.getStylesheets().add(getClass().getResource("/css/application.css").toExternalForm());
				
				stage.setScene(scene);
				stage.show();
			} catch(Exception e) {
				e.printStackTrace();
			}
		} else {
			checkMsg.setText("Invalid Username or Password!");
		}
		
		
		
		
	}
}
