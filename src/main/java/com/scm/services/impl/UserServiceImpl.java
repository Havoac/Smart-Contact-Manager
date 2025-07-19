package com.scm.services.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.scm.constants.AppConstants;
import com.scm.entities.User;
import com.scm.exceptions.ResourceNotFoundException;
import com.scm.exceptions.UserNotFoundException;
import com.scm.repositories.UserRepo;
import com.scm.services.UserService;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User SaveUser(User user) {
        String userId = UUID.randomUUID().toString();
        user.setUserId(userId);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        user.setRoleList(List.of(AppConstants.ROLE_USER));

        return userRepo.save(user);
    }

    @Override
    public void DeleteUser(String id) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with the id : " + id));
        userRepo.delete(user);
    }

    @Override
    public User GetUserById(String id) {
        return userRepo.findById(id).orElseThrow(() -> new UserNotFoundException("User not found with the id : " + id));
    }

    @Override
    public User UpdateUser(User newUser) {
        User oldUser = userRepo.findById(newUser.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found with the id : " + newUser.getUserId()));

        oldUser.setAbout(newUser.getAbout());
        oldUser.setEmail(newUser.getEmail());
        oldUser.setName(newUser.getName());
        oldUser.setPassword(newUser.getPassword());
        oldUser.setProfilePic(newUser.getProfilePic());
        oldUser.setEmailVerified(newUser.isEmailVerified());
        oldUser.setPhoneVerified(newUser.isPhoneVerified());

        return userRepo.save(newUser);
    }

    @Override
    public boolean IsUserExist(String id) {
        return userRepo.findById(id).orElse(null) != null;
    }

    @Override
    public boolean IsUserExistByEmail(String email) {
        return userRepo.findByEmail(email).orElse(null) != null;
    }

    @Override
    public List<User> GetAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepo.findByEmail(email)
                .orElse(null);
    }

}
