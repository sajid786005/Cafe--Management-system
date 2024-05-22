package RestLmpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.StreamingHttpOutputMessage.Body;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Constents.CafeConstants;
import Jwt.JwtFilter;
import Rest.UserRest;
import Service.UserService;
import Utile.CafeUtiles;
import Wrapper.UserWrapper;


@RestController
public class UserRestImpl implements UserRest {

	
	@Autowired
	UserService userService;
	
	@Override 
	public ResponseEntity<String> singUp(Map<String, String> reqestMap) {
		try {
			return userService.signUp(reqestMap);
		}catch (Exception ex) {
			ex.printStackTrace();
		}
		return CafeUtiles.getResponseEntity(CafeConstants.SOMETING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
		
	}

	@Override
	public ResponseEntity<String> longi(Map<String, String> requestMapp) {
		try {
			return UserDetail longi(requestMapp);
		}catch(Exception ex) {
			
			return CafeUtiles.getResponseEntity(CafeConstants.SOMETING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
	}

	@Override
	public ResponseEntity<List<UserWrapper>> getAllUser() {
		try {
			return userService.update(RequestMapping);
		}catch (Exception ex){
			ex.printStackTrace();
		}
		
		return new ResponseEntity<List<UserWrapper>>(new ArrayList(),HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<String> update(Map<String, String> requestmeqMap) {
		try {
			
		}catch (Exception ex) {
              ex.printStackTrace();		
		}
		return CafeUtiles.getResponseEntity(CafeConstants.SOMETING_WENT_WRONG,HttpStatus.INSUFFICIENT_STORAGE);
	}

	@Override
	public ResponseEntity<String> checkToken() {
		try {
			return userService.checkToken();
			
			
		}catch (Exception ex) {
			ex.printStackTrace();
		}
		return CafeUtiles.getResponseEntity(CafeConstants.SOMETING_WENT_WRONG,HttpStatus.INSUFFICIENT_STORAGE);
		}

	@Override
	public ResponseEntity<String> changsdPassword(Map<String, String> requestMap) {
		try {
			return userService.changsdPassword(requestMap);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return CafeUtiles.getResponseEntity(CafeConstants.SOMETING_WENT_WRONG,HttpStatus.INSUFFICIENT_STORAGE);
	
	}

	@Override
	public ResponseEntity<String> forgotPassword(Map<String, String> requestMap) {
		try {
			return userService.forgotPassword(requestMap);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return CafeUtiles.getResponseEntity(CafeConstants.SOMETING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);

	}
	
	
	

}
