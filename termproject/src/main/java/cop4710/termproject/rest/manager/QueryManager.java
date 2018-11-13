package cop4710.termproject.rest.manager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cop4710.termproject.dbms.admin.Admin;
import cop4710.termproject.dbms.admin.AdminService;
import cop4710.termproject.dbms.comment.Comment;
import cop4710.termproject.dbms.comment.CommentService;
import cop4710.termproject.dbms.event.priv.PrivateEvent;
import cop4710.termproject.dbms.event.priv.PrivateEventService;
import cop4710.termproject.dbms.event.pub.PublicEvent;
import cop4710.termproject.dbms.event.pub.PublicEventService;
import cop4710.termproject.dbms.event.rso.RSOEvent;
import cop4710.termproject.dbms.event.rso.RSOEventService;
import cop4710.termproject.dbms.rso.RSOService;
import cop4710.termproject.dbms.superadmin.SuperAdmin;
import cop4710.termproject.dbms.superadmin.SuperAdminService;
import cop4710.termproject.dbms.user.User;
import cop4710.termproject.dbms.user.UserService;
import cop4710.termproject.rest.helpers.Event;
import cop4710.termproject.rest.helpers.EventComment;
import cop4710.termproject.rest.helpers.RSO;

@Service
public class QueryManager 
{
	@Autowired
	private AdminService admin;
	@Autowired
	private CommentService comment;
	@Autowired
	private PrivateEventService privateEvent;
	@Autowired
	private PublicEventService publicEvent;
	@Autowired
	private RSOEventService rsoEvent;
	@Autowired
	private RSOService rso;
	@Autowired
	private SuperAdminService superAdmin;
	@Autowired
	private UserService user;
	
	public QueryManager() {}

	public List<Event> getPublicList() 
	{
		List<PublicEvent> events = publicEvent.findAll();
		List<Event> results = new LinkedList<>();
		
		for (PublicEvent event: events)
		{
			results.add(new Event(event.getId(), event.getName(), event.getDesc(), event.getLocation(), "public", event.getTime()));
		}
		
		return results;
	}

	public List<Event> getList()
	{
		List<Event> results = getPublicList();
		List<PrivateEvent> pevents = privateEvent.findAll();
		List<RSOEvent> revents = rsoEvent.findAll();
		
		for (PrivateEvent event : pevents)
		{
			results.add(new Event(event.getId(), event.getName(), event.getDesc(), event.getLocation(), "private", event.getTime()));
		}
		
		for (RSOEvent event : revents)
		{
			results.add(new Event(event.getId(), event.getName(), event.getDesc(), event.getLocation(), event.getRso().getName(), event.getTime()));
		}
		
		return results;
	}

	public boolean userExists(String username) 
	{
		return user.userExists(username);
	}

	public void createUser(String username, String password) 
	{
		user.insert(new User(username, password));
	}

	public boolean validateUser(String username, String password) 
	{
		return user.validate(username, password);
	}

	public List<RSO> getUsersRSOs(String username, String password)
	{
		if (validateUser(username, password))
		{
			List<cop4710.termproject.dbms.rso.RSO> results = rso.findRSOByUsername(username);
			List<RSO> values = new LinkedList<>();
			
			for (cop4710.termproject.dbms.rso.RSO result : results)
			{
				values.add(new RSO(result.getId(), result.getAdmin().getName(), result.getName(), result.isActive()));
			}
			return values;
			
		} else
		{
			return null;
		}
	}

	public boolean updateUserRso(String username, Long id)
	{
		Optional<cop4710.termproject.dbms.rso.RSO> result = rso.find(id);
		if (result.isPresent())
		{
			user.addRSOToRecord(username, id, result.get());
			return true;
		} else
		{
			return false;
		}
	}

	public List<RSO> getRSOList(String username, String password) 
	{
		List<cop4710.termproject.dbms.rso.RSO> results = rso.findAll();
		List<RSO> values = new LinkedList<>();
		
		for (cop4710.termproject.dbms.rso.RSO result : results)
		{
			values.add(new RSO(result.getId(), result.getAdmin().getName(), result.getName(), result.isActive()));
		}
		return values;
	}

	public boolean createNewRSO(String username, String name, String rsopassword) 
	{
		Optional<cop4710.termproject.dbms.user.User> owner = user.find(username);
		Optional<cop4710.termproject.dbms.rso.RSO> result = rso.find(name); 
		if (owner.isPresent() && !result.isPresent())
		{
			cop4710.termproject.dbms.rso.RSO newClub = new cop4710.termproject.dbms.rso.RSO(name, rsopassword, owner.get());
			rso.insert(newClub);
			return true;
		} else
		{
			return false;
		}
	}

	public boolean RSOActivate(Long id, String rsopassword) 
	{
		Optional<cop4710.termproject.dbms.rso.RSO> result = rso.find(id);
		if (result.isPresent() && result.get().getPasswd().equals(rsopassword))
		{
			if (user.countUsersInRSO(result.get().getName()) > 4)
			{
				return true;
			} else
			{
				return false;
			}
		}else
		{
			return false;
		}
	}

