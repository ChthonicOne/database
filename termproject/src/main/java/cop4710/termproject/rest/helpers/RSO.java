package cop4710.termproject.rest.helpers;

public class RSO 
{
	Long id;
	String admin,
		   name;
	boolean active;
	
	public RSO(Long id, String admin, String name, boolean active)
	{
		this.id = id;
		this.admin = admin;
		this.name = name;
		this.active = active;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAdmin() {
		return admin;
	}

	public void setAdmin(String admin) {
		this.admin = admin;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
}
