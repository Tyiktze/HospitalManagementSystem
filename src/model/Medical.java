package model;

public class Medical {

	    private String name;
	    private String manufacturer;
	    private String expiryDate;
	    private int cost;
	    private int count;

	    public Medical(String name, String manufacturer, String expiryDate, int cost, int count) {
	        this.name = name;
	        this.manufacturer = manufacturer;
	        this.expiryDate = expiryDate;
	        this.cost = cost;
	        this.count = count;
	    }

	    //get and set
	    public String getName() { return name; }
	    public void setName(String name) { this.name = name; }

	    public String getManufacturer() { return manufacturer; }
	    public void setManufacturer(String manufacturer) { this.manufacturer = manufacturer; }

	    public String getExpiryDate() { return expiryDate; }
	    public void setExpiryDate(String expiryDate) { this.expiryDate = expiryDate; }

	    public int getCost() { return cost; }
	    public void setCost(int cost) { this.cost = cost; }

	    public int getCount() { return count; }
	    public void setCount(int count) { this.count = count; }
}


