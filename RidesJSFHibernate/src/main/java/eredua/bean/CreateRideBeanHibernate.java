package eredua.bean;

import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.Column;

import org.primefaces.event.SelectEvent;

import eredua.businessLogic.BLFacade;
import exceptions.RideAlreadyExistException;
import exceptions.RideMustBeLaterThanTodayException;

@ManagedBean(name = "createRideBeanHibernate")
@SessionScoped
public class CreateRideBeanHibernate {
	@Column(name = "departure_city")
	private String from;
	@Column(name = "arrival_city")
	private String to;
	private Date date;
	private int nPlaces;
	private float price;

	public CreateRideBeanHibernate() {

	}

	public boolean createRide() {
		try {
			BLFacade facade = FacadeBeanHibernate.getBusinessLogic();
			facade.createRide(from, to, date, nPlaces, price, this.getUsername());
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Ride created successfully", "Success"));
			return true;
		} catch (RideMustBeLaterThanTodayException | RideAlreadyExistException e) {
			e.getMessage();
			return false;
		}
	}

	public String getUsername() {
		LoginBeanHibernate loginBean = (LoginBeanHibernate) FacesContext.getCurrentInstance().getExternalContext()
				.getSessionMap().get("loginBeanHibernate");
		if (loginBean != null) {
			return loginBean.getUsername();
		}
		return "Zuri";
	}

	public String close() {
		resetFields();
		return "Driver";
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getnPlaces() {
		return nPlaces;
	}

	public void setnPlaces(int nPlaces) {
		this.nPlaces = nPlaces;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public void resetFields() {
		this.from = null;
		this.to = null;
		this.nPlaces = 0;
		this.price = 0;
		this.date = new Date();
	}

	public void onDateSelect(SelectEvent event) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Data aukeratua: " + event.getObject()));
	}
}