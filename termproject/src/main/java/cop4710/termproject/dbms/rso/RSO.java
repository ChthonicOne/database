package cop4710.termproject.dbms.rso;

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
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import cop4710.termproject.dbms.admin.Admin;
import cop4710.termproject.dbms.event.rso.RSOEvent;
import cop4710.termproject.dbms.user.User;

@Entity(name = "rso")
@Table(name = "rso")
public class RSO
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "rid", nullable = false)
	private long id;
	
	private String name,
				   passwd/*,
				   university*/;
	private boolean active = false;
	
	@OneToMany(fetch = FetchType.LAZY,
            cascade =  CascadeType.ALL,
            orphanRemoval = true)
	@JoinColumn(name = "eid")
	private Set<RSOEvent> event = new HashSet<>();
	
	@OneToOne
    @JoinColumn(name = "aid", nullable = true)
    private Admin admin;
	
	@ManyToMany(fetch = FetchType.LAZY,
			cascade = CascadeType.ALL,
			mappedBy = "rso")
	@Transient
	private Set<User> users = new HashSet<>();
	
	public RSO(String name, String passwd, User user/*, String university*/)
	{
		super();
		this.name = name;
		this.passwd = passwd;
		this.users.add(user);
		//this.university = university;
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

	public Set<RSOEvent> getEvent() 
	{
		return event;
	}

	public void setEvent(Set<RSOEvent> event) 
	{
		this.event = event;
	}
	
	public void addEvent(RSOEvent event)
	{
		this.event.add(event);
	}

	public Admin getAdmin()
	{
		return admin;
	}

	public void setAdmin(Admin admin)
	{
		this.admin = admin;
	}

	public Set<User> getUsers() 
	{
		return users;
	}

	public void setUsers(Set<User> users) 
	{
		this.users = users;
	}
	
	public void addUser(User user)
	{
		this.users.add(user);
	}

	public boolean isActive()
	{
		return active;
	}

	public void setActive(boolean active) 
	{
		this.active = active;
	}

	@Override
	public String toString()
	{
		return String.format("[%d] Username %s",
				id, name);
	}
}
