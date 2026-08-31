package model;

import java.util.ArrayList;
import java.util.List;

public class Dashboard {
	private int totalPatients;
	private int availableBeds;
	private int totalStaff;
	private int[] ageGroupCount;
	private List<Medical> criticalInventory;
	
	
	public Dashboard() {
	    this.totalPatients = 0;
	    this.availableBeds = 100;
	    this.totalStaff = 0;
	    this.ageGroupCount = new int[4];
	    this.criticalInventory = new ArrayList<>();
	}
	
	
	public void updateDashboard(ArrayList<Patient> patientList, List<Medical> medicalList, ArrayList<Staff> staffList) {
	    this.totalPatients = patientList.size();
	    this.availableBeds = Math.max(0, 100 - totalPatients);
	    this.totalStaff = staffList.size();
	    this.ageGroupCount = new int[4];
	    this.criticalInventory.clear();
	    
	    for (Patient patient : patientList) { //to get the age group distribution
            int age = patient.getAge();

            if (age <= 18) { 
                ageGroupCount[0]++; //index 0 is for patients aged between 0 and 18
            } else if (age <= 40) {
                ageGroupCount[1]++; //index 1 is for patients aged between 19 and 40
            } else if (age <= 65) {
                ageGroupCount[2]++; //index 2 is for patients aged between 41 and 65
            } else {
                ageGroupCount[3]++; //index 3 is for patients aged more than 65
            }
        }
	    
	    
	    for (Medical medical : medicalList) { //to get the critical inventory
	        if (medical.getCount() <= 10) {
	            this.criticalInventory.add(medical); //save the medical object to the list
	        }
	    }
	    
	    
	}

	//all the get methods
	public int getTotalPatients() {
		return totalPatients;
	}
	
	public int getAvailableBeds() {
		return availableBeds;
	}
	
	public int getTotalStaff() {
		return totalStaff;
	}
	
	public int[] getAgeGroupCount() {
		return ageGroupCount;
	}
	
	public List<Medical> getCriticalInventory() {
	    return criticalInventory;
	}
	
	
}
