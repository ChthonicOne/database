package cop4710.termproject.initial;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cop4710.termproject.dbms.superadmin.SuperAdmin;
import cop4710.termproject.dbms.superadmin.SuperAdminRepository;

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
