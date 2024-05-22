package ServiceImpl;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.hibernate.cache.spi.support.CacheUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;

import Constents.CafeConstants;
import Dao.UserDao;
import Jwt.CustomerUserDetailsService;
import Jwt.JwtFilter;
import Jwt.JwtUtil;
import Pojo.User;
import Service.UserService;
import Utile.CafeUtiles;
import Utile.EmailUtils;
import Wrapper.UserWrapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserServiceImpl implements UserService{

	private static final String anotherString = null;

	@Autowired
	UserDao userDao;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	JwtUtil  jetutil;
	@Autowired
	EmailUtils emailUtils;
	
	@Autowired
	JwtFilter jwtFilter;
	
	@Autowired
	CustomerUserDetailsService customerUserDetailsService;
	
	@Override
	public ResponseEntity<String> signUp(Map<String, String> requestMap) {
             log.info("Insid singnup {}", requestMap);
                if(validateSingUpMap(requestMap)) {
                	User user = userDao.findByEmailId(requestMap.get("email"));
                	if(Objects.isNull(user)){
                		userDao.save(getUserFromMap(requestMap));
                		return CafeUtiles.getResponseEntity("SuccessFully Registred",HttpStatus.OK);
                	}else {
                		return CafeUtiles.getResponseEntity("Email already exits.", HttpStatus.BAD_REQUEST);
                	}
                }
                else {
                	return CafeUtiles.getResponseEntity(CafeConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
                }
                
                return null;
	}

	private boolean validateSingUpMap(Map<String, String> requestMap) {
		if(requestMap.containsKey("name") &&  requestMap.containsKey("contactNumber")
				&& requestMap.containsKey("email") && requestMap.containsKey("password")){
			return true;
		}
		
		return false;
	}
	
	private User getUserFromMap(Map<String, String>reqestMap) {
		
		User user = new User();
		user.setName
	(reqestMap.get("name"));
		user.setContactNumber(reqestMap.get("contactNumber"));
		user.setEmail(reqestMap.get("password"));
		user.setPassword(reqestMap.get("password"));
		user.setStatus("false");
		user.setRole("user");
		return user;
	}

	@Override
	public ResponseEntity<String> logiin(Map<String, String> requestMap) {
	      log.info("Inside login");
	      try {
	    	  
	    	  Authentication auth = authenticationManager.authenticate(
	    			  new UsernamePasswordAuthenticationToken(requestMap.get("email"),requestMap.get("password") )
	    			  
	    			  );
	    	  
	    	  if(auth.isAuthenticated()) {
	    		  if(customerUserDetailsService.getUserDetail().getStatus().equalsIgnoreCase(anotherString )){
	    			  return new ResponseEntity<String>("{\"token\":\""+
	    		  JwtUtil.generateToken(customerUserDetailsService.getUserDetail().getEmail(),
	    				  customerUserDetailsService.getUserDetail().getRole()) +"\"}"),
	    					  HttpStatus.OK);
	    		  }
	    		  
	    		  else {
	    			  return new ResponseEntity<String>("\"messge\":\"" +"Wait for admin approval"+"\"}",
	    					  HttpStatus.BAD_REQUEST);
	    			  
	    		  }
	    	  }
	    	  
	      }catch (Exception ex) {
	    	  log.error("{}",ex);
	      }
	      return new ResponseEntity<String>("\"messge\":\"" +"Bad Credential"+"\"}",
	    		  HttpStatus.BAD_REQUEST);
		
	}

	@Override
	public ResponseEntity<List<UserWrapper>> getAllUser() {
		try {
			
			if (jwtFilter.isAdmin()) {
				return new ResponseEntity<>(userDao.getAllUser(),HttpStatus.OK);
				
			}else {
				return new ResponseEntity<>(new ArrayList<>(),HttpStatus.UNAUTHORIZED);
			}
			
		}catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return new  ResponseEntity<>( new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<String> update(Map<String, String> requestMap) {
	try {
		
		if(jwtFilter.isAdmin()) {
		Optional<User> optional  =	userDao.findById(Integer.parseInt(requestMap.get("id")));
			if(!optional.isEmpty()) {
				userDao.updateStatus(requestMap.get("status"),Integer.parseInt(requestMap.get("id")));
				return CafeUtiles.getResponseEntity("User Status Update SuccessFully",HttpStatus.OK);
			}
			else {
		CafeUtiles.getResponseEntity( "User id doesn`t not exist",HttpStatus.OK);
			}
		}else {
			
			return CafeUtiles.getResponseEntity(CafeConstants.UNAUTHORIZED_ACCESS,HttpStatus.UNAUTHORIZED);
		}
		
	}catch(Exception ex) {
		ex.printStackTrace();
	}
	  return CafeUtiles.getResponseEntity(CafeConstants.SOMETING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
	
	}
	
	private void sendMailtoAllAdmin(String staus,String user,List<String> allAdmin) {
		
		allAdmin.remove(jwtFilter.getCurrenterserUser());
		if(staus!=null && staus.equalsIgnoreCase(anotherString)) {
			EmailUtils.sendSimpleMessage(jwtFilter.getCurrenterserUser(),"Account Approved", text " User:=" "+user+"\n is approved by \n ADMIN:-"+jwtFilter.getCurrenterserUser(), allAdmin);
					
					EmailUtils.sendSimpleMessage(jwtFilter.getCurrenterserUser(),"Account Approved", text " User:=" "+user+"\n is disabled by \n ADMIN:-"+jwtFilter.getCurrenterserUser(), allAdmin);	
								
		}else {
			
		}
	}

	@Override
	public ResponseEntity<String> checkToken() {
		return CafeUtiles.getResponseEntity( "true", HttpStatus.OK);
	}

	@Override
	public ResponseEntity<String> changsdPassword(Map<String, String> requestMap) {
		try {
			User user = userObj.findByEmail(JwtFilter.getCurrenterserUser());
			if(!user.equals(null)) {
				if(userObj.getPassword().equals(requestMap.get("oldPassword"))) {
					userObj.setpassword(requestMap.get("newPassword"));
					userDao.save(userObj);
					return CafeUtiles.getResponseEntity("Password Update Successfully", HttpStatus.OK);
				}
				return CafeUtiles.getResponseEntity( "Incorrect Old Password", HttpStatus.BAD_REQUEST);
				
			}
			return CafeUtiles.getResponseEntity(CafeConstants.SOMETING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
					
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return CafeUtiles.getResponseEntity(CafeConstants.SOMETING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<String> forgotPassword(Map<String, String> requestMap) {
		try {
			User user = UserDao.findByEmail(requestMap.get("email"));
			if(!Objects.isNull(user) && !Strings.isNullOrEmpty(user.getEmail())) 
				emailUtils.forgotMail(user.getEmail(), "Credentials by Cafe Managment System", user.getPassword());
			return CafeUtiles.getResponseEntity("Check yor mail for Credentials", HttpStatus.OK);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return CafeUtiles.getResponseEntity(CafeConstants.SOMETING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
