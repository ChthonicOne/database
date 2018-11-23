package cop4710.termproject.dbms.event.rso;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import cop4710.termproject.dbms.event.Event;
import cop4710.termproject.dbms.rso.RSO;

@Entity(name = "rsoevent")
@Table(name = "rsoevent", 
	   uniqueConstraints=
	   @UniqueConstraint(columnNames={"time", "location"}))
public class RSOEvent extends Event 
{
	@ManyToOne
    @JoinColumn(name = "rid", nullable = false)
	private RSO rso;
	
	public RSOEvent() {}
	
	public RSOEvent(String name, String desc, String location, long time, RSO rso)
	{
		super(name, desc, location, time);
		this.rso = rso;
	}
	
	public RSO getRso() {
		return rso;
	}

	public void setRso(RSO rso) {
		this.rso = rso;
	}

	@Override
	public String toString()
	{
		Calendar date = Calendar.getInstance();
		date.setTimeInMillis(super.getTime());
		return String.format("[%d] Event Name %s by %s: Location %s: %tD",
				super.getId(), super.getName(), rso.getName(), super.getLocation(), date);
	}
}
