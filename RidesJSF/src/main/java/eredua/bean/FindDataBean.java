package eredua.bean;

import java.util.ArrayList;

import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import businessLogic.BLFacade;
import domain.Ride;

@ManagedBean(name = "finddataBean")
@SessionScoped
public class FindDataBean {
	private List<Ride> foundRides = new ArrayList<>();
	private Date date;
	private String from;
	private String to;

	public FindDataBean() {
		System.out.println(this.getData());
	}

	public void loadRides() {
		System.out.println("Selected Date: " + this.getData());
		if (this.getData() != null) {
			BLFacade facade = FacadeBean.getBusinessLogic();
			foundRides = facade.getRidesByDate(this.getData());
			System.out.println("Found " + foundRides.size() + " rides for date: " + this.getData());

		}
	}

	public Date getData() {
		DataBidaiakBean dataBidaiakBean = (DataBidaiakBean) FacesContext.getCurrentInstance().getExternalContext()
				.getSessionMap().get("databidaiakBean");
		if (dataBidaiakBean != null) {
			return dataBidaiakBean.getDate();
		}
		return null;
	}

	public String close() {
		return "Bidaiak";
	}

	public List<Ride> getFoundRides() {
		return foundRides;
	}

	public void setFoundRides(List<Ride> foundRides) {
		this.foundRides = foundRides;
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
}