package Rest;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import Wrapper.UserWrapper;

@RequestMapping(path = "/user")
public interface UserRest {
	
	
	@PostMapping(path = "/singup")
	public ResponseEntity<String> singUp(@RequestBody(required= true) Map<String, String> reqestMap);

	@PostMapping(path = "/login")
	public ResponseEntity<String> longi(@RequestBody(required = true )Map<String,String> requestMapp);
	
	
	@GetMapping(path = "/get")
	public ResponseEntity<List<UserWrapper>> getAllUser();
	
	
	@PostMapping (path =  "/update")
			public ResponseEntity<String> update(@RequestBody(required = true) Map<String, String>meqMap);
	
	@GetMapping(path = "/checkToken")
	ResponseEntity<String> checkToken();
	
	@PostMapping(path = "/changsdPassword")
	ResponseEntity<String>changsdPassword(@RequestBody Map<String,String>requestMap) ;
	
	@PostMapping(path = "\forgotPassword")
	ResponseEntity<String> forgotPassword(@RequestBody Map<String, String> requestMap);
	
	
	
}
