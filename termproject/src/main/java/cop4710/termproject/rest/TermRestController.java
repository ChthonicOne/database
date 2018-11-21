package cop4710.termproject.rest;

import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import cop4710.termproject.rest.helpers.Event;
import cop4710.termproject.rest.helpers.EventComment;
import cop4710.termproject.rest.helpers.RSO;
import cop4710.termproject.rest.manager.QueryManager;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/termproject")
public class TermRestController
{
	@Autowired
	private QueryManager query;
	
	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(TermRestController.class);
	
	@RequestMapping(value = "/publiceventlist", method = RequestMethod.GET)
	public ResponseEntity<?> publicEventList()
	{
		log.info("Public Query Request");
		return ResponseEntity.ok(query.getPublicList());
	}
	
	@RequestMapping(value = "/privateeventlist", method = RequestMethod.GET)
	public ResponseEntity<?> privateEventList(@Param("username") String username, @Param("password") String password)
	{
		if (query.validateUser(username, password))
		{
			log.info(username + " viewed their private event list");
			return ResponseEntity.ok(query.getList());
		} else
		{
			log.info("Unauthorized access to private event list by username:" + username + " with password: " + password);
			List<Event> response = new LinkedList<>();
			response.add(new Event(-1L,
								   "Forbidden",
								   "You are not authorized to view this page", 
								   "http://localhost:8080/termproject/privateeventlist", 
								   "error", 
								   0L));
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
		}
	}
	
	@RequestMapping(value = "/userregistration", method = RequestMethod.GET)
	public ResponseEntity<?> userRegistration(@Param("username") String username, @Param("password") String password) throws URISyntaxException
	{
		if (query.userExists(username))
		{
			log.info("User " + username + " already exists");
			return ResponseEntity.status(HttpStatus.CONFLICT).body("User already Exists");
		}else
		{
			log.info("User " + username + " added.");
			query.createUser(username, password);
			return ResponseEntity.ok("User Registered");
		}
	}
	
	@RequestMapping(value = "/userlogin", method = RequestMethod.GET)
	public ResponseEntity<?> userLogin(@Param("username") String username, @Param("password") String password) throws URISyntaxException
	{
		if (query.validateUser(username, password))
		{
			log.info(username + " logged in.");
			return ResponseEntity.ok("User Validated");
		} else
		{
			log.info("Invalid login to " + username + " with password " + password);
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unauthorized Access");
		}
	}
	
	@RequestMapping(value = "/userrso", method = RequestMethod.GET)
	public ResponseEntity<?> userRSO(@Param("username") String username, @Param("password") String password)
	{
		if (query.validateUser(username, password))
		{
			log.info(username + " aquires their rso list");
			return ResponseEntity.ok(query.getUsersRSOs(username, password));
		}else
		{
			log.info("Unauthorized access to " + username + "'s rso list with password " + password);
			List<RSO> response = new LinkedList<>();
			response.add(new RSO(-1L,
								 "Forbidden",
								 "You are not authorized to view this page", 
								 false));
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
		}
	}
	
	@RequestMapping(value = "/userupdaterso", method = RequestMethod.GET)
	public ResponseEntity<?> userUpdateRSO(@Param("username") String username, @Param("password") String password, @Param("rso") Long id) throws URISyntaxException
	{
		if (query.validateUser(username, password))
		{
			if (query.updateUserRso(username, id))
			{
				log.info(username + " added  RSO #" + id + " to their RSOs");
				return ResponseEntity.ok("Updated RSO List with RSO #" + id);
			} else
			{
				log.info(username + " attempted to add RSO #" + id + " to their RSOs but it was not found");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("RSO #" + id + " not found.");
			}
		}else
		{
			log.info("Unauthorized access to " + username + "'s RSO list with password " + password);
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unauthorized Access");
		}
	}
	
	@RequestMapping(value = "/listrso", method = RequestMethod.GET)
	public ResponseEntity<?> listRSO(@Param("username") String username, @Param("password") String password)
	{
		if (query.validateUser(username, password))
		{
			log.info(username + "Retrieved list of RSOs");
			return ResponseEntity.ok(query.getRSOList(username, password));
		}else
		{
			log.info("Unauthorized access to RSO list by username " + username + " with password " + password);
			List<RSO> response = new LinkedList<>();
			response.add(new RSO(-1L,
								 "Forbidden",
								 "You are not authorized to view this page", 
								 false));
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
		}
	}
	
	@RequestMapping(value = "/createrso", method = RequestMethod.GET)
	public ResponseEntity<?> createRSO(@Param("username") String username, 
									   @Param("password") String password, 
									   @Param("name") String name,
									   @Param("rsopassword") String rsopassword) throws URISyntaxException
	{
		if (query.validateUser(username, password))
		{
			if (query.createNewRSO(username, name, rsopassword))
			{
				log.info(username + " created a new RSO named " + name + " with password " + rsopassword);
				return ResponseEntity.ok("RSO Created.");
			} else
			{
				log.info(username + " attempted to create a new RSO but it already existed");
				return ResponseEntity.status(HttpStatus.CONFLICT).body("RSO already exists.");
			}
		} else
		{
			log.info("Unauthorized access to create a new RSO by username " + username + " with password " + password);
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unauthorized Access");
		}
	}
	
