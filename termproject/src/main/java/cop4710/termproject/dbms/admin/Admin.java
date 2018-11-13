package cop4710.termproject.dbms.admin;

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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import cop4710.termproject.dbms.event.priv.PrivateEvent;
import cop4710.termproject.dbms.rso.RSO;

@Entity(name = "admin")
@Table(name = "admin")
public class Admin 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "aid", nullable = false)
	private long id;
	
	private String name,
				   passwd;
	
	@OneToMany(fetch = FetchType.LAZY,
            cascade =  CascadeType.ALL,
            orphanRemoval = true)
	@JoinColumn(name = "eid")
	private Set<PrivateEvent> event = new HashSet<>();
	
	@OneToOne(fetch = FetchType.LAZY,
            cascade =  CascadeType.ALL,
            orphanRemoval = true)
	@JoinColumn(name = "rid")
	private RSO rso;
	

	public Admin(String name, String passwd, RSO rso)
	{
		super();
		this.name = name;
		this.passwd = passwd;
		this.rso = rso;
	}
	
	public long getId()
	{
		return id;
	}

	public void setId(long id) 
	{
		this.id = id;
	}

	public String getName() 
	{
		return name;
	}

	public void setName(String name) 
	{
		this.name = name;
	}

	public String getPasswd() 
	{
		return passwd;
	}

	public void setPasswd(String passwd)
	{
		this.passwd = passwd;
	}

	public Set<PrivateEvent> getEvent()
	{
		return event;
	}

	public void setEvent(Set<PrivateEvent> event)
	{
		this.event = event;
	}
	
	public void addEvent(PrivateEvent event)
	{
		this.event.add(event);
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
		return String.format("[%d] Username %s",
				id, name);
	}
}
