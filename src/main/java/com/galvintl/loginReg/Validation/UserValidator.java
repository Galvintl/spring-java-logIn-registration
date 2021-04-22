package com.galvintl.loginReg.Validation;


import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.galvintl.loginReg.Models.User;
import com.galvintl.loginReg.Services.UserService;



@Component
public class UserValidator implements Validator {
    private final UserService userService;
    //this injects Service so we can use it and the class can talk to service
    public UserValidator(UserService userService) {
    	this.userService = userService;
    }
    
    
    // 1 
    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }
    
    // 2  validate function
    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        
        if (!user.getPasswordConfirmation().equals(user.getPassword())) {
            // 3 these are the getters and setters
            errors.rejectValue("passwordConfirmation", "Match");
        }  
        //we can talk to service to see if any email matches from form matches an email in DB
          if (this.userService.findByEmail(user.getEmail().toLowerCase()) != null) {
        	  //create a new error message
        	  errors.rejectValue("email", "DupeEmail");
          }
        
    }
}


