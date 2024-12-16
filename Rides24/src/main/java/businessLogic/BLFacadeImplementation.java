package businessLogic;

import java.util.Date;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;

import configuration.ConfigXML;
import dataAccess.DataAccess;
import domain.Ride;
import domain.Traveler;
import domain.User;
import domain.Driver;
import exceptions.RideMustBeLaterThanTodayException;
import exceptions.RideAlreadyExistException;

/**
 * It implements the business logic as a web service.
 */
@WebService(endpointInterface = "businessLogic.BLFacade")
public class BLFacadeImplementation implements BLFacade {
	DataAccess dbManager;

	public BLFacadeImplementation() {
		System.out.println("Creating BLFacadeImplementation instance");

		dbManager = new DataAccess();

		// dbManager.close();

	}

	public BLFacadeImplementation(DataAccess da) {

		System.out.println("Creating BLFacadeImplementation instance with DataAccess parameter");
		@SuppressWarnings("unused")
		ConfigXML c = ConfigXML.getInstance();

		dbManager = da;
	}

	/**
	 * {@inheritDoc}
	 */
	@WebMethod
	public List<String> getDepartCities() {
		dbManager.open();

		List<String> departLocations = dbManager.getDepartCities();

		dbManager.close();

		return departLocations;

	}

	/**
	 * {@inheritDoc}
	 */
	@WebMethod
	public List<String> getDestinationCities(String from) {
		dbManager.open();

		List<String> targetCities = dbManager.getArrivalCities(from);

		dbManager.close();

		return targetCities;
	}

	/**
	 * {@inheritDoc}
	 */
	@WebMethod
	public Ride createRide(String from, String to, Date date, int nPlaces, float price, String driverName)
			throws RideMustBeLaterThanTodayException, RideAlreadyExistException {

		dbManager.open();
		Ride ride = dbManager.createRide(from, to, date, nPlaces, price, driverName);
		dbManager.close();
		return ride;
	};

	/**
	 * {@inheritDoc}
	 */
	@WebMethod
	public List<Ride> getRides(String from, String to, Date date) {
		dbManager.open();
		List<Ride> rides = dbManager.getRides(from, to, date);
		dbManager.close();
		return rides;
	}

	@WebMethod
	public List<Ride> getRidesByDate(Date date) {
		dbManager.open();
		List<Ride> rides = dbManager.getRidesByDate(date);
		dbManager.close();
		return rides;
	}

	/**
	 * {@inheritDoc}
	 */
	@WebMethod
	public List<Date> getThisMonthDatesWithRides(String from, String to, Date date) {
		dbManager.open();
		List<Date> dates = dbManager.getThisMonthDatesWithRides(from, to, date);
		dbManager.close();
		return dates;
	}

	public void close() {
		DataAccess dB4oManager = new DataAccess();

		dB4oManager.close();

	}

	/**
	 * {@inheritDoc}
	 */
	@WebMethod
	public void initializeBD() {
		dbManager.open();
		dbManager.initializeDB();
		dbManager.close();
	}

	@Override
	public User getUser(String erab) {
		dbManager.open();
		User u = dbManager.getUser(erab);
		dbManager.close();
		return u;
	}

	@Override
	public boolean isRegistered(String erab, String passwd) {
		dbManager.open();
		boolean registered = dbManager.isRegistered(erab, passwd);
		dbManager.close();
		return registered;
	}

	@Override
	public Driver getDriver(String erab) {
		dbManager.open();
		Driver d = dbManager.getDriver(erab);
		dbManager.close();
		return d;
	}

	@Override
	public Traveler getTraveler(String erab) {
		dbManager.open();
		Traveler t = dbManager.getTraveler(erab);
		dbManager.close();
		return t;
	}

	@Override
	public String getMotaByUsername(String erab) {
		dbManager.open();
		String mota = dbManager.getMotabyUsername(erab);
		dbManager.close();
		return mota;
	}

	@Override
	public boolean addDriver(String username, String password) {
		dbManager.open();
		boolean ondo = dbManager.addDriver(username, password);
		dbManager.close();
		return ondo;
	}

	@Override
	public boolean addTraveler(String username, String password) {
		dbManager.open();
		boolean ondo = dbManager.addTraveler(username, password);
		dbManager.close();
		return ondo;
	}
}
