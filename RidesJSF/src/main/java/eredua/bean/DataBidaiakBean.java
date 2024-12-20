package eredua.bean;

import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.SelectEvent;

@ManagedBean(name = "databidaiakBean")
@SessionScoped
public class DataBidaiakBean {

	private Date date = new Date();

	public DataBidaiakBean() {

	}

	public String bidaiak() {
		FindDataBean findDataBean = (FindDataBean) FacesContext.getCurrentInstance().getExternalContext()
				.getSessionMap().get("finddataBean");

		if (findDataBean == null) {
			findDataBean = new FindDataBean();
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("finddataBean", findDataBean);
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