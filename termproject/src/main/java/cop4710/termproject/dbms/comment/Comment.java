package cop4710.termproject.dbms.comment;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import cop4710.termproject.dbms.event.Event;
import cop4710.termproject.dbms.user.User;

@Entity(name = "comment")
@Table(name = "comment")
public class Comment
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cid", nullable = false)
	private long id;
	
	private String text;
	private double rating;
	private long timestamp;
	
	@ManyToOne
    @JoinColumn(name = "uid", nullable = false)
    private User user;
	
	@ManyToOne
    @JoinColumn(name = "eid", nullable = false)
    private Event event;
	
	public Comment(String text, double rating, long timestamp, User user, Event event)
	{
		super();
		this.text = text;
		this.rating = rating;
		this.timestamp = timestamp;
		this.user = user;
		this.event = event;
	}
	
	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public String getText()
	{
		return text;
	}

	public void setText(String text)
	{
		this.text = text;
	}

	public double getRating()
	{
		return rating;
	}

	public void setRating(double rating)
	{
		this.rating = rating;
	}

	public long getTimestamp()
	{
		return timestamp;
	}

	public void setTimestamp(long timestamp)
	{
		this.timestamp = timestamp;
	}

	public User getUser()
	{
		return user;
	}

	public void setUser(User user)
	{
		this.user = user;
	}

	public Event getEvent()
	{
		return event;
	}

	public void setEvent(Event event) 
	{
		this.event = event;
	}

	@Override
	public String toString()
	{
		Calendar time = Calendar.getInstance();
		time.setTimeInMillis(timestamp);
		return String.format("[%d] Event: %s -- %s -- %s -- %f -- %tD",
				id, event.getName(), text, user.getName(), rating, time);
	}
}
