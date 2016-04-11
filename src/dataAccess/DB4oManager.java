package dataAccess;

import java.io.File;
//import java.util.Enumeration;
//import java.util.Vector;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.HashSet;
import java.util.ListIterator;
import java.util.Vector;

import com.db4o.*;
import com.db4o.config.EmbeddedConfiguration;
import com.db4o.cs.Db4oClientServer;
import com.db4o.cs.config.ClientConfiguration;

import configuration.ConfigXML;
import domain.Activity;
import domain.Booking;
import domain.Client;
import domain.Offer;
//import dataModel.Offer;
import domain.User;
import domain.Owner;
import domain.RuralHouse;
import exceptions.OfferCanNotBeBooked;
import exceptions.OverlappingOfferExists;
import exceptions.OverlappingUserExists;

public class DB4oManager {

	private static ObjectContainer db;
	private static EmbeddedConfiguration configuration;
	private static ClientConfiguration configurationCS;
	private int bookingNumber = 0; // if it is "static" then it is not
									// serialized
	private int offerNumber = 0; // if it is "static" then it is not serialized
	private int activityNumber = 0;
	private static DB4oManager theDB4oManager = null;

	private static DB4oManagerAux theDB4oManagerAux;
	ConfigXML c;

	private DB4oManager() throws Exception {
		theDB4oManagerAux = new DB4oManagerAux(0, 0, 0, 0);
		c = ConfigXML.getInstance();
		System.out.println("Creating DB4oManager instance => isDatabaseLocal: "
				+ c.isDatabaseLocal() + " getDatabBaseOpenMode: "
				+ c.getDataBaseOpenMode());

		if ((c.getDataBaseOpenMode().equals("initialize"))
				&& (c.isDatabaseLocal()))
			new File(c.getDb4oFilename()).delete();

		if (c.isDatabaseLocal()) {
			configuration = Db4oEmbedded.newConfiguration();
			configuration.common().activationDepth(c.getActivationDepth());
			configuration.common().updateDepth(c.getUpdateDepth());
			db = Db4oEmbedded.openFile(configuration, c.getDb4oFilename());
			System.out.println("DataBase opened");
		} else // c.isDatabaseLocal==false
		{
			openObjectContainer();
		}
		if (c.getDataBaseOpenMode().equals("initialize")) {
			initializeDB();
			System.out.println("DataBase initialized");
		} else // c.getDataBaseOpenMode().equals("open")

		{
			ObjectSet res = db.queryByExample(DB4oManagerAux.class);
			ListIterator listIter = res.listIterator();
			if (listIter.hasNext())
				theDB4oManagerAux = (DB4oManagerAux) res.next();
		}
	}

	private void openObjectContainer() {

		configurationCS = Db4oClientServer.newClientConfiguration();
		configurationCS.common().activationDepth(c.getActivationDepth());
		configurationCS.common().updateDepth(c.getUpdateDepth());
		configurationCS.common().objectClass(Owner.class).cascadeOnDelete(true);
		db = Db4oClientServer.openClient(configurationCS, c.getDatabaseNode(),
				c.getDatabasePort(), c.getUser(), c.getPassword());

	}

	class DB4oManagerAux {
		int bookingNumber;
		int offerNumber;
		int houseNumber;
		int activityNumber;

		DB4oManagerAux(int bookingNumber, int offerNumber, int houseNumber,
				int activityNumber) {
			this.bookingNumber = bookingNumber;
			this.offerNumber = offerNumber;
			this.houseNumber = houseNumber;
			this.activityNumber = activityNumber;
		}
	}

	public static DB4oManager getInstance() throws Exception {
		if (theDB4oManager == null)
			theDB4oManager = new DB4oManager();
		return theDB4oManager;
	}

