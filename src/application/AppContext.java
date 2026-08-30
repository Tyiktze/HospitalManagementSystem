package application;

import services.LoginService;
import services.MedicalService;
import services.StaffService;
import model.Dashboard;
import services.DoctorService;
import services.PatientService;

public class AppContext {
	private static AppContext instance;
	
	private final LoginService loginService;
	private final StaffService staffService;
	private final DoctorService doctorService;
	private final PatientService patientService;
	private final MedicalService medicalService;
	
	private AppContext() {
		this.loginService = new LoginService();
		this.staffService = new StaffService();
		this.doctorService = new DoctorService();
		this.patientService = new PatientService();
		this.medicalService = new MedicalService();
	}
	
	
	public static AppContext getInstance() {
		if (instance == null) instance = new AppContext();
		return instance;
	}
	
	public LoginService getLoginService() { return loginService; }
	public StaffService getStaffService() { return staffService; }
	public DoctorService getDoctorService() { return doctorService; }
    public PatientService getPatientService() { return patientService; }
    public MedicalService getMedicalService() { return medicalService; }
	
}
