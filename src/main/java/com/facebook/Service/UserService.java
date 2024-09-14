package com.facebook.Service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.facebook.Entity.User;
import com.facebook.Repository.UserRepository;
import com.facebook.utitility.JwtUtil;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public void signup(User user) {
		// Encode the password before saving the user
		user.setPassword(passwordEncoder.encode(user.getPassword()));

		
		userRepository.save(user);
	}

	public String signin(String username, String password) {
		
		User user = userRepository.findByUsername(username);

		// Check if the user exists and the password matches
		if (user != null && passwordEncoder.matches(password, user.getPassword())) {
			// Generate and return the JWT token
			return jwtUtil.generateToken(username);
		}

		// Return null if authentication fails
		return "Username or Password is Incorrect";
	}
}
