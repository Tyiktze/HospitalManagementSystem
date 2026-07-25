package application;

import services.LoginService;
import services.StaffService;

public class AppContext {
	private static AppContext instance;
	
	private final LoginService loginService;
	private final StaffService staffService;
	
	private AppContext() {
		this.loginService = new LoginService();
		this.staffService = new StaffService();
	}
	
	
	public static AppContext getInstance() {
		if (instance == null) instance = new AppContext();
		return instance;
	}
	
	public LoginService getLoginService() { return loginService; }
	public StaffService getStaffService() { return staffService; }
}