	public void initializeDB() {

		Owner jon = new Owner("Jon", "Jonlog", "passJon");
		Owner alfredo = new Owner("Alfredo", "AlfredoLog", "passAlfredo");
		Owner jesus = new Owner("Jes�s", "Jesuslog", "passJesus");
		Owner josean = new Owner("Josean", "JoseanLog", "passJosean");

		boolean[] serv0 = { true, true, false, false, false, false, false,
				false, false };
		boolean[] serv1 = { true, true, false, false, true, false, true, false,
				false };
		boolean[] serv2 = { true, true, false, false, false, false, false,
				true, true };

		jon.addRuralHouse(theDB4oManagerAux.houseNumber++, "Ezkioko etxea",
				"Ezkio", "Gurutxeta 44", serv0);
		jon.addRuralHouse(theDB4oManagerAux.houseNumber++, "Etxetxikia",
				"Iru�a", "Rotonda Kale 21", serv1);
		jesus.addRuralHouse(theDB4oManagerAux.houseNumber++, "Udaletxea",
				"Bilbo", "Pagasarri 30", serv0);
		josean.addRuralHouse(theDB4oManagerAux.houseNumber++, "Gaztetxea",
				"Renteria", "Rua del Percebe 2", serv2);

		jon.addActivity(theDB4oManagerAux.activityNumber++,
				"Tiro con arco a caballo", 20, "Arquerias Jontxu");
		josean.addActivity(theDB4oManagerAux.activityNumber++,
				"Rutas a caballo por el monte", 21.6, "Rancho caballo loco");

		jon.setBankAccount("12345677");
		alfredo.setBankAccount("77654321");
		jesus.setBankAccount("12344321");
		josean.setBankAccount("43211234");

		db.store(jon);
		db.store(alfredo);
		db.store(jesus);
		db.store(josean);

		db.store(theDB4oManagerAux);

		db.commit();
	}

	public Owner createOwner(String name, String login, String password,
			String bankAccount) {
		try {
			Owner o = new Owner(name, login, password, bankAccount);
			db.store(o);
			db.commit();
			return o;
		} catch (com.db4o.ext.ObjectNotStorableException e) {
			System.out
					.println("Error: com.db4o.ext.ObjectNotStorableException in createOffer "
							+ e.getMessage());
			return null;
		}

	}

	public Offer createOffer(RuralHouse ruralHouse, Date firstDay,
			Date lastDay, float price) throws RemoteException, Exception {

		try {

			// if (c.isDatabaseLocal()==false) openObjectContainer();

			RuralHouse proto = new RuralHouse(ruralHouse.getHouseNumber(),
					null, null, null, null, null);
			ObjectSet result = db.queryByExample(proto);
			RuralHouse rh = (RuralHouse) result.next();
			Offer o = rh.createOffer(theDB4oManagerAux.offerNumber++, firstDay,
					lastDay, price);
			db.store(theDB4oManagerAux); // To store the new value for
											// offerNumber
			db.store(o);
			db.commit();
			return o;
		} catch (com.db4o.ext.ObjectNotStorableException e) {
			System.out
					.println("Error: com.db4o.ext.ObjectNotStorableException in createOffer "
							+ e.getMessage());
			return null;
		}
	}

	public void deleteDB() {
		try {
			Owner proto = new Owner(null, null, null, null);
			ObjectSet result = db.queryByExample(proto);
			Vector<Owner> owners = new Vector<Owner>();
			while (result.hasNext()) {
				Owner o = (Owner) result.next();
				System.out.println("Deleted owner: " + o.toString());
				db.delete(o);
			}
			db.commit();
		} finally {
			// db.close();
		}
	}

