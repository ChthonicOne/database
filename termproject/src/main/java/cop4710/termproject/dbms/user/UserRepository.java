package cop4710.termproject.dbms.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long>
{
	@Query("Select u from user u Where u.name = :username")
	public Optional<User> findUserByName(@Param("username") String username);
	@Query("Select Count (u.id) from user u Join rso r Where r.name = :name")
	public long countUsersInRSO(@Param("name") String name);
}
