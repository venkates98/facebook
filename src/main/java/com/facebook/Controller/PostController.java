package com.facebook.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.facebook.Entity.Post;

import com.facebook.Service.PostService;
import com.facebook.utitility.JwtUtil;

import java.io.IOException;

import java.util.Optional;

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

	@DeleteMapping("/delete/{postId}")
	public ResponseEntity<?> deletePost(@RequestHeader("Authorization") String token, @PathVariable Long postId) {
		String jwtToken = token.replace("Bearer ", "");

		// Assuming Post entity has the username to check against JWT
		Post post = postService.getPostById(postId);

		if (post != null && jwtUtil.validateToken(jwtToken, post.getUsername())) {
			postService.deletePost(postId);
			return ResponseEntity.ok("Post deleted successfully.");
		} else {
			return ResponseEntity.status(401).body("Unauthorized");
		}
	}

	@PutMapping("/update/{postId}")
	public ResponseEntity<Post> updatePost(@RequestHeader("Authorization") String token,
			@RequestPart("post") Post postDetails,
			@RequestPart(value = "image", required = false) MultipartFile imageFile, @PathVariable Long postId)
			throws IOException {
		String jwtToken = token.replace("Bearer ", "");

		// Get existing post
		Post existingPost = postService.getPostById(postId);

		if (existingPost != null && jwtUtil.validateToken(jwtToken, existingPost.getUsername())) {
			if (imageFile != null) {
				String imagePath = getFileName(imageFile);
				postDetails.setImage(imagePath);
			}

			// Update post details
			Post updatedPost = postService.updatePost(postId, postDetails);
			return ResponseEntity.ok(updatedPost);
		} else {
			return ResponseEntity.status(401).body(null);
		}
	}

	@GetMapping("/user/{userId}")
	public ResponseEntity<Optional<Post>> getPostsByUserId(@RequestHeader("Authorization") String token,
			@PathVariable Long userId) {
		String jwtToken = token.replace("Bearer ", "");

		// Assuming the JWT token contains the username, and you can fetch posts by the
		// user
		if (jwtUtil.validateToken(jwtToken, jwtUtil.extractUsername(jwtToken))) {
			Optional<Post> userPosts = postService.getPostsByUserId(userId);
			return ResponseEntity.ok(userPosts);
		} else {
			return ResponseEntity.status(401).body(null);
		}
	}

}
