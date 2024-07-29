package com.smart.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.smart.dao.UserRepository;
import com.smart.entities.Contact;
import com.smart.entities.User;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    // this will be available for all the pages
    // response method for adding common data
    @ModelAttribute
    public void addCommonData(Model model, Principal principal) {
        String userName = principal.getName(); // username - email

        User user = userRepository.getUserByUserName(userName);

        model.addAttribute("user", user);
    }

    // dashboard home
    @RequestMapping("/index")
    public String dashboard(Model model, Principal principal) {
        model.addAttribute("title", "User Dashboard");
        return "normal/user_dashboard";
    }

    // open add form controller
    @RequestMapping("/add-contact")
    public String openAddContactForm(Model model) {
        model.addAttribute("title", "Add Contact");
        model.addAttribute("contact", new Contact());
        return "normal/add_contact_form";
    }

    // processing add contact form
    @PostMapping("/process-contact")
    public String processContact(@ModelAttribute Contact contact, Principal principal) {
        String name = principal.getName();
        User user = this.userRepository.getUserByUserName(name);

        contact.setUser(user);
        user.getContacts().add(contact);

        // saving to database
        this.userRepository.save(user);

        return "normal/add_contact_form";
    }
}
