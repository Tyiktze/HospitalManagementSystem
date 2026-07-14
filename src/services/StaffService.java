package services;

import model.Staff;
import controller.HospitalManagement;

public class StaffService {
	private HospitalManagement hospital;
	
	public StaffService(HospitalManagement hospital) {
		this.hospital = hospital;
	}
	
	public void displayAllStaff() {
		int i = 1;
		for (Staff staff : hospital.getStaffs()) {
			System.out.print(i++ + " ");
			staff.showStaffInfo();
		}
	}
}
