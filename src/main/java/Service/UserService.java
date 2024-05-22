package Service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import Wrapper.UserWrapper;

public interface UserService {
	
	ResponseEntity<String> signUp(Map<String, String>requestMap);
	
	ResponseEntity<String> logiin(Map<String, String>requestMap);

	ResponseEntity<List<UserWrapper>> getAllUser();
	
	ResponseEntity<String>update(Map<String,String> requestMp);

	ResponseEntity<String> checkToken();

	ResponseEntity<String> changsdPassword(Map<String, String> requestMap);

	ResponseEntity<String> forgotPassword(Map<String, String> requestMap);


}
