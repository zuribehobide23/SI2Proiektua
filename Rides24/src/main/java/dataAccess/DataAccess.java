package dataAccess;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import configuration.ConfigXML;
import configuration.UtilDate;
import domain.*;
import exceptions.RideAlreadyExistException;
import exceptions.RideMustBeLaterThanTodayException;

/**
 * It implements the data access to the objectDb database
 */
public class DataAccess {
	private EntityManager db;
	private EntityManagerFactory emf;

	ConfigXML c = ConfigXML.getInstance();

	public DataAccess() {
		if (c.isDatabaseInitialized()) {
			String fileName = c.getDbFilename();

			File fileToDelete = new File(fileName);
			if (fileToDelete.delete()) {
				File fileToDeleteTemp = new File(fileName + "$");
				fileToDeleteTemp.delete();

				System.out.println("File deleted");
			} else {
				System.out.println("Operation failed");
			}
		}
		open();
		if (c.isDatabaseInitialized()) {
			initializeDB();
		}

		System.out.println("DataAccess created => isDatabaseLocal: " + c.isDatabaseLocal() + " isDatabaseInitialized: "
				+ c.isDatabaseInitialized());

		close();

	}

	// This constructor is used to mock the DB
	public DataAccess(EntityManager db) {
		this.db = db;
	}

	/**
	 * This is the data access method that initializes the database with some events
	 * and questions. This method is invoked by the business logic (constructor of
	 * BLFacadeImplementation) when the option "initialize" is declared in the tag
	 * dataBaseOpenMode of resources/config.xml file
	 */
	public void initializeDB() {
		db.getTransaction().begin();
		try {
			Driver driver1 = new Driver("Urtzi", "123");
			Driver driver2 = new Driver("Zuri", "456");
			db.persist(driver1);
			db.persist(driver2);

			Traveler traveler1 = new Traveler("Unax", "789");
			Traveler traveler2 = new Traveler("Luken", "abc");
			db.persist(traveler1);
			db.persist(traveler2);

			Calendar cal = Calendar.getInstance();
			cal.set(2024, Calendar.MAY, 20);
			Date date1 = UtilDate.trim(cal.getTime());

			cal.set(2024, Calendar.MAY, 30);
			Date date2 = UtilDate.trim(cal.getTime());

			cal.set(2024, Calendar.MAY, 10);
			Date date3 = UtilDate.trim(cal.getTime());

			cal.set(2024, Calendar.MAY, 20);
			Date date4 = UtilDate.trim(cal.getTime());

			driver1.addRide("Donostia", "Madrid", date2, 5, 20); // ride1
			driver1.addRide("Irun", "Donostia", date2, 5, 2); // ride2
			driver1.addRide("Madrid", "Donostia", date3, 5, 5); // ride3
			driver1.addRide("Barcelona", "Madrid", date4, 0, 10); // ride4
			driver2.addRide("Donostia", "Hondarribi", date1, 5, 3); // ride5

			db.getTransaction().commit();
			System.out.println("Db initialized");

		} catch (Exception e) {
			e.printStackTrace();
			db.getTransaction().rollback();
		}
	}

	/**
	 * This method returns all the cities where rides depart
	 * 
	 * @return collection of cities
	 */
	public List<String> getDepartCities() {
		TypedQuery<String> query = db.createQuery("SELECT DISTINCT r.from FROM Ride r ORDER BY r.from", String.class);
		List<String> cities = query.getResultList();
		return cities;

	}

	/**
	 * This method returns all the arrival destinations, from all rides that depart
	 * from a given city
	 * 
	 * @param from the depart location of a ride
	 * @return all the arrival destinations
	 */
	public List<String> getArrivalCities(String from) {
		TypedQuery<String> query = db.createQuery("SELECT DISTINCT r.to FROM Ride r WHERE r.from=?1 ORDER BY r.to",
				String.class);
		query.setParameter(1, from);
		List<String> arrivingCities = query.getResultList();
		return arrivingCities;

	}

