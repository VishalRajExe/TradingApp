package com.vishal.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vishal.config.JwtProvider;
import com.vishal.model.User;
import com.vishal.repository.UserRepository;
import com.vishal.response.AuthResponse;
import com.vishal.services.CustomeUserDetailsService;
import org.springframework.security.authentication.BadCredentialsException;
@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CustomeUserDetailsService customUserDetails;
	
	@PostMapping("/signup")
	public ResponseEntity<AuthResponse> register(@RequestBody User  user) throws Exception {
		
		User isEmailExist = userRepository.findByEmail(user.getEmail());		

		if(isEmailExist != null){

		    throw new Exception("email is already used with another account");
		}

		User newUser = new User();		
		
		newUser.setEmail(user.getEmail());
		newUser.setPassword(user.getPassword());
		newUser.setEmail(user.getEmail());
		newUser.setFullName(user.getFullName());
		
		User savedUser = userRepository.save(newUser);
		
		 Authentication auth =
                 new UsernamePasswordAuthenticationToken(
                         user.getEmail(),
                         user.getPassword()
                 );
		 
		 SecurityContextHolder.getContext().setAuthentication(auth);
		 
		 String jwt = JwtProvider.generateToken(auth);
		 AuthResponse res = new AuthResponse();

		 res.setJwt(jwt);

		 res.setStatus(true);

		 res.setMessage("register success");
		return new ResponseEntity<>(res, HttpStatus.CREATED);
		
	}
	@PostMapping("/signin")
	public ResponseEntity<AuthResponse> login(@RequestBody User  user) throws Exception {
		
		
		String username = user.getEmail();
		String password = user.getPassword();
		
		Authentication auth = authenticate(username, password);

		 
		 SecurityContextHolder.getContext().setAuthentication(auth);
		 
		 String jwt = JwtProvider.generateToken(auth);
		 AuthResponse res = new AuthResponse();

		 res.setJwt(jwt);

		 res.setStatus(true);

		 res.setMessage("login success");
		return new ResponseEntity<>(res, HttpStatus.CREATED);
	
}
	private Authentication authenticate(String username, String password) {
		UserDetails userDetails = customUserDetails.loadUserByUsername(username);
		if (userDetails == null) {
			throw new BadCredentialsException("Invalid username or password");
		}
		if(!password.equals(userDetails.getPassword())) {
			throw new BadCredentialsException("Invalid username or password");
		}
		return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
	}
}
