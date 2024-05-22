package Wrapper;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserWrapper {

	
	
	
	private Integer id;
	
	private String name;
	
	
	private String email;
	
	private String ContactNumber;
	
	
	private  String status;


	public UserWrapper(Integer id, String name, String email, String contactNumber, String status) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		ContactNumber = contactNumber;
		this.status = status;
	}
	
	
	
}
