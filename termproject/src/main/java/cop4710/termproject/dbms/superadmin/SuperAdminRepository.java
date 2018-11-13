package cop4710.termproject.dbms.superadmin;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SuperAdminRepository extends JpaRepository<SuperAdmin, Long> 
{
	@Query("Select s from superadmin s Where s.name = :username")
	public Optional<SuperAdmin> findSAByName(@Param("username") String username);
}
