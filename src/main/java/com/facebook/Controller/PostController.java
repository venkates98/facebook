package com.facebook.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.facebook.Entity.Post;

import com.facebook.Service.PostService;
import com.facebook.utitility.JwtUtil;

import java.io.IOException;

@RestController
@RequestMapping("/api/posts")
public class PostController {

	@Autowired
	private PostService postService;

	@Autowired
	private JwtUtil jwtUtil;

	@PostMapping("/create/{userId}")
	public ResponseEntity<Post> createPost(@RequestHeader("Authorization") String token, @RequestPart("post") Post post,
			@RequestPart("image") MultipartFile imageFile, @PathVariable Long userId) throws IOException {

		String jwtToken = token.replace("Bearer ", "");
		if (jwtUtil.validateToken(jwtToken, post.getUsername())) {
			// Store the file and get the file path
			String imagePath = getFileName(imageFile);

			// Set the image path in the post
			post.setImage(imagePath);

			Post createdPost = postService.createPost(post, userId);
			return ResponseEntity.ok(createdPost);
		} else {
			return ResponseEntity.status(401).body(null);
		}
	}

	private String getFileName(MultipartFile file) throws IOException {
		// InputStream input = file.getInputStream();
		return file.getOriginalFilename();

	}
}
