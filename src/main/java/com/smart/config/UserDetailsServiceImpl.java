package com.smart.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.smart.dao.UserRepository;
import com.smart.entities.User;

public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // fetching user from database

        User user = userRepository.getUserByUserName(username);

        if (user == null)
            throw new UsernameNotFoundException("Could not found user !!!");

        UserDetailsImpl userDetailsImpl = new UserDetailsImpl(user);

        // System.out.println("Fetched username : " + userDetailsImpl.getUsername() + ",
        // Fetched User Password : "
        // + userDetailsImpl.getPassword());

        return userDetailsImpl;
    }

}
