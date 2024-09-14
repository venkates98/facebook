package com.facebook.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.facebook.Entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

}
