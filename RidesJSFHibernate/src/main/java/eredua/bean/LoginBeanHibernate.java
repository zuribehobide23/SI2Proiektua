package eredua.bean;

import javax.faces.application.FacesMessage;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import eredua.businessLogic.BLFacade;

@ManagedBean(name = "loginBeanHibernate")
@SessionScoped
public class LoginBeanHibernate {

	private String username;
	private String password;

	public LoginBeanHibernate() {
	}

	public String login() {
		if (username != null && !username.trim().isEmpty() && password != null && !password.trim().isEmpty()) {
			BLFacade facade = FacadeBeanHibernate.getBusinessLogic();
			boolean sartu = facade.isRegistered(username, password);
			if (sartu) {
				String mota = facade.getMotaByUsername(username);
				if (mota.equals("Driver")) {
					return "Driver";
				} else if (mota.equals("Traveler")) {
					return "Traveler";
				}
			} else {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Error, username or password is incorrect", "Error"));
			}
		}
		return null;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String close() {
		return "Main";
	}
}