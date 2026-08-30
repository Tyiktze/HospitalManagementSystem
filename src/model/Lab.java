package model;

public class Lab {
	    private String lab;
	    private int cost;

	    public Lab(String lab, int cost) {
	        this.lab = lab;
	        this.cost = cost;
	    }

	    public String getLab() { return lab; }
	    public void setLab(String lab) { this.lab = lab; }
	    public int getCost() { return cost; }
	    public void setCost(int cost) { this.cost = cost; }

}
