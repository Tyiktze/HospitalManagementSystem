package model;

public class Staff extends Person {
	//Attributes
	private String designation;
	private String sex;
	private int salary;
	
	//Constructors
	public Staff() {
		super();
	}
	
	public Staff(String id, String name, String designation, String sex, int salary) {
		super(id, name);
		this.designation = designation;
		this.sex = sex;
		this.salary = salary;
	}
	
	//Methods
	//PRINTS in console
	public void showStaffInfo() {
		System.out.printf("[%s]\t[%s]\t[%s]\t[%s]\t[%d]%n",
		        id, name, designation, sex, salary);
	}
	
	//Returns a string
	public String getStaffInfo() {
		return ("ID: " + id
                + "\nName: " + name
                + "\nDesignation: " + designation
                + "\nSex: " + sex
                + "\nSalary: " + salary);
	}

	@Override
	public String getDetails() {
		return getStaffInfo();
	}
	
	//Getters & Setters
	public String getDesignation() { return designation; }
	public void setDesignation(String designation) { this.designation = designation; }

	public String getSex() { return sex; }
	public void setSex(String sex) { this.sex = sex; }

	public int getSalary() { return salary; }
	public void setSalary(int salary) { this.salary = salary; }
}
