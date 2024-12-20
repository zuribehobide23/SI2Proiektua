package eredua.bean;

import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.SelectEvent;

import businessLogic.BLFacade;
import exceptions.RideAlreadyExistException;
import exceptions.RideMustBeLaterThanTodayException;

@ManagedBean(name = "createRideBean")
@SessionScoped
public class CreateRideBean {

	private String from;
	private String to;
	private Date date;
	private int nPlaces;
	private float price;

	public CreateRideBean() {

	}

	public boolean createRide() {
		try {
			BLFacade facade = FacadeBean.getBusinessLogic();
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
		LoginBean loginBean = (LoginBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.get("loginBean");
		if (loginBean != null) {
			return loginBean.getUsername();
		}
		return null;
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