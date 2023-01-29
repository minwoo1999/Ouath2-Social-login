package com.example.security1.repository;

import com.example.security1.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {

    public User findByUsername(String username);

    public User findByEmail(String email);
}
