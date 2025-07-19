package com.scm.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.scm.entities.User;
import com.scm.helper.Helper;
import com.scm.services.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private Helper helper;

    private Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;

    // dashboard
    @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public String UserDashboard() {
        return "user/dashboard";
    }

    // profile
    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String UserProfile(Model model, Authentication authentication) {
        String userName = helper.getEmailOfLoggedInUser(authentication);

        logger.info("User is " + userName);

        User user = userService.getUserByEmail(userName);

        model.addAttribute("loggedInUser", user);

        return "user/profile";
    }
}