	/**
	 * This method creates a book with a corresponding parameters
	 * 
	 * @param First
	 *            day, last day, house number and telephone
	 * @return a book
	 */
	public Booking createBooking(RuralHouse ruralHouse, Date firstDate,
			Date lastDate, String bookTelephoneNumber, User u)
			throws OfferCanNotBeBooked {

		try {

			// if (c.isDatabaseLocal()==false) openObjectContainer();

			RuralHouse proto = new RuralHouse(ruralHouse.getHouseNumber(),
					null, ruralHouse.getDescription(), ruralHouse.getCity(),
					ruralHouse.getAddress(), ruralHouse.getServices());
			ObjectSet result = db.queryByExample(proto);
			RuralHouse rh = (RuralHouse) result.next();

			Offer offer;
			offer = rh.findOffer(firstDate, lastDate);

			if (offer != null) {
				Booking b = offer.createBooking(
						theDB4oManagerAux.bookingNumber++, bookTelephoneNumber,
						u);
				System.out.println(u.getName());

				u.addBooking(b);
				db.store(theDB4oManagerAux); // To store the new value for

				db.store(u);
				db.commit();

				return offer.getBooking();
			}
			return null;

		} catch (com.db4o.ext.ObjectNotStorableException e) {
			System.out
					.println("Error: com.db4o.ext.ObjectNotStorableException in createBooking "
							+ e.getMessage());
			return null;
		} catch (Exception exc) {
			exc.printStackTrace();
			return null;
		}
	}

	/**
	 * This method returns existing owners
	 * 
	 */
	public Vector<Owner> getOwners() throws RemoteException, Exception {

		// if (c.isDatabaseLocal()==false) openObjectContainer();

		try {
			Owner proto = new Owner(null, null, null, null);
			ObjectSet result = db.queryByExample(proto);
			Vector<Owner> owners = new Vector<Owner>();
			while (result.hasNext())
				owners.add((Owner) result.next());
			return owners;
		} finally {
			// db.close();
		}
	}

	/**
	 * This method returns existing rural houses
	 * 
	 */
	public Vector<RuralHouse> getAllRuralHouses() throws RemoteException,
			Exception {

		// if (c.isDatabaseLocal()==false) openObjectContainer();

		try {
			RuralHouse proto = new RuralHouse(0, null, null, null, null, null);
			ObjectSet result = db.queryByExample(proto);
			Vector<RuralHouse> ruralHouses = new Vector<RuralHouse>();
			while (result.hasNext())
				ruralHouses.add((RuralHouse) result.next());
			return ruralHouses;
		} finally {
			// db.close();
		}
	}

	/**
	 * This method checks if there is an overlapping offer
	 * 
	 */
	public boolean existsOverlappingOffer(RuralHouse rh, Date firstDay,
			Date lastDay) throws RemoteException, OverlappingOfferExists {
		try {
			// if (c.isDatabaseLocal()==false) openObjectContainer();

			RuralHouse rhn = (RuralHouse) db.queryByExample(
					new RuralHouse(rh.getHouseNumber(), null, null, null, null,
							null)).next();
			if (rhn.overlapsWith(firstDay, lastDay) != null)
				throw new OverlappingOfferExists();
			else
				return false;
		} finally {
			// db.close();
		}
	}

	/**
	 * This method checks if there is an overlapping User
	 * 
	 */
	public boolean existsOverlappingUser(String login) throws RemoteException,
			OverlappingUserExists {
		try {
			ObjectSet ob = db.queryByExample(new User(null, login, null, null));
			if (ob.isEmpty()) {
				return false;
			} else {
				throw new OverlappingUserExists();
			}
		} finally {

		}

	}

	/**
	 * This method checks if the login is valid and returns the user
	 * 
	 */

	public User checkLogin(String login, String password)
			throws RemoteException {
		try {
			ObjectSet ob = db.queryByExample(new User(null, login, password,
					null));
			if (ob.isEmpty())
				return null;
			else
				return (User) ob.get(0);

		} finally {

		}
	}

	/**
	 * This method cancels the requested booking
	 * 
	 */
	public boolean cancelRequestedBooking(Booking b, User u)
			throws RemoteException, Exception {
		try {

			ObjectSet result = db.queryByExample(b.getOffer());
			Offer of = (Offer) result.get(0);
			of.setBooking(null);
			u.getBookings().remove(b);
			db.store(of);
			db.delete(b);
			db.store(u);
			db.commit();
			return true;
		} catch (Exception e1) {
			e1.printStackTrace();
			return false;
		}
	}

