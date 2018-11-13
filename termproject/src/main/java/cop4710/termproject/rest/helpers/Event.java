package cop4710.termproject.rest.helpers;

public class Event 
{
	private Long id;
	
	private String name,
				   desc,
				   location,
				   type;
	
	private long time;
	
	public Event(Long id, String name, String desc, String location, String type, long time)
	{
		this.id = id;
		this.name = name;
		this.desc = desc;
		this.location = location;
		this.type = type;
		this.time = time;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public long getTime()
{
		return time;
	}

	public void setTime(long time)
	{
		this.time = time;
	}

}
