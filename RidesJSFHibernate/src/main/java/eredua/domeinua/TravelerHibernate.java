package eredua.domeinua;

import javax.persistence.Entity;
import javax.persistence.DiscriminatorValue;
import java.io.Serializable;

@Entity
@DiscriminatorValue("TRAVELER")
public class TravelerHibernate extends UserHibernate implements Serializable {

	private static final long serialVersionUID = 1L;

	public TravelerHibernate() {
	}

	public TravelerHibernate(String username, String passwd) {
		super(username, passwd, "Traveler");
	}

	@Override
	public String toString() {
		return super.toString();
	}
}
