package cop4710.termproject.dbms.user;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import cop4710.termproject.dbms.rso.RSO;

@Service
@Transactional
@Repository
public class UserService
{
	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private UserRepository repository;
	
	public long insert(User user)
	{
		repository.save(user);
		return user.getId();
	}
	
	public Optional<User> find(long id)
	{
		return repository.findById(id);
	}
	
	public Optional<User> find(String name)
	{
		return repository.findUserByName(name);
	}
	
	public List<User> findAll()
	{
		return repository.findAll();
	}
	
	public void delete(long id)
	{
		//log.info("Deleting " + id);
		repository.deleteById(id);
	}
	
	public long replace(long id, User user)
	{
		//log.info("Starting delete of " + id);
		delete(id);
		//log.info("Inserting " + user);
		return insert(user);
	}
	
	public boolean userExists(String username)
	{
		Optional<User> result = repository.findUserByName(username);
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
		Optional<User> result = repository.findUserByName(username);
		if (result.isPresent() && result.get().getPasswd().equals(password))
		{
			return true;
		} else
		{
			return false;
		}
	}

	public void addRSOToRecord(String username, Long id, RSO result2) 
	{
		Optional<User> result = repository.findUserByName(username);
		if (result.isPresent())
		{
			User user = result.get();
			user.addRso(result2);
			repository.save(user);
		}
		//TODO send error if user isn't there
	}
	
	public Long countUsersInRSO(String name)
	{
		return repository.countUsersInRSO(name);
	}
}
