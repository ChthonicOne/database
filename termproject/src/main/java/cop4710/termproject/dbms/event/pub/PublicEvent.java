package cop4710.termproject.dbms.event.pub;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import cop4710.termproject.dbms.event.Event;
import cop4710.termproject.dbms.superadmin.SuperAdmin;

@Entity(name = "publicevent")
@Table(name = "publicevent", 
	   uniqueConstraints=
	   @UniqueConstraint(columnNames={"time", "location"}))
public class PublicEvent extends Event
{
	@ManyToOne
    @JoinColumn(name = "said", nullable = false)
	private SuperAdmin admin;
	
	public PublicEvent(String name, String desc, String location, long time, SuperAdmin admin)
	{
		super(name, desc, location, time);
		this.admin = admin;
	}
	
	public SuperAdmin getAdmin() 
	{
		return admin;
	}

	public void setAdmin(SuperAdmin admin) 
	{
		this.admin = admin;
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
