package cop4710.termproject.dbms.rso;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RSORepository extends JpaRepository<RSO, Long> 
{
	@Query("Select r From rso r Join user u Join r.admin a Where u.name = :username")
	List<RSO> findRSOByUsername(@Param("username") String username);
	@Query("Select r From rso r Where r.name = :name")
	Optional<RSO> findRSOByName(@Param("name") String name);

}
