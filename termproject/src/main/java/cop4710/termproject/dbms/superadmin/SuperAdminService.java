package cop4710.termproject.dbms.superadmin;

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
public class SuperAdminService 
{
	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(SuperAdminService.class);
	
	@Autowired
	private SuperAdminRepository repository;
	
	public long insert(SuperAdmin sa)
	{
		repository.save(sa);
		return sa.getId();
	}
	
	public Optional<SuperAdmin> find(String name)
	{
		return repository.findSAByName(name);
	}
	
	public Optional<SuperAdmin> find(long id)
	{
		return repository.findById(id);
	}
	
	public List<SuperAdmin> findAll()
	{
		return repository.findAll();
	}
	
	public void delete(long id)
	{
		//log.info("Deleting " + id);
		repository.deleteById(id);
	}
	
	public long replace(long id, SuperAdmin sa)
	{
		//log.info("Starting delete of " + id);
		delete(id);
		//log.info("Inserting " + sa);
		return insert(sa);
	}
	
	public boolean superAdminExists(String username)
	{
		Optional<SuperAdmin> result = repository.findSAByName(username);
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
		Optional<SuperAdmin> result = repository.findSAByName(username);
		if (result.isPresent() && result.get().getPassword().equals(password))
		{
			return true;
		} else
		{
			return false;
		}
	}
}
