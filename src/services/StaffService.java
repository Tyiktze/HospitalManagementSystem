package services;

import model.OperationResult;
import model.Staff;
import utils.InputValidator;

import java.lang.reflect.Field;
import java.util.ArrayList;


public class StaffService {
	private final ArrayList<Staff> staffList;
	private int nextId;
	
	public StaffService() {
		this.staffList = new ArrayList<>();
		initialiseData();
		nextId = staffList.size() + 1;
	}
	
	private void initialiseData() {
		staffList.add(new Staff("S001", "Teoh", "Admin", "Male", 3000));
		staffList.add(new Staff("S002", "Ooi", "Random Guy", "Male", 6700));
		staffList.add(new Staff("S003", "Beh", "Poor", "Male", 2));
		staffList.add(new Staff("S004", "Teoh", "In Debt", "Male", -10));
	}
	
	public String getStaffId() {
		return String.format("S%03d", nextId);
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
	
	public OperationResult<Void> addStaff(String newId, String newName, String newDesignation, String newSex, String rawSalary) {
		if (newName == null || newName.isBlank()) return new OperationResult<>(false, "Please enter a name", null);
		if (newDesignation == null || newDesignation.isBlank()) return new OperationResult<>(false, "Please enter a designation", null);
		if (newSex == null || newSex.isBlank()) return new OperationResult<>(false, "Please enter a sex", null);
		
		Integer salary = InputValidator.parseInteger(rawSalary);
		if (salary == null) return new OperationResult<>(false, "Salary must be a number.", null);
		
		Staff staff = new Staff(newId, newName, newDesignation, newSex, salary);
		staffList.add(staff);
		
		nextId++;
		return new OperationResult<>(true, "Staff ID: " + newId + " added.", null);
	}
	
	public OperationResult<Void> removeStaff(Staff staff) {
		if (staff == null) return new OperationResult<>(false, "No Staff selected", null);
		
		if (staffList.remove(staff)) {
			return new OperationResult<>(true, "Staff " + staff.getId() + " deleted. This action cannot be undone.", null);
		}
		
		return new OperationResult<>(false, "Staff no longer exist.", null);
	}
	
	public OperationResult<Void> updateStaff(Staff staff, String newName, String newDesignation, String newSex, String rawSalary) {
		if (staff == null) return new OperationResult<>(false, "Staff not found.", null);
		
		if (newName != null && !newName.isEmpty()) staff.setName(newName);
    	if (newDesignation != null && !newDesignation.isEmpty()) staff.setDesignation(newDesignation);
    	if (newSex != null && !newSex.isEmpty()) staff.setSex(newSex);
    	
    	if (rawSalary != null && !rawSalary.isEmpty()) {
    		Integer salary = InputValidator.parseInteger(rawSalary);
    		if (salary == null) return new OperationResult<>(false, "Salary must be a number.", null);
        	staff.setSalary(salary);
    	}
    	
    	return new OperationResult<>(true, "Staff " + staff.getId() + " updated.", null);
	}
	
	public OperationResult<Staff> findStaff(String id) {
		if (id == null || id.isBlank()) return new OperationResult<>(false, "Please enter an ID", null);
		
		for (Staff staff: staffList) {
			if (id.equalsIgnoreCase(staff.getId())) return new OperationResult<>(true, "Staff found", staff);
		}
		
		return new OperationResult<>(false, "Staff not found", null);
	}
	
	
	public OperationResult<ArrayList<Staff>> searchStaff(String id, String name, String designation, String sex, String rawSalary) {
		ArrayList<Staff> result = new ArrayList<>();
		Integer salary = 0;
		
		if(rawSalary != null && !rawSalary.isBlank()) {
	        salary = InputValidator.parseInteger(rawSalary);

	        if(salary == null) {
	            return new OperationResult<>(false, "Salary must be a number.", null);
	        }
	    }
		
		for (Staff staff: staffList) {
			boolean match = true;
			
			if (id != null && !id.isBlank() 
					&& !id.equalsIgnoreCase(staff.getId())) match = false;
			
			if (name != null && !name.isBlank()
					&& !staff.getName().toLowerCase().contains(name.toLowerCase())) match = false;
			
			if (designation != null && !designation.isBlank()
					&& !staff.getDesignation().toLowerCase().contains(designation.toLowerCase())) match = false;
			
			if (sex != null && !sex.isBlank()
					&& !sex.equalsIgnoreCase(staff.getSex())) match = false;
			
			if (salary != 0
					&& salary  != staff.getSalary()) match = false;
			
			if (match) result.add(staff);
		}
		
		return new OperationResult<>(true, "Search successful, " + result.size() + " entries found.", result);
	}
	
}
