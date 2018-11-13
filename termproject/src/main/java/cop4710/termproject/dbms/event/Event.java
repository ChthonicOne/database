package cop4710.termproject.dbms.event;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;


import cop4710.termproject.dbms.comment.Comment;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Event 
{
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	@Column(name = "eid", nullable = false)
	private long id;
	
	private String name,
				   desc;
	
	@Column(name = "location", nullable = false)
	private String location;
	
	@Column(name = "time", nullable = false)
	private long time;
	
	@OneToMany(fetch = FetchType.LAZY,
            cascade =  CascadeType.ALL,
            orphanRemoval = true)
	@JoinColumn(name = "cid")
	private Set<Comment> comment = new HashSet<>();
	
	public Event() {}
	
	public Event(String name, String desc, String location, long time)
	{
		super();
		this.name = name;
		this.desc = desc;
		this.location = location;
		this.time = time;
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

	public String getDesc()
	{
		return desc;
	}

	public void setDesc(String desc)
	{
		this.desc = desc;
	}

	public String getLocation()
	{
		return location;
	}

	public void setLocation(String location)
	{
		this.location = location;
	}

	public long getTime()
{
		return time;
	}

	public void setTime(long time)
	{
		this.time = time;
	}

	public Set<Comment> getComment()
	{
		return comment;
	}

	public void setComment(Set<Comment> comment) 
	{
		this.comment = comment;
	}
	
	public void addComment(Comment comment)
	{
		this.comment.add(comment);
	}
}
