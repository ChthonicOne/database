package cop4710.termproject.dbms.event.pub;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PublicEventRepository extends JpaRepository<PublicEvent, Long> 
{
	@Query("Select Count (p.id) From publicevent p Where p.location = :location And p.time = :time")
	public Long countEventsAtLocAndTime(@Param("location") String location, @Param("time") Long time);
}