	@RequestMapping(value = "/activaterso", method = RequestMethod.GET)
	public ResponseEntity<?> activateRSO(@Param("username") String username, 
									     @Param("password") String password, 
									     @Param("rso") Long id,
									     @Param("rsopassword") String rsopassword) throws URISyntaxException
	{
		if(query.validateUser(username, password) && query.RSOActivate(id, rsopassword))
		{
			log.info(username + " activated RSO #" + id);
			return ResponseEntity.ok("RSO Created");
		} else
		{
			if (query.validateUser(username, password))
			{
				log.info("Unauthorized access to activate RSO #" + id + " by username " + username + " with password " + password);
				return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unauthorized Access");
			} else
			{
				log.info(username + " tried to activate RSO #" + id + " but did not have enough members");
				return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Not Enough Members");
			}
		}
	}
	
	@RequestMapping(value = "/deleteuser", method = RequestMethod.GET)
	public ResponseEntity<?> deleteUser(@Param("username") String username, @Param("password") String password) throws URISyntaxException
	{
		if (query.validateUser(username, password))
		{
			query.deleteUser(username, password);
			log.info(username + " removed from database");
			return ResponseEntity.ok("User Deleted.");
		} else
		{
			log.info("Unauthorized access to delete username " + username + " with password " + password);
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unauthorized Access");
		}
	}
	
	@RequestMapping(value = "/getcomments", method = RequestMethod.GET)
	public ResponseEntity<?> getComments(@Param("username") String username, @Param("password") String password, @Param("event") Long id)
	{
		if (query.validateUser(username, password))
		{
			log.info(username + " got comments for event #" + id);
			return ResponseEntity.ok(query.getComments(id));
		} else
		{
			log.info("Unauthorized access to delete username " + username + " with password " + password);
			List<EventComment> response = new LinkedList<>();
			response.add(new EventComment(-1L,
								 		  "Forbidden",
								 		  "You are not authorized to view this page",
								 		  0.0,
								 		  0L));
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
		}
	}
	
	@RequestMapping(value = "/comment", method = RequestMethod.GET)
	public ResponseEntity<?> userComment(@Param("username") String username, 
										 @Param("password") String password, 
										 @Param("event") Long id,
										 @Param("text") String text,
										 @Param("rating") Double rating) throws URISyntaxException
	{
		if (query.validateUser(username, password))
		{
			if (query.makeComment(username, id, text, rating))
			{
				log.info(username + " added comment to event #" + id);
				return ResponseEntity.ok("Comment added");
			} else
			{
				log.info(username + " tried to add comment to event #" + id + " but event was not found");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Event not Found.");
			}
		} else
		{
			log.info("Unauthorized access to add comment to event #" + id + " with username " + username + " with password " + password);
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unauthorized Access");
		}
	}
	
	@RequestMapping(value = "/changecomment", method = RequestMethod.GET)
	public ResponseEntity<?> changeComment(@Param("username") String username, 
										   @Param("password") String password, 
										   @Param("comment") Long id,
										   @Param("text") String text,
										   @Param("rating") Double rating) throws URISyntaxException
	{
		if (query.validateUser(username, password))
		{
			if (query.changeComment(username, id, text, rating))
			{
				log.info(username + " changed their comment #" + id);
				return ResponseEntity.ok("Comment Changed");
			} else
			{
				log.info(username + " tried to change comment #" + id + " but the comment was not found");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comment not Found.");
			}
		} else
		{
			log.info("Unauthorized access to change comment #" + id + " with username " + username + " with password " + password);
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unauthorized Access");
		}
	}
	
	@RequestMapping(value = "/createadmin", method = RequestMethod.GET)
	public ResponseEntity<?> createAdmin(@Param("username") String username, 
			   							 @Param("password") String password, 
			   							 @Param("rso") Long id,
			   							 @Param("adminpassword") String adminpassword) throws URISyntaxException
	{
		if (query.validateUser(username, password))
		{
			int result = query.createAdmin(username,  id,  adminpassword);
			if (result == 0)
			{
				log.info(username + " created a new admin account for RSO #" + id + " with password " + adminpassword);
				return ResponseEntity.ok("Admin Account Created");
			} else if (result == 3)
			{
				log.info(username + " tried to created a new admin account for RSO #" + id + " but the RSO was not found");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("RSO #" + id + " not found.");
			} else if (result == 2)
			{
				log.info(username + " tried to created a new admin account for RSO #" + id + " but the RSO did not have enough students");
				return ResponseEntity.status(HttpStatus.CONFLICT).body("RSO #" + id + " did not have enough users");
			} else
			{
				log.info(username + " tried to created a new admin account for RSO #" + id + " but the RSO already had an admin");
				return ResponseEntity.status(HttpStatus.CONFLICT).body("RSO #" + id + " has an admin already");
			}
		} else
		{
			log.info("Unauthorized access to create admin with username " + username + " with password " + password);
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unauthorized Access");
		}
	}
	
