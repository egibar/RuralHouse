package domain;

import java.io.Serializable;
import java.util.Vector;

@SuppressWarnings("serial")
public class Owner extends User implements Serializable {

	private Vector<RuralHouse> ruralHouses;
	private Vector<Activity> activities;

	public Owner(String name, String login, String password) {
		super(name, login, password);
		ruralHouses = new Vector<RuralHouse>();
		activities = new Vector<Activity>();

	}

	public Owner(String name, String login, String password, String bankAccount) {
		super(name, login, password, bankAccount);
		ruralHouses = new Vector<RuralHouse>();
		activities = new Vector<Activity>();

	}

	public Vector<RuralHouse> getRuralHouses() {
		return ruralHouses;
	}

	public Vector<Activity> getActivies() {
		return activities;
	}

	public RuralHouse addRuralHouse(int houseNumber, String description,
			String city, String address, boolean[] services) {
		RuralHouse rh = new RuralHouse(houseNumber, this, description, city,
				address, services);
		ruralHouses.add(rh);
		return rh;

	}

	public Activity addActivity(int activityNumber, String description,
			double price, String name) {
		Activity a = new Activity(activityNumber, description, price, name, this);
		activities.add(a);
		return a;

	}

}