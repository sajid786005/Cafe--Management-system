package Dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

import Pojo.User;
import Wrapper.UserWrapper;
import jakarta.transaction.Transactional;

public interface UserDao extends JpaRepository  <User,Integer>{
	
	
	static User findByEmailId(@Param("email")String email) {
		// TODO Auto-generated method stub
		return null;
	}
	
	List<UserWrapper> getAllUser();
	
	List<String> getAllAdmin();
	
	@Transactional
	@Modifying
	Integer updateStatus (@Param("status") String status, @Param("id")Integer id);
	
	

}
