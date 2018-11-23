package cop4710.termproject.rest.helpers;

public class EventComment 
{
	String text,
		   user;
	Double rating;
	Long time, id;
	
	public EventComment(Long id, String text, String user, Double rating, Long time)
	{
		this.id = id;
		this.text = text;
		this.user = user;
		this.rating = rating;
		this.time = time;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id) 
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

	public String getUser()
	{
		return user;
	}

	public void setUser(String user)
	{
		this.user = user;
	}

	public Double getRating()
	{
		return rating;
	}

	public void setRating(Double rating)
	{
		this.rating = rating;
	}

	public Long getTime()
	{
		return time;
	}

	public void setTime(Long time) 
	{
		this.time = time;
	}
}
