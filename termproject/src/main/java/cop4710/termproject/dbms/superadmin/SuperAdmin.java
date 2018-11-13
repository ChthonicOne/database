package cop4710.termproject.dbms.superadmin;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import cop4710.termproject.dbms.event.pub.PublicEvent;

@Entity(name = "superadmin")
@Table(name = "superadmin")
public class SuperAdmin
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "said", nullable = false)
	private long id;
	private String name,
				   password;
	
	@OneToMany(fetch = FetchType.LAZY,
            cascade =  CascadeType.ALL,
            orphanRemoval = true)
	@JoinColumn(name = "eid")
	private Set<PublicEvent> event = new HashSet<>();
	
	public SuperAdmin(String name, String password)
	{
		super();
		this.name = name;
		this.password = password;
	}
	
	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<PublicEvent> getEvent()
	{
		return event;
	}

	public void setEvent(Set<PublicEvent> event)
	{
		this.event = event;
	}
	
	public void addEvent(PublicEvent event)
	{
		this.event.add(event);
	}

		@Override
	public String toString()
	{
		return String.format("[%d] SuperAdmin: %s",
				id, name);
	}
}
