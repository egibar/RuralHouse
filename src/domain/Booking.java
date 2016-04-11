package domain;

import java.io.*;
import java.util.Date;
import java.util.Vector;

@SuppressWarnings("serial")
public class Booking implements Serializable {
	private int bookingNumber;
	private boolean isPaid;
	private Date bookingDate;
	private String telephone;
	private Offer offer;
	private User client;
	private Vector<Activity> activities;
	
	
	public Booking() {
	}

	public Booking(int bookingNumber, String telephone, Offer offer,User u) {

		this.bookingNumber = bookingNumber;
		this.telephone = telephone;
		this.offer = offer;
		// this.price = price;
		// Booking date is assigned to actual date
		this.bookingDate = new Date(System.currentTimeMillis());
		this.isPaid = false;
		activities = new Vector<Activity>();
		this.client = u;

	}

	public Vector<Activity> getActivities() {
		return activities;
	}

	public void setActivities(Vector<Activity> activities) {
		this.activities = activities;
	}

	// used for search
	public Booking(int bookingNumber, boolean isPaid, Date bookingDate,
			String telephone, Offer offer,User u) {
		super();
		this.bookingNumber = bookingNumber;
		this.isPaid = isPaid;
		this.bookingDate = bookingDate;
		this.telephone = telephone;
		this.offer = offer;
		this.client= u;
		activities = new Vector<Activity>();

	}

	public User getClient() {
		return client;
	}

	public void setClient(User client) {
		this.client = client;
	}

	public void setBookingNumber(int bookingNumber) {
		this.bookingNumber = bookingNumber;
	}

	public void setPaid(boolean isPaid) {
		this.isPaid = isPaid;
	}

	
	public void imprimete() {
		System.out.println(bookingNumber);
		System.out.println(isPaid);
		System.out.println(bookingDate);
		System.out.println(telephone);
		System.out.println(offer);

	}

	/**
	 * Get the booking number
	 * 
	 * @return booking number
	 */
	public int getBookingNumber() {
		return this.bookingNumber;
	}

	/**
	 * Set a offer object
	 * 
	 * @param Offer
	 *            object
	 * 
	 */
	public void setOffer(Offer offer) {
		this.offer = offer;
	}

	/**
	 * Get the offer object
	 * 
	 * @return Offer object
	 */
	public Offer getOffer() {
		return this.offer;
	}

	/**
	 * This method returns a price
	 * 
	 * @return price
	 */
	public float getPrice() {
		return this.offer.getPrice();
	}

	/**
	 * This method sets a booking date
	 * 
	 * @param bookDate
	 */
	public void setBookingDate(Date bookingDate) {
		this.bookingDate = bookingDate;
	}

	/**
	 * This method returns a booking date
	 * 
	 * @return booking date
	 */
	public Date getBookingDate() {
		return this.bookingDate;
	}

	/**
	 * This method sets a telephone number
	 * 
	 * @param telephone
	 */
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	/**
	 * This method returns a telephone number
	 * 
	 * @return telephone number
	 */
	public String getTelephone() {
		return this.telephone;
	}

	/**
	 * This method puts the reserve as paid
	 * 
	 * @param none
	 */
	public void paid() {
		this.isPaid = true;
	}

	/**
	 * This method puts the reserve as not paid
	 * 
	 * @return none
	 */
	public void notPaid() {
		this.isPaid = false;
	}

	/**
	 * This method returns the status of a book
	 * 
	 * @param none
	 */
	public boolean isPaid() {
		return isPaid;
	}

	@Override
	public String toString() {
		return "Booking: " +bookingNumber+
				"			From: "+ offer.getFirstDay().toString().substring(0, 10) + 
				"			To:"+ offer.getLastDay().toString().substring(0, 10)+ 
				"			House: "+ offer.getRuralHouse().toString();
	}
}