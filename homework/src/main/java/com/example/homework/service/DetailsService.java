package com.example.homework.service;

import com.example.homework.model.entity.jpa.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface DetailsService extends UserDetailsService {

    UserDetails loadUserByUsername(String username);

    User createUser(User user);

}
