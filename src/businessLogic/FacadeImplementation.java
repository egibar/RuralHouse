package businessLogic;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;
import java.sql.SQLException;
import java.util.Vector;

import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;

import configuration.ConfigXML;
import dataAccess.DB4oManager;
import domain.Activity;
import domain.Booking;
import domain.Client;
import domain.Offer;
import domain.Owner;
import domain.RuralHouse;
import domain.User;
import exceptions.BadDates;
import exceptions.DB4oManagerCreationException;
import exceptions.OfferCanNotBeBooked;
import exceptions.OverlappingOfferExists;

public class FacadeImplementation extends UnicastRemoteObject implements
		ApplicationFacadeInterface {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Vector<Owner> owners;
	Vector<RuralHouse> ruralHouses;
	Vector<Activity> activities;
	DB4oManager dB4oManager;

	public FacadeImplementation() throws RemoteException,
			InstantiationException, IllegalAccessException,
			ClassNotFoundException, SQLException, DB4oManagerCreationException {
		owners = null;
		ruralHouses = null;
		activities = null;
		try {
			dB4oManager = DB4oManager.getInstance();
		} catch (com.db4o.ext.DatabaseFileLockedException e) {
			System.out
					.println("Error in FacadeImplementation: " + e.toString());
			throw e;
		} catch (Exception e) {
			System.out
					.println("Error in FacadeImplementation: " + e.toString());
			throw new DB4oManagerCreationException();
		}
	}

	/**
	 * This method creates an offer with a house number, first day, last day and
	 * price
	 * 
	 * @param House
	 *            number, start day, last day and price
	 * @return None
	 */

	public Offer createOffer(RuralHouse ruralHouse, Date firstDay,
			Date lastDay, float price) throws OverlappingOfferExists, BadDates,
			RemoteException, Exception {
		if (firstDay.compareTo(lastDay) >= 0)
			throw new BadDates();
		ruralHouses = null;
		owners = null;
		boolean b = dB4oManager.existsOverlappingOffer(ruralHouse, firstDay,
				lastDay); // The ruralHouse object in the client may not be
							// updated
		if (!b)
			return dB4oManager
					.createOffer(ruralHouse, firstDay, lastDay, price);
		return null;
	}

	/**
	 * This method creates a book with a corresponding parameters to a concrete
	 * User
	 * 
	 * @param First
	 *            day, last day, house number, telephone and User
	 * @return a book
	 */
	public Booking createBooking(RuralHouse ruralHouse, Date firstDate,
			Date lastDate, String bookTelephoneNumber, User u)
			throws OfferCanNotBeBooked {
		ruralHouses = null;
		owners = null;
		return dB4oManager.createBooking(ruralHouse, firstDate, lastDate,
				bookTelephoneNumber, u);
	}

	/**
	 * This method retrieves the existing owners
	 * 
	 * @return a Set of owners
	 */
	public Vector<Owner> getOwners() throws RemoteException, Exception {

		if (owners != null) {
			System.out
					.println("Owners obtained directly from business logic layer");
			return owners;
		} else
			return owners = dB4oManager.getOwners();
	}

	/**
	 * This method retrieves the existing rural houses
	 * 
	 * @return a Set of rural houses
	 */
	public Vector<RuralHouse> getAllRuralHouses() throws RemoteException,
			Exception {

		if (ruralHouses != null) {
			System.out
					.println("RuralHouses obtained directly from business logic layer");
			return ruralHouses;
		} else
			return ruralHouses = dB4oManager.getAllRuralHouses();

	}

	public void close() throws RemoteException {
		dB4oManager.close();

	}

	/**
	 * This method creates an owner with the parameters
	 * 
	 * @param Name
	 *            , login, password and bank account
	 * 
	 * @return None
	 */
	@Override
	public Owner createOwner(String name, String login, String password,
			String bankAccount) throws RemoteException, Exception {
		boolean b = dB4oManager.existsOverlappingUser(login);
		if (!b)
			return dB4oManager.createOwner(name, login, password, bankAccount);
		return null;

	}

	/**
	 * This method retreives the selected User
	 * 
	 * @return a User
	 */
	@Override
	public User checkLogin(String login, String password)
			throws RemoteException, Exception {
		return dB4oManager.checkLogin(login, password);

	}

	/**
	 * This method cancels an actual Booking
	 * 
	 * @return true if deleted false otherwise
	 */
	@Override
	public boolean cancelRequestedBooking(Booking b, User u)
			throws RemoteException, Exception {
		return dB4oManager.cancelRequestedBooking(b, u);

	}

	/**
	 * This method updates the selectedHouse
	 * 
	 * @return true if updated, false otherwise
	 */
	@Override
	public boolean updateRequestedHouse(RuralHouse h, String city,
			String description, String address, boolean[] services)
			throws RemoteException, Exception {

		Boolean b = dB4oManager.updateRequestedHouse(h, city, description,
				address, services);
		if (b)
			ruralHouses = null;
		return b;
	}


	/**
	 * This method creates a ruralHouse for a concrete Owner
	 * 
	 * @return a ruralHouse
	 */
	@Override
	public RuralHouse createRuralHouse(Owner o, String city,
			String description, String address, boolean[] services)
			throws RemoteException, Exception {
		RuralHouse rh = dB4oManager.createRuralHouse(o, city, description,
				address, services);
		if (rh != null)
			ruralHouses = null;
		return rh;
	}

	/**
	 * This method deletes an actual House
	 * 
	 * @return true if deleted, false otherwise
	 */
	@Override
	public boolean deleteRequestedHouse(RuralHouse h, Owner o)
			throws RemoteException, Exception {

		Boolean b = dB4oManager.deleteRequestedHouse(h, o);
		if (b)
			ruralHouses = null;
		return b;
	}

	/**
	 * 
	 * This method creates a client
	 * 
	 * @param Name
	 *            , login, password and bank account
	 * @return a client
	 */
	@Override
	public Client createClient(String name, String login, String password,
			String bankAccount) throws RemoteException, Exception {
		boolean b = dB4oManager.existsOverlappingUser(login);
		if (!b)
			return dB4oManager.createClient(name, login, password, bankAccount);
		return null;
	}

	/**
	 * 
	 * This method creates an activity for a concrete Owner
	 * 
	 * @return an activity
	 */
	@Override
	public Activity createActivity(Owner o, String description, String name,
			double price) throws RemoteException, Exception {
		activities = null;
		Activity a = dB4oManager.createActivity(o, description, name, price);
		if (a != null)
			return a;
		return null;
	}

	/**
	 * 
	 * This method creates an activity booking for a concrete activity
	 * 
	 * @return a booking
	 */
	@Override
	public Booking createActivityBooking(Booking b, Activity a)
			throws RemoteException, Exception {
		Booking boo = dB4oManager.createActivityBooking(b, a);
		if (boo != null)
			return boo;
		return null;
	}

	/**
	 * This method retrieves the existing activities
	 * 
	 * @return a Set of activities
	 */
	@Override
	public Vector<Activity> getAllActivities() throws RemoteException,
			Exception {
		
		if (activities != null) {
			System.out
					.println("Activities obtained directly from business logic layer");
			return activities;
		} else
			return activities = dB4oManager.getAllActivities();

	}

}
