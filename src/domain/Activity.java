package domain;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Activity implements Serializable {
	private int activityNumber;
	private String description;
	private double price;
	private String name;
	private Owner owner;

	public Activity(int activityNumber, String description, double price, String name, Owner o) {
		this.activityNumber = activityNumber;
		this.description = description;
		this.price = price;
		this.name = name;
		this.owner = o;
	}

	public int getActivityNumber() {
		return activityNumber;
	}

	public void setActivityNumber(int activityNumber) {
		this.activityNumber = activityNumber;
	}

	public Owner getOwner() {
		return owner;
	}

	public void setOwner(Owner owner) {
		this.owner = owner;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}
	
}