	public void deleteUser(String username, String password)
	{
		Optional<cop4710.termproject.dbms.user.User> owner = user.find(username);
		if (owner.isPresent())
		{
			user.delete(owner.get().getId());
		}
	}

	public List<EventComment> getComments(Long id)
	{
		List<Comment> comments = comment.findByEventId(id);
		List<EventComment> results = new ArrayList<>();
		for (Comment c : comments)
		{
			results.add(new EventComment(c.getId(), c.getText(), c.getUser().getName(), c.getRating(), c.getTimestamp()));
		}
		return results;
	}

	public boolean makeComment(String username, Long id, String text, Double rating)
	{
		Optional<RSOEvent> event1 = rsoEvent.find(id);
		Optional<PrivateEvent> event2 = privateEvent.find(id);
		Optional<PublicEvent> event3 = publicEvent.find(id);
		cop4710.termproject.dbms.event.Event event;
		
		if(event1.isPresent())
		{
			event = event1.get();
		} else if (event2.isPresent())
		{
			event = event2.get();
		} else if (event3.isPresent())
		{
			event = event3.get();
		} else
		{
			return false;
		}
		Calendar now = Calendar.getInstance();
		Optional<cop4710.termproject.dbms.user.User> owner = user.find(username);
		if (!owner.isPresent())
		{
			return false;
		}
		Comment c = new Comment(text, rating, now.getTimeInMillis(), owner.get(), event);
		comment.insert(c);
		
		return true;
	}

	public boolean changeComment(String username, Long id, String text, Double rating) 
	{
		Optional<Comment> change = comment.find(id);
		if (!change.isPresent())
		{
			return false;
		}
		Comment finished = change.get();
		if (!finished.getUser().getName().equals(username))
		{
			return false;
		}
		finished.setText(text);
		finished.setRating(rating);
		comment.insert(finished);
		return true;
	}

	public boolean createAdmin(String username, Long id, String adminpassword) 
	{
		Optional<cop4710.termproject.dbms.rso.RSO> result = rso.find(id);
		if (result.isPresent())
		{
			if (user.countUsersInRSO(result.get().getName()) > 4)
			{
				Admin a = new Admin(username, adminpassword, result.get());
				admin.insert(a);
				cop4710.termproject.dbms.rso.RSO update = result.get();
				update.setActive(true);
				rso.insert(update);
				return true;
			} else
			{
				return false;
			}
		}else
		{
			return false;
		}
	}

	public boolean validateAdmin(String username, String password) 
	{
		if (admin.validate(username, password))
		{
			return true;
		} else
		{
			return false;
		}
	}

	public boolean createEvent(String username, String type, Long time, String location, String name, String desc)
	{
		Optional<Admin> me = admin.find(username);
		if (!me.isPresent() || publicEvent.collision(location, time) || privateEvent.collision(location, time) || rsoEvent.collision(location, time))
		{
			return false;
		} else if (type.equals("private"))
		{
			PrivateEvent event = new PrivateEvent(name, desc, location, time, me.get());
			privateEvent.insert(event);
			return true;
		} else if (type.equals("rso"))
		{
			RSOEvent event = new RSOEvent(name, desc, location, time, me.get().getRso());
			rsoEvent.insert(event);
			return true;
		}
		return false;
	}

	public boolean deleteAdmin(String username, String password)
	{
		Optional<Admin> me = admin.find(username);
		if (!me.isPresent())
		{
			return false;
		} else
		{
			cop4710.termproject.dbms.rso.RSO club = me.get().getRso();
			club.setActive(false);
			rso.insert(club);
			admin.delete(me.get().getId());
			return true;
		}
	}

	public boolean validateSuperAdmin(String username, String password) 
	{
		if (superAdmin.validate(username, password))
		{
			return true;
		} else
		{
			return false;
		}
	}

	public boolean createPublicEvent(String username, Long time, String location, String name, String desc) 
	{
		Optional<SuperAdmin> me = superAdmin.find(username);
		if (!me.isPresent() || publicEvent.collision(location, time) || privateEvent.collision(location, time) || rsoEvent.collision(location, time))
		{
			return false;
		} else 
		{
			PublicEvent event = new PublicEvent(name, desc, location, time, me.get());
			publicEvent.insert(event);
			return true;
		}
	}

	public boolean approved(Long id, boolean approve) 
	{
		Optional<PrivateEvent> event = privateEvent.find(id);
		if (event.isPresent())
		{
			PrivateEvent body = event.get();
			if (approve)
			{
				body.setApproved(true);
				privateEvent.insert(body);
			} else
			{
				privateEvent.delete(body.getId());
			}
			return true;
		} else
		{
			return false;
		}
	}

	public List<Event> getUnapproved(String username, String password) 
	{
		if (validateSuperAdmin(username, password))
		{
			List<PrivateEvent> events = privateEvent.findUnapproved();
			List<Event> results = new LinkedList<>();
			for (PrivateEvent event : events)
			{
				results.add(new Event(event.getId(), event.getName(), event.getDesc(), event.getLocation(), "private", event.getTime()));
			}
			
			return results;
		} else
		{
			return Collections.emptyList();
		}
	}
}
