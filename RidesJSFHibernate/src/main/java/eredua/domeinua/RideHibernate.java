package eredua.domeinua;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class RideHibernate implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer rideNumber;

	private String from;
	private String to;
	private int nPlaces;
	private Date date;
	private double price;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "driver")
	private DriverHibernate driver;

	private boolean active;

	public RideHibernate() {
	}

	public RideHibernate(String from, String to, Date date, int nPlaces, double price, DriverHibernate driver) {
		this.from = from;
		this.to = to;
		this.date = date;
		this.nPlaces = nPlaces;
		this.price = price;
		this.driver = driver;
		this.active = true;
	}

	public Integer getRideNumber() {
		return rideNumber;
	}

	public void setRideNumber(Integer rideNumber) {
		this.rideNumber = rideNumber;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public int getnPlaces() {
		return nPlaces;
	}

	public void setnPlaces(int nPlaces) {
		this.nPlaces = nPlaces;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public DriverHibernate getDriver() {
		return driver;
	}

	public void setDriver(DriverHibernate driver) {
		this.driver = driver;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	@Override
	public String toString() {
		return from + "; " + to + "; " + date + "; " + nPlaces + " " + price;
	}
}
