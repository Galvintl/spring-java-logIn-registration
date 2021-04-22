package com.galvintl.loginReg.Controllers;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.galvintl.loginReg.Models.User;
import com.galvintl.loginReg.Services.UserService;
import com.galvintl.loginReg.Validation.UserValidator;

@Controller
public class MainController{
	private final UserService userService;
	
	 private final UserValidator userValidator;
	
	public MainController(UserService userService, UserValidator userValidator) {
		this.userService = userService;
		this.userValidator = userValidator;
	}
	
	@RequestMapping("/registration")
		public String registrationForm(@ModelAttribute("user") User user) {//@ModelAttribute associates with the form:form in jsp file
		return "registrationPage.jsp";
	}
	
	@PostMapping("/registration")
	public String registerUser(@Valid @ModelAttribute("user") User user, BindingResult result, HttpSession session) {
		
		//this lets you see results on console 
//		System.out.println(user.getEmail());
//		System.out.println(this.userService.findByEmail(user.getEmail()));
		
		
		userValidator.validate(user, result);
		//if result has errors, return the registration page
		if(result.hasErrors()) {
			return "registrationPage.jsp";
		}
		else {
			//this will prevent duplicate emails
			
			//create a user with this information
			 User u = userService.registerUser(user);
		        session.setAttribute("userId", u.getId());
			//get user id of who just got created and store in Session
			return"redirect:/homePage";
		}
	}
		@GetMapping("/homePage")
		public String dashboard(Model model, HttpSession session) {
			//retrieve the user object from the db whose id matches the id stored in session
			Long id = (Long)session.getAttribute("userId");
			User loggedinuser = this.userService.findUserById(id);
			model.addAttribute("loggedinuser", loggedinuser);
			return"homePage.jsp";
		}
		
		@GetMapping("/logout")
		public String logout(HttpSession session) {
			session.invalidate();
			return "redirect:/registration";
		}
		
		//to collect information from a regular form use Request Param
		@PostMapping("/login")
		public String login(@RequestParam("email") String email, @RequestParam("password") String passsword, HttpSession session, RedirectAttributes redirectAttributes) {
			Boolean isLegit = this.userService.authenticateUser(email, passsword);
			if(isLegit) {
				//if the email password combo is correct, log them in using session and redirect them to dashboard
				//get the user with that email
				User user =this.userService.findByEmail(email);
				//put that users id in sessions
				session.setAttribute("userId", user.getId());
				return "redirect:/homePage";
			}
			//if log in is not successful, flash them a message
			redirectAttributes.addFlashAttribute("error", "Invalid login attempt");
			return"redirect:/registration";
		}
	}
	
	






