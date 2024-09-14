package com.facebook.Service;

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
}
