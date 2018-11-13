package cop4710.termproject.dbms.admin;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AdminRepository extends JpaRepository<Admin, Long> 
{
	@Query("Select a from admin a Where a.name = :username")
	public Optional<Admin> findAdminByName(@Param("username") String username);
	
}
