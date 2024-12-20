package eredua.domeinua;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class UserHibernate implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String username;
	private String passwd;
	private String mota;

	public UserHibernate() {
	}

	public UserHibernate(String username, String passwd, String mota) {
		this.username = username;
		this.passwd = passwd;
		this.mota = mota;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return passwd;
	}

	public void setPassword(String passwd) {
		this.passwd = passwd;
	}

	public String getMota() {
		return mota;
	}

	public void setMota(String mota) {
		this.mota = mota;
	}

	@Override
	public String toString() {
		return username + ";" + passwd;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		UserHibernate other = (UserHibernate) obj;
		return java.util.Objects.equals(username, other.username);
	}
}
