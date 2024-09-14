package com.facebook.Service;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.facebook.Entity.Post;
import com.facebook.Repository.PostRepository;

@Service
public class PostService {

	@Autowired
	private PostRepository postRepository;

	public Post createPost(Post post, Long userId) {
		// Set the userId in the post
		post.setUserId(userId);

		// Save the post to the repository
		return postRepository.save(post);
		  
	}
	
	    // Get a single post by postId
	    public Post getPostById(Long postId) {
	        return postRepository.findById(postId).orElse(null);
	    }

	    // Update a post by postId
	    public Post updatePost(Long postId, Post postDetails) {
	        Post existingPost = postRepository.findById(postId).orElse(null);
	        if (existingPost != null) {
	           
	            existingPost.setDescription(postDetails.getDescription());
	            existingPost.setImage(postDetails.getImage());
	            return postRepository.save(existingPost);
	              
	        }
	        return null;
	    }

	    // Delete a post by postId
	    public String deletePost(Long postId) {
	        postRepository.deleteById(postId);
	        return "Post Deleted Successfully";
	    }

	    // Get list of posts by userId
	    public Optional<Post> getPostsByUserId(Long userId) {
	        return postRepository.findById(userId);
	    }
	}

	
	
	
	
	
	
	

