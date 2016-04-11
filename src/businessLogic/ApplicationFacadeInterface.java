package businessLogic;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.Vector;

import domain.Activity;
import domain.Booking;
import domain.Client;
import domain.Offer;
import domain.Owner;
import domain.RuralHouse;
import domain.User;
import exceptions.OfferCanNotBeBooked;

public interface ApplicationFacadeInterface extends Remote {

	/**
	 * This method creates an offer with a house number, first day, last day and
	 * price
	 * 
	 * @param House
	 *            number, start day, last day and price
	 * @return None
	 */

	Offer createOffer(RuralHouse ruralHouse, Date firstDay, Date lastDay,
			float price) throws RemoteException, Exception;

	/**
	 * This method creates an owner with the parameters
	 * 
	 * @param Name
	 *            , login, password and bank account
	 * 
	 * @return None
	 */

	Owner createOwner(String name, String login, String password,
			String bankAccount) throws RemoteException, Exception;

	/**
	 * This method creates a book with a corresponding parameters to a concrete
	 * User
	 * 
	 * @param First
	 *            day, last day, house number, telephone and User
	 * @return a book
	 */
	Booking createBooking(RuralHouse ruralHouse, Date firstDay, Date lastDay,
			String telephoneNumber, User u) throws RemoteException,
			OfferCanNotBeBooked;

	/**
	 * This method retrieves the existing owners
	 * 
	 * @return a Set of owners
	 */
	public Vector<Owner> getOwners() throws RemoteException, Exception;

	/**
	 * This method retrieves the existing rural houses
	 * 
	 * @return a Set of rural houses
	 */
	public Vector<RuralHouse> getAllRuralHouses() throws RemoteException,
			Exception;

	/**
	 * This method retreives the selected User
	 * 
	 * @return a User
	 */
	public User checkLogin(String login, String password)
			throws RemoteException, Exception;

	/**
	 * This method cancels an actual Booking
	 * 
	 * @return true if deleted false otherwise
	 */
	public boolean cancelRequestedBooking(Booking b, User u)
			throws RemoteException, Exception;

	/**
	 * This method updates the selectedHouse
	 * 
	 * @return true if updated, false otherwise
	 */
	public boolean updateRequestedHouse(RuralHouse h, String city,
			String description, String address, boolean[] services)
			throws RemoteException, Exception;

	/**
	 * This method creates a ruralHouse for a concrete Owner
	 * 
	 * @return a ruralHouse
	 */
	public RuralHouse createRuralHouse(Owner o, String city,
			String description, String address, boolean[] services)
			throws RemoteException, Exception;

	/**
	 * This method deletes an actual House
	 * 
	 * @return true if deleted, false otherwise
	 */
	boolean deleteRequestedHouse(RuralHouse h, Owner o) throws RemoteException,
			Exception;

	public void close() throws RemoteException;

	/**
	 * 
	 * This method creates a client
	 * 
	 * @param Name
	 *            , login, password and bank account
	 * @return a client
	 */
	public Client createClient(String name, String login, String password,
			String bankAccount) throws RemoteException, Exception;

	/**
	 * 
	 * This method creates an activity for a concrete Owner
	 * 
	 * @return an activity
	 */
	public Activity createActivity(Owner o, String description, String name,
			double price) throws RemoteException, Exception;

	/**
	 * 
	 * This method creates an activity booking for a concrete activity
	 * 
	 * @return a booking
	 */
	public Booking createActivityBooking(Booking b, Activity a)
			throws RemoteException, Exception;

	/**
	 * This method retrieves the existing activities
	 * 
	 * @return a Set of activities
	 */
	public Vector<Activity> getAllActivities() throws RemoteException,
			Exception;

}