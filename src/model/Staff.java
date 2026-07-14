package model;

public class Staff {
	//Attributes
	private String id;
	private String name;
	private String designation;
	private String sex;
	private int salary;
	
	//Constructors
	public Staff() {}
	
	public Staff(String id, String name, String designation, String sex, int salary) {
		this.id = id;
		this.name = name;
		this.designation = designation;
		this.sex = sex;
		this.salary = salary;
	}
	
	//Methods
	public void showStaffInfo() {
		System.out.printf("[%s]\t[%s]\t[%s]\t[%s]\t[%d]%n",
		        id, name, designation, sex, salary);
	}
	
	//Getters & Setters
	public String getId() {return id;}
	public void setId(String id) {this.id = id;}
	
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	
	public String getDesignation() { return designation; }
	public void setDesignation(String designation) { this.designation = designation; }

	public String getSex() { return sex; }
	public void setSex(String sex) { this.sex = sex; }

	public int getSalary() { return salary; }
	public void setSalary(int salary) { this.salary = salary; }

}
