package domain;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@DiscriminatorValue("TRAVELER")
public class Traveler extends User implements Serializable {
	private static final long serialVersionUID = 1L;

	public Traveler(String username, String passwd) {
		super(username, passwd, "Traveler");
	}

	@Override
	public String toString() {
		return super.toString();
	}
}
