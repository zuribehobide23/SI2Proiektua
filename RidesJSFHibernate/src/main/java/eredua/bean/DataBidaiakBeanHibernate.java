package eredua.bean;

import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.SelectEvent;

@ManagedBean(name = "databidaiakBeanHibernate")
@SessionScoped
public class DataBidaiakBeanHibernate {

	private Date date = new Date();

	public DataBidaiakBeanHibernate() {

	}

	public String bidaiak() {
		FindDataBeanHibernate findDataBean = (FindDataBeanHibernate) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("finddataBeanHibernate");

		if (findDataBean == null) {
			findDataBean = new FindDataBeanHibernate();
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("finddataBeanHibernate",
					findDataBean);
		}

		findDataBean.setDate(this.date);
		findDataBean.loadRides();

		return "FindData";
	}

	public String close() {
		return "Main";
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void onDateSelect(SelectEvent event) {
		this.date = (Date) event.getObject();
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Selected Date: " + this.date));
	}
}