	/**
	 * This method creates a ride for a driver
	 * 
	 * @param from        the origin location of a ride
	 * @param to          the destination location of a ride
	 * @param date        the date of the ride
	 * @param nPlaces     available seats
	 * @param driverEmail to which ride is added
	 * 
	 * @return the created ride, or null, or an exception
	 * @throws RideMustBeLaterThanTodayException if the ride date is before today
	 * @throws RideAlreadyExistException         if the same ride already exists for
	 *                                           the driver
	 */
	public Ride createRide(String from, String to, Date date, int nPlaces, float price, String driverName)
			throws RideAlreadyExistException, RideMustBeLaterThanTodayException {
		System.out.println(
				">> DataAccess: createRide=> from= " + from + " to= " + to + " driver=" + driverName + " date " + date);
		if (driverName == null)
			return null;
		try {
			if (new Date().compareTo(date) > 0) {
				System.out.println("ppppp");
				throw new RideMustBeLaterThanTodayException(
						ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.ErrorRideMustBeLaterThanToday"));
			}

			db.getTransaction().begin();
			Driver driver = db.find(Driver.class, driverName);
			if (driver.doesRideExists(from, to, date)) {
				db.getTransaction().commit();
				throw new RideAlreadyExistException(
						ResourceBundle.getBundle("Etiquetas").getString("DataAccess.RideAlreadyExist"));
			}
			Ride ride = driver.addRide(from, to, date, nPlaces, price);
			// next instruction can be obviated
			db.persist(driver);
			db.getTransaction().commit();

			return ride;
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			return null;
		}

	}

	/**
	 * This method retrieves the rides from two locations on a given date
	 * 
	 * @param from the origin location of a ride
	 * @param to   the destination location of a ride
	 * @param date the date of the ride
	 * @return collection of rides
	 */
	public List<Ride> getRides(String from, String to, Date date) {
		System.out.println(">> DataAccess: getActiveRides=> from= " + from + " to= " + to + " date " + date);

		List<Ride> res = new ArrayList<>();
		TypedQuery<Ride> query = db.createQuery(
				"SELECT r FROM Ride r WHERE r.from = ?1 AND r.to = ?2 AND r.date = ?3 AND r.active = true", Ride.class);
		query.setParameter(1, from);
		query.setParameter(2, to);
		query.setParameter(3, date);
		List<Ride> rides = query.getResultList();
		for (Ride ride : rides) {
			res.add(ride);
		}
		return res;
	}

	public List<Ride> getRidesByDate(Date date) {
		System.out.println(">> DataAccess: getActiveRides=> date " + date);

		List<Ride> res = new ArrayList<>();
		TypedQuery<Ride> query = db.createQuery("SELECT r FROM Ride r WHERE r.date = ?1 AND r.active = true",
				Ride.class);
		query.setParameter(1, date);
		List<Ride> rides = query.getResultList();
		for (Ride ride : rides) {
			res.add(ride);
		}
		return res;
	}

	/**
	 * This method retrieves from the database the dates a month for which there are
	 * events
	 * 
	 * @param from the origin location of a ride
	 * @param to   the destination location of a ride
	 * @param date of the month for which days with rides want to be retrieved
	 * @return collection of rides
	 */
	public List<Date> getThisMonthDatesWithRides(String from, String to, Date date) {
		System.out.println(">> DataAccess: getThisMonthActiveRideDates");

		List<Date> res = new ArrayList<>();

		Date firstDayMonthDate = UtilDate.firstDayMonth(date);
		Date lastDayMonthDate = UtilDate.lastDayMonth(date);

		TypedQuery<Date> query = db.createQuery(
				"SELECT DISTINCT r.date FROM Ride r WHERE r.from=?1 AND r.to=?2 AND r.date BETWEEN ?3 and ?4 AND r.active = true",
				Date.class);

		query.setParameter(1, from);
		query.setParameter(2, to);
		query.setParameter(3, firstDayMonthDate);
		query.setParameter(4, lastDayMonthDate);
		List<Date> dates = query.getResultList();
		res.addAll(dates);

		return res;
	}

	public void open() {

		String fileName = c.getDbFilename();
		if (c.isDatabaseLocal()) {
			emf = Persistence.createEntityManagerFactory("objectdb:" + fileName);
			db = emf.createEntityManager();
		} else {
			Map<String, String> properties = new HashMap<>();
			properties.put("javax.persistence.jdbc.user", c.getUser());
			properties.put("javax.persistence.jdbc.password", c.getPassword());

			emf = Persistence.createEntityManagerFactory(
					"objectdb://" + c.getDatabaseNode() + ":" + c.getDatabasePort() + "/" + fileName, properties);
			db = emf.createEntityManager();
		}
		System.out.println("DataAccess opened => isDatabaseLocal: " + c.isDatabaseLocal());

	}

	public void close() {
		db.close();
		System.out.println("DataAcess closed");
	}

	public User getUser(String erab) {
		TypedQuery<User> query = db.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class);
		query.setParameter("username", erab);
		return query.getSingleResult();
	}

