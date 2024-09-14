package com.facebook.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.facebook.Entity.SigninRequest;
import com.facebook.Entity.SignupRequest;
import com.facebook.Entity.User;
import com.facebook.Service.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private UserService userService;

	@PostMapping("/signup")
	public ResponseEntity<String> signup(@RequestBody SignupRequest signupRequest) {

		User user = new User(signupRequest.getUsername(), signupRequest.getPassword(), signupRequest.getEmailId());

		userService.signup(user);
		return ResponseEntity.ok("User registered successfully");
	}

	@PostMapping("/signin")
	public ResponseEntity<String> signin(@RequestBody SigninRequest signinRequest) {
		String token = userService.signin(signinRequest.getUsername(), signinRequest.getPassword());
		if (token != null) {
			return ResponseEntity.ok(token);
		} else {
			return ResponseEntity.status(401).body("Invalid credentials");
		}
	}
}
