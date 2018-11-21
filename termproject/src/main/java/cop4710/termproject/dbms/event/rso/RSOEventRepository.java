package cop4710.termproject.dbms.event.rso;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RSOEventRepository extends JpaRepository<RSOEvent, Long> 
{
	@Query("Select r from rsoevent r Join r.rso")
	public List<RSOEvent> findAll();
	@Query("Select Count (p.id) From rsoevent p Where p.location = :location And p.time = :time")
	public Long countEventsAtLocAndTime(@Param("location") String location, @Param("time") Long time);
}