	public boolean isRegistered(String erab, String passwd) {
		TypedQuery<Long> travelerQuery = db.createQuery(
				"SELECT COUNT(t) FROM Traveler t WHERE t.username = :username AND t.passwd = :passwd", Long.class);
		travelerQuery.setParameter("username", erab);
		travelerQuery.setParameter("passwd", passwd);
		Long travelerCount = travelerQuery.getSingleResult();

		TypedQuery<Long> driverQuery = db.createQuery(
				"SELECT COUNT(d) FROM Driver d WHERE d.username = :username AND d.passwd = :passwd", Long.class);
		driverQuery.setParameter("username", erab);
		driverQuery.setParameter("passwd", passwd);
		Long driverCount = driverQuery.getSingleResult();

		return travelerCount > 0 || driverCount > 0;
	}

	public Driver getDriver(String erab) {
		TypedQuery<Driver> query = db.createQuery("SELECT d FROM Driver d WHERE d.username = :username", Driver.class);
		query.setParameter("username", erab);
		List<Driver> resultList = query.getResultList();
		if (resultList.isEmpty()) {
			return null;
		} else {
			return resultList.get(0);
		}
	}

	public Traveler getTraveler(String erab) {
		TypedQuery<Traveler> query = db.createQuery("SELECT t FROM Traveler t WHERE t.username = :username",
				Traveler.class);
		query.setParameter("username", erab);
		List<Traveler> resultList = query.getResultList();
		if (resultList.isEmpty()) {
			return null;
		} else {
			return resultList.get(0);
		}
	}

	public String getMotabyUsername(String erab) {
		TypedQuery<String> driverQuery = db.createQuery("SELECT d.mota FROM Driver d WHERE d.username = :username",
				String.class);
		driverQuery.setParameter("username", erab);
		List<String> driverResultList = driverQuery.getResultList();

		TypedQuery<String> travelerQuery = db.createQuery("SELECT t.mota FROM Traveler t WHERE t.username = :username",
				String.class);
		travelerQuery.setParameter("username", erab);
		List<String> travelerResultList = travelerQuery.getResultList();

		if (!driverResultList.isEmpty()) {
			return driverResultList.get(0);
		} else if (!travelerResultList.isEmpty()) {
			return travelerResultList.get(0);
		} else {
			return "Admin";
		}
	}

	public boolean addDriver(String username, String password) {
		try {
			db.getTransaction().begin();
			Driver existingDriver = getDriver(username);
			Traveler existingTraveler = getTraveler(username);
			if (existingDriver != null || existingTraveler != null) {
				return false;
			}

			Driver driver = new Driver(username, password);
			db.persist(driver);
			db.getTransaction().commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			db.getTransaction().rollback();
			return false;
		}
	}

	public boolean addTraveler(String username, String password) {
		try {
			db.getTransaction().begin();

			Driver existingDriver = getDriver(username);
			Traveler existingTraveler = getTraveler(username);
			if (existingDriver != null || existingTraveler != null) {
				return false;
			}

			Traveler traveler = new Traveler(username, password);
			db.persist(traveler);
			db.getTransaction().commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			db.getTransaction().rollback();
			return false;
		}
	}
}
