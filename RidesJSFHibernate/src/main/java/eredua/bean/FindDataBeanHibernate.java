package eredua.bean;

import java.util.ArrayList;

import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import eredua.businessLogic.BLFacade;
import eredua.domeinua.*;

@ManagedBean(name = "finddataBeanHibernate")
@SessionScoped
public class FindDataBeanHibernate {
	private List<RideHibernate> foundRides = new ArrayList<>();
	private Date date;
	private String from;
	private String to;

	public FindDataBeanHibernate() {
		System.out.println(this.getData());
	}

	public void loadRides() {
		if (this.getData() != null) {
			BLFacade facade = FacadeBeanHibernate.getBusinessLogic();
			foundRides = facade.getRidesByDate(this.getData());
		}
	}

	public Date getData() {
		DataBidaiakBeanHibernate dataBidaiakBean = (DataBidaiakBeanHibernate) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("databidaiakBeanHibernate");
		if (dataBidaiakBean != null) {
			return dataBidaiakBean.getDate();
		}
		return null;
	}

	public String close() {
		return "Bidaiak";
	}

	public List<RideHibernate> getFoundRides() {
		return foundRides;
	}

	public void setFoundRides(List<RideHibernate> foundRides) {
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