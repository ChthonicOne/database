package cop4710.termproject.dbms.superadmin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SALoader 
{
	private SuperAdminRepository repository;
	
	@Autowired
	public SALoader(SuperAdminRepository repository)
	{
		this.repository = repository;
		LoadUsers();
	}
	
	private void LoadUsers()
	{
		repository.save(new SuperAdmin("admin", "password"));
	}
}
