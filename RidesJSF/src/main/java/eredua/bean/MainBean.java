package eredua.bean;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "mainBean")
@SessionScoped
public class MainBean {

	public String getCreateRideLabel() {
		return "CreateRide";
	}

	public String getQueryRidesLabel() {
		return "QueryRides";
	}
	
	public String getBidaiakLabel() {
		return "Bidaiak";
	}
}
