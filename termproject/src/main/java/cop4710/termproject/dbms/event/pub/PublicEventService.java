package cop4710.termproject.dbms.event.pub;

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
public class PublicEventService
{
	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(PublicEventService.class);
	
	@Autowired
	private PublicEventRepository repository;
	
	public long insert(PublicEvent pe)
	{
		repository.save(pe);
		return pe.getId();
	}
	
	public Optional<PublicEvent> find(long id)
	{
		return repository.findById(id);
	}
	
	public List<PublicEvent> findAll()
	{
		return repository.findAll();
	}
	
	public void delete(long id)
	{
		//log.info("Deleting " + id);
		repository.deleteById(id);
	}
	
	public long replace(long id, PublicEvent pe)
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
