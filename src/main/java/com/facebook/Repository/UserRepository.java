package com.facebook.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.facebook.Entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    User findByUsername(String username);
}