	@RequestMapping(value = "/adminlogin", method = RequestMethod.GET)
	public ResponseEntity<?> loginAdmin(@Param("username") String username, @Param("password") String password) throws URISyntaxException
	{
		if (query.validateAdmin(username, password))
		{
			log.info(username + " logged into admin account");
			return ResponseEntity.ok("Admin Logged In.");
		} else
		{
			log.info("Unauthorized access to admin login with username " + username + " with password " + password);
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unauthorized Access");
		}
	}
	
	@RequestMapping(value = "/createevent", method = RequestMethod.GET)
	public ResponseEntity<?> createEvent(@Param("username") String username, 
										 @Param("password") String password,
										 @Param("type") String type,
										 @Param("time") Long time,
										 @Param("location") String location,
										 @Param("desc") String desc,
										 @Param("name") String name) throws URISyntaxException
	{
		if (query.validateAdmin(username, password))
		{
			if (query.createEvent(username, type, time, location, name, desc))
			{
				log.info(username + " created an event of type " + type);
				return ResponseEntity.ok("Event created.");
			} else
			{
				log.info(username + " tried to create an event but it conflicted with another event");
				return ResponseEntity.status(HttpStatus.CONFLICT).body("There was a conflict with another event.");
			}
		} else
		{
			log.info("Unauthorized access to create event with username " + username + " with password " + password);
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unauthorized Access");
		}
	}
	
	@RequestMapping(value = "/closerso", method = RequestMethod.GET)
	public ResponseEntity<?> closeRSO(@Param("username") String username, @Param("password") String password) throws URISyntaxException
	{
		if (query.validateAdmin(username, password))
		{
			if (query.deleteAdmin(username, password))
			{
				log.info(username + " closed his RSO and deleted his account");
				return ResponseEntity.ok("RSO Closed and admin account deleted");
			} else
			{
				log.info(username + " attempted to close his RSO account, but it was not found?");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Could not find your account, did it ever really exist?");
			}
		} else
		{
			log.info("Unauthorized access to close RSO with username " + username + " with password " + password);
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unauthorized Access");
		}
	}
	
	@RequestMapping(value = "/superlogin", method = RequestMethod.GET)
	public ResponseEntity<?> superLogin(@Param("username") String username, @Param("password") String password) throws URISyntaxException
	{
		if (query.validateSuperAdmin(username, password))
		{
			log.info(username + " logged into super admin account.");
			return ResponseEntity.ok("Super Admin Logged In.");
		} else
		{
			log.info("Unauthorized access to super admin login with username " + username + " with password " + password);
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unauthorized Access");
		}
	}
	
	@RequestMapping(value = "/createpublic", method = RequestMethod.GET)
	public ResponseEntity<?> createPublic(@Param("username") String username, 
										  @Param("password") String password,
										  @Param("time") Long time,
										  @Param("location") String location,
										  @Param("desc") String desc,
										  @Param("name") String name) throws URISyntaxException
	{
		if (query.validateSuperAdmin(username, password))
		{
			if (query.createPublicEvent(username, time, location, name, desc))
			{
				log.info(username + " created a public event");
				return ResponseEntity.ok("Event created.");
			} else
			{
				log.info(username + " tried to create a public event but it conflicted with another event");
				return ResponseEntity.status(HttpStatus.CONFLICT).body("There was a conflict with another event.");
			}
		} else
		{
			log.info("Unauthorized access to create public event with username " + username + " with password " + password);
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unauthorized Access");
		}
	}
	
	@RequestMapping(value = "/approve", method = RequestMethod.GET)
	public ResponseEntity<?> approve(@Param("username") String username, 
			  						 @Param("password") String password,
			  						 @Param("event") Long id,
			  						 @Param("approval") boolean approve) throws URISyntaxException
	{
		if (query.validateSuperAdmin(username, password))
		{
			if (query.approved(id, approve))
			{
				log.info(username + " approved private event #" + id);
				return ResponseEntity.ok("Event created.");
			} else
			{
				log.info(username + " tried to approve private event #" + id + " but it could not be found");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Could not find event #" + id +".");
			}
		} else
		{
			log.info("Unauthorized access to approve private event with username " + username + " with password " + password);
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unauthorized Access");
		}
	}
	
	@RequestMapping(value = "/unapproved", method = RequestMethod.GET)
	public ResponseEntity<?> unapproved(@Param("username") String username, @Param("password") String password)
	{
		if (query.validateSuperAdmin(username, password))
		{
			log.info(username + " viewed their private event list");
			return ResponseEntity.ok(query.getUnapproved(username, password));
		} else
		{
			log.info("Unauthorized access to private event list by username:" + username + " with password: " + password);
			List<Event> response = new LinkedList<>();
			response.add(new Event(-1L,
								   "Forbidden",
								   "You are not authorized to view this page", 
								   "http://localhost:8080/termproject/privateeventlist", 
								   "error", 
								   0L));
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
		}
	}
	
}
