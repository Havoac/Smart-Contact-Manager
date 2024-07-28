package com.smart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.smart.dao.UserRepository;
import com.smart.entities.User;
import com.smart.helper.Message;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/home")
    public String Home(Model model) {
        model.addAttribute("title", "Home - Smart Contact Manager");
        return "home";
    }

    @RequestMapping("/about")
    public String About(Model model) {
        model.addAttribute("title", "About - Smart Contact Manager");
        return "about";
    }

    @RequestMapping("/signup")
    public String Signup(Model model) {
        model.addAttribute("title", "Register - Smart Contact Manager");
        model.addAttribute("user", new User());
        return "signup";
    }

    @RequestMapping(value = "/do_register", method = RequestMethod.POST)
    public String RegisterUser(@ModelAttribute("user") User user,
            @RequestParam(value = "agreement", defaultValue = "false") boolean agreement, Model model,
            HttpSession session) {

        try {
            if (!agreement)
                throw new Exception("You have not agreed to terms and conditions");

            user.setRole("ROLE_USER");
            user.setEnabled(true);

            user.setPassword(passwordEncoder.encode(user.getPassword()));
            // System.out.println("Encoded Password: " +
            // passwordEncoder.encode(user.getPassword()));

            User result = this.userRepository.save(user);

            model.addAttribute("user", new User());

            session.setAttribute("message", new Message("Successfully registered", "alert-success"));

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("user", user);
            session.setAttribute("message", new Message("something went wrong " + e.getMessage(), "alert-danger"));
        }

        if (session.getAttribute("message") != null) {
            model.addAttribute("message", session.getAttribute("message"));
            session.removeAttribute("message"); // Remove the message from the session
        }

        return "signup";
    }

    @RequestMapping("/signin")
    public String customLogin(Model model) {
        model.addAttribute("title", "Login Page");
        return "login";
    }
}
