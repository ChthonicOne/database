package cop4710.termproject.dbms.rso;

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
public class RSOService 
{
	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(RSOService.class);
	
	@Autowired
	private RSORepository repository;
	
	public long insert(RSO rso)
	{
		repository.save(rso);
		return rso.getId();
	}
	
	public Optional<RSO> find(long id)
	{
		return repository.findById(id);
	}
	
	public Optional<RSO> find(String name)
	{
		return repository.findRSOByName(name);
	}
	
	public List<RSO> findAll()
	{
		return repository.findAll();
	}
	
	public void delete(long id)
	{
		//log.info("Deleting " + id);
		repository.deleteById(id);
	}
	
	public long replace(long id, RSO rso)
	{
		//log.info("Starting delete of " + id);
		delete(id);
		//log.info("Inserting " + rso);
		return insert(rso);
	}

	public List<RSO> findRSOByUsername(String username)
	{
		return repository.findRSOByUsername(username);
	}
}
