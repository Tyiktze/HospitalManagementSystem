package services;
import model.User;
import java.util.ArrayList;

public class LoginService {
	private ArrayList<User> users = new ArrayList<>();
	
	public LoginService() {
		users.add(new User("admin", "12345", "Administrator"));
	}
	
	public User authenticate(String username, String password) {
		for (User user : users) {
			if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
				return user;
			}
		}
		
		return null;
	}
}
