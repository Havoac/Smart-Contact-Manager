package com.scm.services;

import java.util.List;

import com.scm.entities.User;

public interface UserService {
    User SaveUser(User user);

    void DeleteUser(String id);

    User GetUserById(String id);

    User UpdateUser(User user);

    boolean IsUserExist(String id);

    boolean IsUserExistByEmail(String email);

    List<User> GetAllUsers();

    User getUserByEmail(String email);
}
