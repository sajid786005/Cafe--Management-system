package Jwt;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import Dao.UserDao;
import Pojo.User;
import lombok.extern.slf4j.Slf4j;

//Solve log error

@Slf4j
@Service
public class CustomerUserDetailsService implements UserDetailsService {

    @Autowired
    UserDao userDao;

    private User userDetail;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        log.info("Inside loadUserByUsername: {}", username);
        userDetail = userDao.findByEmailId(username);
        if (userDetail != null) {
            return new org.springframework.security.core.userdetails.User(
                userDetail.getEmail(),
                userDetail.getPassword(),
                new ArrayList<>()
            );
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }

    public User getUserDetail() {
   
    	return userDetail;
        
    }

   
}
