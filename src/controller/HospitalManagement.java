package controller;

import java.util.ArrayList;
import model.*;

public class HospitalManagement {
	private ArrayList<Staff> staffs;
	
	public HospitalManagement() {
		staffs = new ArrayList<>();
		
		initialiseData();
	}
	
	private void initialiseData() {
		staffs.add(new Staff("001", "Teoh", "Admin", "Male", 3000));
		staffs.add(new Staff("002", "Ooi", "Random Guy", "Male", 6700));
		staffs.add(new Staff("003", "Beh", "Poor", "Male", 2));
		staffs.add(new Staff("004", "Teoh", "In Debt", "Male", -10));
	}
	
	public ArrayList<Staff> getStaffs() { return staffs; }
}