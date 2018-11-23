package cop4710.termproject.dbms.comment;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentRepository extends JpaRepository<Comment, Long>
{
	@Query("Select c from comment c Join c.event Join c.user Where c.event.id = :id")
	public List<Comment> findCommentsByEventId(@Param("id") Long id);
}
