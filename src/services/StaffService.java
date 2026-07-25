package services;

import model.Staff;

import java.lang.reflect.Field;
import java.util.ArrayList;


public class StaffService {
	private final ArrayList<Staff> staffList;
	
	public StaffService() {
		this.staffList = new ArrayList<>();
		initialiseData();
	}
	
	private void initialiseData() {
		staffList.add(new Staff("001", "Teoh", "Admin", "Male", 3000));
		staffList.add(new Staff("002", "Ooi", "Random Guy", "Male", 6700));
		staffList.add(new Staff("003", "Beh", "Poor", "Male", 2));
		staffList.add(new Staff("004", "Teoh", "In Debt", "Male", -10));
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
	
	public void addStaff(String newId, String newName, String newDesignation, String newSex, int newSalary) {
		Staff staff = new Staff(newId, newName, newDesignation ,newSex , newSalary);
		staffList.add(staff);
	}
	
	public void removeStaff(String id) {
		for(Staff staff : staffList){

	        if(staff.getId().equalsIgnoreCase(id)){
	            staffList.remove(staff);
	            return;
	        }
	    }
	}
	
	public void updateStaff(Staff staff, String newName, String newDesignation, String newSex, int newSalary) {
		if (newName != null && !newName.isEmpty()) staff.setName(newName);
    	if (newDesignation != null && !newDesignation.isEmpty()) staff.setDesignation(newDesignation);
    	if (newSex != null && !newSex.isEmpty()) staff.setSex(newSex);
    	staff.setSalary(newSalary);
	}
	
	public Staff findStaff(String id) {
		for (Staff staff: staffList) {
			if (staff.getId().equals(id)) return staff;
		}
		
		return null;
	}
	
	
	public ArrayList<Staff> searchStaff(Staff sample) {
		ArrayList<Staff> result = new ArrayList<>();
		
		for (Staff staff: staffList) {
			boolean match = true;
			
			if (sample.getId() != null && !sample.getId().isEmpty() 
					&& !sample.getId().equalsIgnoreCase(staff.getId())) match = false;
			
			if (sample.getName() != null && !sample.getName().isEmpty() 
					&& !staff.getName().toLowerCase().contains(sample.getName().toLowerCase())) match = false;
			
			if (sample.getDesignation() != null && !sample.getDesignation().isEmpty() 
					&& !staff.getDesignation().toLowerCase().contains(sample.getDesignation().toLowerCase())) match = false;
			
			if (sample.getSex() != null && !sample.getSex().isEmpty() 
					&& !sample.getSex().equalsIgnoreCase(staff.getSex())) match = false;
			
			if (sample.getSalary() != 0 
					&& sample.getSalary() != staff.getSalary()) match = false;
			
			if (match) result.add(staff);
		}
		
		return result;
	}
	
}
