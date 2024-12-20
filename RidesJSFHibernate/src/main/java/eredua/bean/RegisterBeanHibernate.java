package eredua.bean;

import javax.faces.application.FacesMessage;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import eredua.businessLogic.BLFacade;

@ManagedBean(name = "registerBeanHibernate")
@SessionScoped
public class RegisterBeanHibernate {

	private String username;
	private String password;
	private String mota;

	public RegisterBeanHibernate() {
	}

	public boolean register() {
		if (username != null && password != null) {
			BLFacade facade = FacadeBeanHibernate.getBusinessLogic();
			boolean added = false;
			if ("Driver".equals(mota)) {
				added = facade.addDriver(username, password);
			} else if ("Traveler".equals(mota)) {
				added = facade.addTraveler(username, password);
			}
			if (added) {
				resetFields();
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
						"You have been registered succesfully", "Success"));
			} else {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
						"There was an error in the registration, try another username", "Error"));
			}
			return true;
		}
		return false;
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

	public String getMota() {
		return mota;
	}

	public void setMota(String mota) {
		this.mota = mota;
	}

	public void resetFields() {
		this.username = null;
		this.mota = "";
	}

	public String close() {
		resetFields();
		return "Main";
	}
}