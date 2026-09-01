package application;

import services.LoginService;
import services.MedicalService;
import services.StaffService;
import services.DoctorService;
import services.PatientService;
import services.LabService;
import services.FacilityService;

public class AppContext {
	private static AppContext instance;
	
	private final LoginService loginService;
	private final StaffService staffService;
	private final DoctorService doctorService;
	private final PatientService patientService;
	private final MedicalService medicalService;
	private final LabService labService;
    private final FacilityService facilityService;
	
	private AppContext() {
		this.loginService = new LoginService();
		this.staffService = new StaffService();
		this.doctorService = new DoctorService();
		this.patientService = new PatientService();
		this.medicalService = new MedicalService();
		this.labService = new LabService();
		this.facilityService = new FacilityService();
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
    public LabService getLabService() {return labService;}
    public FacilityService getFacilityService() {return facilityService;}
}
