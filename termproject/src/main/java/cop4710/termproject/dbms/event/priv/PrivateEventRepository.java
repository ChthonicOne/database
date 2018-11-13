package cop4710.termproject.dbms.event.priv;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PrivateEventRepository extends JpaRepository<PrivateEvent, Long> 
{
	@Query("Select Count (p.id) From privateevent p Where p.location = :location And p.time = :time")
	public Long countEventsAtLocAndTime(@Param("location") String location, @Param("time") Long time);
	@Query("Select p From privateevent p Where p.approved = FALSE")
	public List<PrivateEvent> findAllUnapproved();
}