	/**
	 * This method updates the requested rural house
	 */
	public boolean updateRequestedHouse(RuralHouse h, String city,
			String description, String address, boolean[] services)
			throws RemoteException, Exception {
		try {
			h.setCity(city);
			h.setDescription(description);
			h.setAddress(address);
			h.setServices(services);
			db.store(h);
			db.commit();
			System.out.println("Updated :" + h.toString());
			return true;
		} catch (Exception e1) {
			return false;
		}
	}

	/**
	 * 
	 * This method adds an rural house to the owner that creates it.
	 * 
	 */
	public RuralHouse createRuralHouse(Owner o, String city,
			String description, String address, boolean[] services)
			throws RemoteException, Exception {
		try {
			RuralHouse rh = new RuralHouse(theDB4oManagerAux.houseNumber++, o,
					description, city, address, services);
			o.addRuralHouse(rh.getHouseNumber(), description, city, address,
					services);
			db.store(theDB4oManagerAux);
			db.store(o);
			System.out.println("Creating House: " + rh.toString());
			return rh;
		} catch (com.db4o.ext.ObjectNotStorableException e) {
			System.out
					.println("Error: com.db4o.ext.ObjectNotStorableException in createOffer "
							+ e.getMessage());
			return null;
		}
	}

	/**
	 * 
	 * This method deletes the requested house
	 */
	public boolean deleteRequestedHouse(RuralHouse h, Owner o)
			throws RemoteException, Exception {
		try {
			Offer of = new Offer(0, h, null, null, 0);
			ObjectSet result = db.queryByExample(of);
			if (result.size() == 0) {
				o.getRuralHouses().removeElement(h);
				db.delete(h);
				db.store(o);
				db.commit();
				System.out.println("Deleted: " + h.toString());
				return true;
			} else {
				return false;
			}

		} catch (Exception e1) {
			e1.printStackTrace();
			return false;
		}
	}

	public void close() {
		db.close();
		System.out.println("DataBase closed");
	}

	public String toString() {
		return "bookingNumber=" + bookingNumber + " offerNumber=" + offerNumber;
	}

	/**
	 * 
	 * This method creates a client with the desired info
	 */
	public Client createClient(String name, String login, String password,
			String bankAccount) {
		try {
			Client c = new Client(name, login, password, bankAccount);
			db.store(c);
			db.commit();
			return c;
		} catch (com.db4o.ext.ObjectNotStorableException e) {
			System.out
					.println("Error: com.db4o.ext.ObjectNotStorableException in createOffer "
							+ e.getMessage());
			return null;
		}
	}

	/**
	 * This method creates an activity with the desired info
	 */
	public Activity createActivity(Owner o, String description, String name,
			double price) {
		try {
			db.store(theDB4oManagerAux);
			Activity a = o.addActivity(theDB4oManagerAux.activityNumber++,
					description, price, name);
			db.store(o);
			db.commit();
			return a;
		} catch (com.db4o.ext.ObjectNotStorableException e) {
			System.out
					.println("Error: com.db4o.ext.ObjectNotStorableException in createOffer "
							+ e.getMessage());
			return null;
		}
	}

	/**
	 * This method creates an activity booking
	 */
	public Booking createActivityBooking(Booking b, Activity a) {
		try {
			b.getActivities().add(a);
			db.store(b);
			db.commit();
			return b;
		} catch (com.db4o.ext.ObjectNotStorableException e) {
			System.out
					.println("Error: com.db4o.ext.ObjectNotStorableException in createOffer "
							+ e.getMessage());
			return null;
		}
	}

	/**
	 * 
	 * This method returns all the possible activities
	 */
	public Vector<Activity> getAllActivities() {
		try {
			Activity proto = new Activity(0, null, 0, null, null);
			ObjectSet result = db.queryByExample(proto);
			Vector<Activity> activities = new Vector<Activity>();
			while (result.hasNext())
				activities.add((Activity) result.next());
			return activities;
		} finally {
			// db.close();
		}
	}

}
