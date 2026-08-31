package services;

import model.OperationResult;
import model.Staff;
import utils.IDCalculator;
import utils.InputValidator;
import utils.SearchParser;
import java.util.ArrayList;


public class StaffService {
	private final ArrayList<Staff> staffList;
	private int nextId;
	
	public StaffService() {
		this.staffList = new ArrayList<>();
		initialiseData();
		this.nextId = IDCalculator.calculateNextId(staffList, Staff::getId);
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
	
	public OperationResult<Void> addStaff(String newId, String newName, String newDesignation, String newSex, String rawSalary) {
		for (Staff staff : staffList) {
			if (newId.equalsIgnoreCase(staff.getId())) return new OperationResult<>(false, "Staff ID " + newId + " already exists.", null);
		}
		if (newName == null || newName.isBlank()) return new OperationResult<>(false, "Please enter a name", null);
		if (newDesignation == null || newDesignation.isBlank()) return new OperationResult<>(false, "Please enter a designation", null);
		if (newSex == null || newSex.isBlank()) return new OperationResult<>(false, "Please enter a sex", null);
		
		Integer salary = InputValidator.parseInteger(rawSalary);
		if (salary == null) return new OperationResult<>(false, "Salary must be a number.", null);
		if (!InputValidator.isPositive(salary)) return new OperationResult<>(false, "Salary must be a positive number.", null);
		
		Staff staff = new Staff(newId, newName, newDesignation, newSex, salary);
		staffList.add(staff);
		
		nextId = Math.max(nextId + 1, IDCalculator.calculateNextId(staffList, Staff::getId));
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
    		if (!InputValidator.isPositive(salary)) return new OperationResult<>(false, "Salary must be a positive number.", null);
        	staff.setSalary(salary);
    	}
    	
    	return new OperationResult<>(true, "Staff " + staff.getId() + " updated.", null);
	}
	
	
	public OperationResult<ArrayList<Staff>> searchStaff(String searchField) {
		ArrayList<Staff> result = new ArrayList<>();
		
		if (searchField == null || searchField.trim().isEmpty()) {
			result.addAll(staffList);
			return new OperationResult<>(true, "Search successful, " + result.size() + " entries found.", result);
		}

		ArrayList<String> query = SearchParser.parseSearch(searchField);
		
		String id = null;
		String name = null;
		String designation = null;
		String sex = null;
		Integer salary = null;
		String generic = null;

		for (int i = 0; i < query.size(); i += 2) {
			String key = query.get(i);
			String value = query.get(i + 1);

			if (key.equalsIgnoreCase("ID")) {
				id = value;
			} else if (key.equalsIgnoreCase("NAME")) {
				name = value;
			} else if (key.equalsIgnoreCase("DESIGNATION")) {
				designation = value;
			} else if (key.equalsIgnoreCase("SEX")) {
				sex = value;
			} else if (key.equalsIgnoreCase("SALARY")) {
				salary = InputValidator.parseInteger(value);
				if (salary == null) {
					return new OperationResult<>(false, "Salary must be a number.", null);
				}
			} else if (key.equalsIgnoreCase("GENERIC")) {
				generic = value.toLowerCase();
			}
		}

		for (Staff staff : staffList) {
			boolean match = true;

			if (generic != null && !generic.isBlank()) {
				boolean genericMatch = staff.getId().toLowerCase().contains(generic)
						|| staff.getName().toLowerCase().contains(generic)
						|| staff.getDesignation().toLowerCase().contains(generic)
						|| staff.getSex().toLowerCase().contains(generic)
						|| String.valueOf(staff.getSalary()).contains(generic);
				if (!genericMatch) {
					match = false;
				}
			}

			if (id != null && !id.isBlank() && !id.equalsIgnoreCase(staff.getId())) {
				match = false;
			}

			if (name != null && !name.isBlank() && !staff.getName().toLowerCase().contains(name.toLowerCase())) {
				match = false;
			}

			if (designation != null && !designation.isBlank() && !staff.getDesignation().toLowerCase().contains(designation.toLowerCase())) {
				match = false;
			}

			if (sex != null && !sex.isBlank() && !sex.equalsIgnoreCase(staff.getSex())) {
				match = false;
			}

			if (salary != null && !salary.equals(staff.getSalary())) {
				match = false;
			}

			if (match) {
				result.add(staff);
			}
		}

		return new OperationResult<>(true, "Search successful, " + result.size() + " entries found.", result);
	}
}
