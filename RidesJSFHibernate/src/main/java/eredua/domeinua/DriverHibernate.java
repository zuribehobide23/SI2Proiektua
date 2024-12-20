package eredua.domeinua;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

@Entity
@DiscriminatorValue("DRIVER")
public class DriverHibernate extends UserHibernate implements Serializable {

	private static final long serialVersionUID = 1L;

	@OneToMany(mappedBy = "driver", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<RideHibernate> createdRides = new ArrayList<>();

	public DriverHibernate() {
	}

	public DriverHibernate(String username, String passwd) {
		super(username, passwd, "Driver");
	}

	@Override
	public String toString() {
		return super.toString();
	}

	public List<RideHibernate> getCreatedRides() {
		return createdRides;
	}

	public void setCreatedRides(List<RideHibernate> createdRides) {
		this.createdRides = createdRides;
	}

	public RideHibernate addRide(String from, String to, Date date, int nPlaces, float price) {
		RideHibernate ride = new RideHibernate(from, to, date, nPlaces, price, this);
		createdRides.add(ride);
		return ride;
	}

	public boolean doesRideExists(String from, String to, Date date) {
		for (RideHibernate r : createdRides) {
			if ((java.util.Objects.equals(r.getFrom(), from)) && (java.util.Objects.equals(r.getTo(), to))
					&& (java.util.Objects.equals(r.getDate(), date))) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

	public RideHibernate removeRide(String from, String to, Date date) {
		RideHibernate rideToRemove = null;
		for (RideHibernate ride : createdRides) {
			if ((java.util.Objects.equals(ride.getFrom(), from)) && (java.util.Objects.equals(ride.getTo(), to))
					&& (java.util.Objects.equals(ride.getDate(), date))) {
				rideToRemove = ride;
				break;
			}
		}
		if (rideToRemove != null) {
			createdRides.remove(rideToRemove);
		}
		return rideToRemove;
	}
}
