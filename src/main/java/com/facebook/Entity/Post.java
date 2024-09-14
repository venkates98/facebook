package com.facebook.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "psot")
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long postId;
	private Long userId;
	private String username;
	private String description;
	private String image;

	public Post() {

	}

	@Override
	public String toString() {
		return "Post [postId=" + postId + ", userId=" + userId + ", username=" + username + ", description="
				+ description + ", image=" + image + "]";
	}

	public Post(Long postId, Long userId, String username, String description, String image) {
		super();
		this.postId = postId;
		this.userId = userId;
		this.username = username;
		this.description = description;
		this.image = image;
	}

	public Long getPostId() {
		return postId;
	}

	public void setPostId(Long postId) {
		this.postId = postId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

}