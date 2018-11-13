package cop4710.termproject.dbms.admin;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
@Transactional
@Repository
public class AdminService
{
	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(AdminService.class);
	
	@Autowired
	private AdminRepository repository;
	
	public long insert(Admin admin)
	{
		repository.save(admin);
		return admin.getId();
	}
	
	public Optional<Admin> find(long id)
	{
		return repository.findById(id);
	}
	
	public Optional<Admin> find(String name)
	{
		return repository.findAdminByName(name);
	}
	
	public List<Admin> findAll()
	{
		return repository.findAll();
	}
	
	public void delete(long id)
	{
		//log.info("Deleting " + id);
		repository.deleteById(id);
	}
	
	public long replace(long id, Admin admin)
	{
		//log.info("Starting delete of " + id);
		delete(id);
		//log.info("Inserting " + admin);
		return insert(admin);
	}
	
	public boolean adminExists(String username)
	{
		Optional<Admin> result = repository.findAdminByName(username);
		if (result.isPresent())
		{
			return true;
		}else
		{
			return false;
		}
	}
	
	public boolean validate(String username, String password) 
	{
		Optional<Admin> result = repository.findAdminByName(username);
		if (result.isPresent() && result.get().getPasswd().equals(password))
		{
			return true;
		} else
		{
			return false;
		}
	}
}
