package Utile;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class CafeUtiles {
	
	private CafeUtiles() {
		
	}
	
	public static ResponseEntity<String> getResponseEntity( String responseMessage, HttpStatus httpStatus){
		return new ResponseEntity<String>( "{\"messag\":\""+ responseMessage +"\"}", httpStatus);

	}

}
