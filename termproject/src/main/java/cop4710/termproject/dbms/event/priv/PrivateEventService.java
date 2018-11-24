package cop4710.termproject.dbms.event.priv;

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
public class PrivateEventService
{
	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(PrivateEventService.class);
	
	@Autowired
	private PrivateEventRepository repository;
	
	public long insert(PrivateEvent pe)
	{
		repository.save(pe);
		repository.flush();
		return pe.getId();
	}
	
	public Optional<PrivateEvent> find(long id)
	{
		return repository.findById(id);
	}
	
	public List<PrivateEvent> findAll()
	{
		return repository.findAll();
	}
	
	public List<PrivateEvent> findUnapproved()
	{
		return repository.findAllUnapproved();
	}
	
	public void delete(long id)
	{
		//log.info("Deleting " + id);
		repository.deleteById(id);
	}
	
	public long replace(long id, PrivateEvent pe)
	{
		//log.info("Starting delete of " + id);
		delete(id);
		//log.info("Inserting " + pe);
		return insert(pe);
	}
	
	public boolean collision(String loc, Long time)
	{
		if (repository.countEventsAtLocAndTime(loc, time) > 0)
		{
			return true;
		} else
		{
			return false;
		}
	}
}
