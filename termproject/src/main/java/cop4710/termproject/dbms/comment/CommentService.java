package cop4710.termproject.dbms.comment;

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
public class CommentService 
{
	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(CommentService.class);
	
	@Autowired
	private CommentRepository repository;
	
	public long insert(Comment comment)
	{
		repository.save(comment);
		repository.flush();
		return comment.getId();
	}
	
	public Optional<Comment> find(long id)
	{
		return repository.findById(id);
	}
	
	public List<Comment> findByEventId(long id)
	{
		return repository.findCommentsByEventId(id);
	}
	
	public List<Comment> findAll()
	{
		return repository.findAll();
	}
	
	public void delete(long id)
	{
		//log.info("Deleting " + id);
		repository.deleteById(id);
	}
	
	public long replace(long id, Comment comment)
	{
		//log.info("Starting delete of " + id);
		delete(id);
		//log.info("Inserting " + comment);
		return insert(comment);
	}
}
