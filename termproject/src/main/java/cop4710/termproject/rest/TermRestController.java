package cop4710.termproject.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
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
	public List<Event> publicEventList()
	{
		return query.getPublicList();
	}
	
	@RequestMapping(value = "/privateeventlist", method = RequestMethod.GET)
	public List<Event> privateEventList(@Param("user") String username, @Param("password") String password)
	{
		if (query.validateUser(username, password))
		{
			return query.getList();
		} else
		{
			return Collections.emptyList();
		}
	}
	
	@RequestMapping(value = "/userregistration", method = RequestMethod.GET)
	public ResponseEntity<?> userRegistration(@Param("user") String username, @Param("password") String password) throws URISyntaxException
	{
		String uribase = "http://localhost:8080/termproject/userregistration";
		URI location = new URI(uribase);
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setLocation(location);
		if (query.userExists(username))
		{
			responseHeaders.set("User already exists", "409"); //Conflict
		}else
		{
			query.createUser(username, password);
			responseHeaders.set("Success", "200"); // Correctly inserted
		}
		return new ResponseEntity<String>("User Added", responseHeaders, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/userlogin", method = RequestMethod.GET)
	public ResponseEntity<?> userLogin(@Param("user") String username, @Param("password") String password) throws URISyntaxException
	{
		String uribase = "http://localhost:8080/termproject/userlogin";
		URI location = new URI(uribase);
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setLocation(location);
		if (query.validateUser(username, password))
		{
			responseHeaders.set("Validated", "200"); // Validated
		} else
		{
			responseHeaders.set("Unauthorized Access", "401"); // unauthorized
		}
		return new ResponseEntity<String>("User Logged in.", responseHeaders, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/userrso", method = RequestMethod.GET)
	public List<RSO> userRSO(@Param("user") String username, @Param("password") String password)
	{
		return query.getUsersRSOs(username, password);
	}
	
	@RequestMapping(value = "/userupdaterso", method = RequestMethod.GET)
	public ResponseEntity<?> userUpdateRSO(@Param("user") String username, @Param("password") String password, @Param("rso") Long id) throws URISyntaxException
	{
		String uribase = "http://localhost:8080/termproject/userlogin";
		URI location = new URI(uribase);
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setLocation(location);
		if (query.validateUser(username, password))
		{
			if (query.updateUserRso(username, id))
			{
				responseHeaders.set("Success", "200"); // 404 not found
			} else
			{
				responseHeaders.set("RSO not found", "404"); // 404 not found
			}
		}else
		{
			responseHeaders.set("Not Authorized", "409"); // not authorized
		}
		return new ResponseEntity<String>("User Added", responseHeaders, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/listrso", method = RequestMethod.GET)
	public List<RSO> listRSO(@Param("user") String username, @Param("password") String password)
	{
		return query.getRSOList(username, password);
	}
	
	@RequestMapping(value = "/createrso", method = RequestMethod.GET)
	public ResponseEntity<?> createRSO(@Param("user") String username, 
									   @Param("password") String password, 
									   @Param("name") String name,
									   @Param("rsopassword") String rsopassword) throws URISyntaxException
	{
		String uribase = "http://localhost:8080/termproject/userregistration";
		URI location = new URI(uribase);
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setLocation(location);
		if (query.validateUser(username, password))
		{
			if (query.createNewRSO(username, name, rsopassword))
			{
				responseHeaders.set("Success", "200"); //Success
			} else
			{
				responseHeaders.set("RSO already exists", "409"); //Conflict
			}
		} else
		{
			responseHeaders.set("Not Authorized", "401"); // Not Authorized
		}
		return new ResponseEntity<String>("User Added", responseHeaders, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/activaterso", method = RequestMethod.GET)
	public ResponseEntity<?> activateRSO(@Param("user") String username, 
									     @Param("password") String password, 
									     @Param("rso") Long id,
									     @Param("rsopassword") String rsopassword) throws URISyntaxException
	{
		String uribase = "http://localhost:8080/termproject/userlogin";
		URI location = new URI(uribase);
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setLocation(location);
		if(query.validateUser(username, password) && query.RSOActivate(id, rsopassword))
		{
			responseHeaders.set("Success", "200"); // Success
		} else
		{
			if (query.validateUser(username, password))
			{
				responseHeaders.set("Not Authorized", "401"); // Not Authorized
			} else
			{
				responseHeaders.set("Not enough members", "401"); // Not enough members
			}
		}
		
		return new ResponseEntity<String>("User Added", responseHeaders, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/deleteuser", method = RequestMethod.GET)
	public ResponseEntity<?> deleteUser(@Param("user") String username, @Param("password") String password) throws URISyntaxException
	{
		String uribase = "http://localhost:8080/termproject/userlogin";
		URI location = new URI(uribase);
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setLocation(location);
		if (query.validateUser(username, password))
		{
			query.deleteUser(username, password);
			responseHeaders.set("Success", "200"); // 404 not found/401 unauthorized
		} else
		{
			responseHeaders.set("Not Authorized", "401"); //Not Authorized
		}
		
		return new ResponseEntity<String>("User Added", responseHeaders, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/getcomments", method = RequestMethod.GET)
	public List<EventComment> getComments(@Param("user") String username, @Param("password") String password, @Param("event") Long id)
	{
		if (query.validateUser(username, password))
		{
			return query.getComments(id);
		} else
		{
			return Collections.emptyList();
		}
	}
	
	@RequestMapping(value = "/comment", method = RequestMethod.GET)
	public ResponseEntity<?> userComment(@Param("user") String username, 
										 @Param("password") String password, 
										 @Param("event") Long id,
										 @Param("text") String text,
										 @Param("rating") Double rating) throws URISyntaxException
	{
		String uribase = "http://localhost:8080/termproject/userlogin";
		URI location = new URI(uribase);
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setLocation(location);
		if (query.validateUser(username, password))
		{
			if (query.makeComment(username, id, text, rating))
			{
				responseHeaders.set("Success", "200"); // Success
			} else
			{
				responseHeaders.set("Event not Found", "404"); // Event not Found
			}
		} else
		{
			responseHeaders.set("Not Authorized", "401"); // Not Authorized
		}
		return new ResponseEntity<String>("User Added", responseHeaders, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/changecomment", method = RequestMethod.GET)
	public ResponseEntity<?> changeComment(@Param("user") String username, 
										   @Param("password") String password, 
										   @Param("comment") Long id,
										   @Param("text") String text,
										   @Param("rating") Double rating) throws URISyntaxException
	{
		String uribase = "http://localhost:8080/termproject/userlogin";
		URI location = new URI(uribase);
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setLocation(location);
		if (query.validateUser(username, password))
		{
			if (query.changeComment(username, id, text, rating))
			{
				responseHeaders.set("Success", "200"); // Success
			} else
			{
				responseHeaders.set("Comment not Found", "404"); // Comment not Found
			}
		} else
		{
			responseHeaders.set("Not Authorized", "401"); // Not Authorized
		}
		return new ResponseEntity<String>("User Added", responseHeaders, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/createadmin", method = RequestMethod.GET)
	public ResponseEntity<?> createAdmin(@Param("user") String username, 
			   							 @Param("password") String password, 
			   							 @Param("rso") Long id,
			   							 @Param("adminpassword") String adminpassword) throws URISyntaxException
	{
		String uribase = "http://localhost:8080/termproject/userlogin";
		URI location = new URI(uribase);
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setLocation(location);
		if (query.validateUser(username, password))
		{
			if (query.createAdmin(username, id, adminpassword))
			{
				responseHeaders.set("Success", "200"); // Success
			} else
			{
				responseHeaders.set("RSO not Found", "404"); // RSO Not Fount
			}
		} else
		{
			responseHeaders.set("Not Authorized", "401"); // Not Authorized
		}
		return new ResponseEntity<String>("User Added", responseHeaders, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/adminlogin", method = RequestMethod.GET)
	public ResponseEntity<?> loginAdmin(@Param("user") String username, @Param("password") String password) throws URISyntaxException
	{
		String uribase = "http://localhost:8080/termproject/userlogin";
		URI location = new URI(uribase);
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setLocation(location);
		if (query.validateAdmin(username, password))
		{
			responseHeaders.set("Success", "200"); // Success
		} else
		{
			responseHeaders.set("Not Authorized", "401"); // Not Authorized
		}
		return new ResponseEntity<String>("User Added", responseHeaders, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/createevent", method = RequestMethod.GET)
	public ResponseEntity<?> createEvent(@Param("user") String username, 
										 @Param("password") String password,
										 @Param("type") String type,
										 @Param("time") Long time,
										 @Param("location") String location,
										 @Param("desc") String desc,
										 @Param("name") String name) throws URISyntaxException
	{
		String uribase = "http://localhost:8080/termproject/userlogin";
		URI location1 = new URI(uribase);
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setLocation(location1);
		if (query.validateAdmin(username, password))
		{
			if (query.createEvent(username, type, time, location, name, desc))
			{
				responseHeaders.set("Success", "200"); // 401 unauthorized/ 409 conflict
			} else
			{
				responseHeaders.set("There was a Conflict with another Event", "409"); //Conflict with another Event
			}
		} else
		{
			responseHeaders.set("Not Authorized", "401"); //Not Authorized
		}
		return new ResponseEntity<String>("User Added", responseHeaders, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/closerso", method = RequestMethod.GET)
	public ResponseEntity<?> closeRSO(@Param("user") String username, @Param("password") String password) throws URISyntaxException
	{
		String uribase = "http://localhost:8080/termproject/userlogin";
		URI location = new URI(uribase);
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setLocation(location);
		if (query.validateAdmin(username, password))
		{
			if (query.deleteAdmin(username, password))
			{
				responseHeaders.set("Success", "200"); // Success
			} else
			{
				responseHeaders.set("Admin not Found", "404"); // Not Found
			}
		} else
		{
			responseHeaders.set("Not Authorized", "401"); // Not Authorized
		}
		return new ResponseEntity<String>("User Added", responseHeaders, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/superlogin", method = RequestMethod.GET)
	public ResponseEntity<?> superLogin(@Param("username") String username, @Param("password") String password) throws URISyntaxException
	{
		log.info("SuperLogin request User: " + username + " Password: " + password);
		String uribase = "http://localhost:8080/termproject/superlogin";
		URI location = new URI(uribase);
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setLocation(location);
		ResponseEntity<String> response;
		if (query.validateSuperAdmin(username, password))
		{
			responseHeaders.set("Success", "200"); // Success
			response = new ResponseEntity<String>("Logged in", responseHeaders, HttpStatus.CREATED);
		} else
		{
			responseHeaders.set("Not Authorized", "401"); // Not Authorized
			response = new ResponseEntity<String>("Not Authorized", responseHeaders, HttpStatus.CREATED);
		}
		return response;
	}
	
	@RequestMapping(value = "/createpublic", method = RequestMethod.GET)
	public ResponseEntity<?> createPublic(@Param("user") String username, 
										  @Param("password") String password,
										  @Param("time") Long time,
										  @Param("location") String location,
										  @Param("desc") String desc,
										  @Param("name") String name) throws URISyntaxException
	{
		String uribase = "http://localhost:8080/termproject/userlogin";
		URI location1 = new URI(uribase);
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setLocation(location1);
		if (query.validateSuperAdmin(username, password))
		{
			if (query.createPublicEvent(username, time, location, name, desc))
			{
				responseHeaders.set("Success", "200"); // 401 unauthorized/ 409 conflict
			} else
			{
				responseHeaders.set("There was a Conflict with another Event", "409"); //Conflict with another Event
			}
		} else
		{
			responseHeaders.set("Not Authorized", "401"); //Not Authorized
		}
		return new ResponseEntity<String>("User Added", responseHeaders, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/approve", method = RequestMethod.GET)
	public ResponseEntity<?> approve(@Param("user") String username, 
			  						 @Param("password") String password,
			  						 @Param("event") Long id,
			  						 @Param("approval") boolean approve) throws URISyntaxException
	{
		String uribase = "http://localhost:8080/termproject/userlogin";
		URI location1 = new URI(uribase);
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setLocation(location1);
		if (query.validateSuperAdmin(username, password))
		{
			if (query.approved(id, approve))
			{
				responseHeaders.set("Success", "200"); // Success
			} else
			{
				responseHeaders.set("Could not find event to approve", "404"); // Event not found
			}
		} else
		{
			responseHeaders.set("Not Authorized", "401"); // Not Authorized
		}
		return new ResponseEntity<String>("User Added", responseHeaders, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/unapproved", method = RequestMethod.GET)
	public List<Event> unapproved(@Param("user") String username, @Param("password") String password)
	{
		return query.getUnapproved(username, password);
	}
	
}
