package com.galvintl.loginReg.Services;


//import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.galvintl.loginReg.Models.User;
import com.galvintl.loginReg.Repostitories.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    
    public UserService(UserRepository userRepository) {   //dependency injection, brings in the repo depositories
        this.userRepository = userRepository;
    }
    
// register user and hash their password(generated a bcrypt encrypted password) static method can use out of box
    public User registerUser(User user) {
        String hashed = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        user.setPassword(hashed);//sets password as hashed pw
        user.setEmail(user.getEmail().toLowerCase());
        return userRepository.save(user);
    }

// find user by email
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
// find user by id
    public User findUserById(Long id) {
    	 return userRepository.findById(id).orElse(null);
    }
   
// authenticate user  this will work with the log in method in the Controller
    public boolean authenticateUser(String email, String password) {
        // first find the user by email
        User user = userRepository.findByEmail(email);
        // if we can't find it by email, return false
        if(user == null) {
            return false;
        } else {
        	// if the passwords match, return true, else, return false
            if(BCrypt.checkpw(password, user.getPassword())) {
                return true;
            } else {
                return false;
            }
        }
    }
}


