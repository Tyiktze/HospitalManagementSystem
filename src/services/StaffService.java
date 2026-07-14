package services;

import model.Staff;

import java.lang.reflect.Field;
import java.util.ArrayList;

import controller.HospitalManagement;

public class StaffService {
	private ArrayList<Staff> staffList;
	
	public StaffService(HospitalManagement hospital) {
		this.staffList = hospital.getStaffs();
	}
	
	public ArrayList<Staff> getStaff() {
		return staffList;
	}
	
	public ArrayList<String> getColumns() {
		Field[] fields = Staff.class.getDeclaredFields();
		ArrayList<String> arr = new ArrayList<>();
		for (Field field : fields) {
			arr.add(field.getName());
		}
		return arr;
	}
	
	public void addStaff(Staff staff) {
		staffList.add(staff);
	}
	
}
