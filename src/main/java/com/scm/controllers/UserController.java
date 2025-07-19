package com.scm.controllers;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.scm.helper.Helper;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private Helper helper;

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    // dashboard
    @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public String UserDashboard() {
        return "user/dashboard";
    }

    // profile
    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String UserProfile(Authentication authentication) {
        String user = helper.getEmailOfLoggedInUser(authentication);

        logger.info("User is " + user);

        return "user/profile";
    }
}
