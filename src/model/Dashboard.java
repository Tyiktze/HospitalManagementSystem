package model;

import java.util.ArrayList;
import java.util.List;

import services.PatientService;

public class Dashboard {
	private int totalPatients;
	private int availableBeds;
	private double occupancyRate;
	private int[] ageGroupCount;
	//private List<Items> criticalInventory;
	private PatientService patientService;
	
	public Dashboard(PatientService patientService) {
	    this.patientService = patientService;
	    this.totalPatients = 0;
	    this.availableBeds = 100;
	    this.occupancyRate = 0.00;
	    this.ageGroupCount = new int[4];
	}
	
	
	public void updateDashboard() {
	    ArrayList<Patient> patientList = patientService.getPatients();

	    this.totalPatients = patientList.size();
	    this.availableBeds = totalBeds - totalPatients;
	    this.occupancyRate = ((double) totalPatients / 100.00) * 100;/*the first 100 is the maximum number of patients, 
		                                                                  and the second 100 is to convert to percentage*/
	}
	
	
		 /*the first 100 is the maximum number of patients, 
														and the second 100 is to convert to percentage*/
		
	}
	
	public void getAgeGroupCount() {
		ArrayList<Patient> patientList = patientService.getPatients();
		for (int i= 0;i<patientList.size();i++) {
			if (patientList.get(i).getAge()>=0 && patientList.get(i).getAge()<=18 ) {
				ageGroupCount[0]++; //index 0 is for patient aged between 0 and 18 (means non-adult)
			}
			else if (patientList.get(i).getAge()>=19 && patientList.get(i).getAge()<=40) {
				ageGroupCount[1]++; //index 1 is for patient aged between 19 and 40
			}
			else if (patientList.get(i).getAge()>=41 && patientList.get(i).getAge()<=65) {
				ageGroupCount[2]++; //index 2 is for patient aged between 41 and 65
			}
			else {
				ageGroupCount[3]++; //index 3 is for patient aged more than 65
			}
		}
	}
	
	
}
