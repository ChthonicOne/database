package cop4710.termproject.dbms.user;

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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import cop4710.termproject.dbms.comment.Comment;
import cop4710.termproject.dbms.rso.RSO;

/**
 * 
 * @author Mel Pelchat
 *
 */

@Entity(name = "user")
@Table(name = "user")
public class User 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "uid", nullable = false)
	private long id;
	
	private String name,
				   passwd/*,
				   university*/;
	
	@OneToMany(fetch = FetchType.LAZY,
            cascade =  CascadeType.ALL,
            orphanRemoval = true)
	@JoinColumn(name = "cid")
	private Set<Comment> comment = new HashSet<>();
	
	@ManyToMany(fetch = FetchType.LAZY,
			cascade = CascadeType.ALL)
	@JoinTable(name = "user_rso", 
	           joinColumns = @JoinColumn(name = "uid", 
	                                     referencedColumnName = "uid"), 
	           inverseJoinColumns = @JoinColumn(name = "rid", 
	                                            referencedColumnName = "rid"))
	private Set<RSO> rsos = new HashSet<>();
	
	public User() {}
	
	public User(String name, String passwd/*, String university*/)
	{
		super();
		this.name = name;
		this.passwd = passwd;
		//this.university = unviversity;
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

	public Set<Comment> getProcess()
	{
		return comment;
	}

	public void setProcess(Set<Comment> comment)
	{
		this.comment = comment;
	}
	
	public void addProcess(Comment comment)
	{
		this.comment.add(comment);
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

	public Set<RSO> getRsos() 
	{
		return rsos;
	}

	public void setRsos(Set<RSO> rsos)
	{
		this.rsos = rsos;
	}
	
	public void addRso(RSO rso)
	{
		this.rsos.add(rso);
	}

	@Override
	public String toString()
	{
		return String.format("[%d] Username %s",
				id, name);
	}
}
