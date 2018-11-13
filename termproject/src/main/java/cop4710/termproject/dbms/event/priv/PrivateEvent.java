package cop4710.termproject.dbms.event.priv;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import cop4710.termproject.dbms.admin.Admin;
import cop4710.termproject.dbms.event.Event;

@Entity(name = "privateevent")
@Table(name = "privateevent", 
	   uniqueConstraints=
	   @UniqueConstraint(columnNames={"time", "location"}))
public class PrivateEvent extends Event
{
	@ManyToOne
    @JoinColumn(name = "aid", nullable = false)
	private Admin admin;
	
	private boolean approved = false;
	//private String university;
	
	public PrivateEvent(String name, String desc, String location, long time, Admin admin/*, String university*/)
	{
		super(name, desc, location, time);
		this.admin = admin;
		//this.university = university;
	}
	
	public Admin getAdmin()
	{
		return admin;
	}

	public void setAdmin(Admin admin)
	{
		this.admin = admin;
	}

	public boolean isApproved() 
	{
		return approved;
	}

	public void setApproved(boolean approved) 
	{
		this.approved = approved;
	}

	@Override
	public String toString()
	{
		Calendar date = Calendar.getInstance();
		date.setTimeInMillis(super.getTime());
		return String.format("[%d] Event Name %s by %s: Location %s: %tD",
				super.getId(), super.getName(), admin.getName(), super.getLocation(), date);
	}
}
