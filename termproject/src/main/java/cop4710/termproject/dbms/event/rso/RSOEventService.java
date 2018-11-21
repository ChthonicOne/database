package cop4710.termproject.dbms.event.rso;

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
public class RSOEventService 
{
	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(RSOEventService.class);
	
	@Autowired
	private RSOEventRepository repository;
	
	public long insert(RSOEvent re)
	{
		repository.save(re);
		return re.getId();
	}
	
	public Optional<RSOEvent> find(long id)
	{
		return repository.findById(id);
	}
	
	public List<RSOEvent> findAll()
	{
		return repository.findAll();
	}
	
	public void delete(long id)
	{
		//log.info("Deleting " + id);
		repository.deleteById(id);
	}
	
	public long replace(long id, RSOEvent re)
	{
		//log.info("Starting delete of " + id);
		delete(id);
		//log.info("Inserting " + re);
		return insert(re);
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
