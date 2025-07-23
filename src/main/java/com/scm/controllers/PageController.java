package com.scm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.scm.entities.User;
import com.scm.forms.UserForm;
import com.scm.message.Message;
import com.scm.message.MessageType;
import com.scm.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class PageController {
    @Autowired
    UserService userService;

    // @GetMapping("/whoami")
    // @ResponseBody
    // public String whoAmI(Authentication auth) {
    // return auth == null ? "Anonymous" : "Logged in as: " + auth.getName();
    // }

    @RequestMapping("/")
    public String Index(Model model) {
        // model.addAttribute("isUserPage", false);
        return "redirect:/home";
    }

    @RequestMapping("/home")
    public String Home(Model model) {
        model.addAttribute("home", "Welcome to the home page");
        // model.addAttribute("isUserPage", false);
        return "home";
    }

    @RequestMapping("/about")
    public String About(Model model) {
        model.addAttribute("about", "Welcome to the about page");
        // model.addAttribute("isUserPage", false);
        return "about";
    }

    @RequestMapping("/services")
    public String Services(Model model) {
        // model.addAttribute("isUserPage", false);
        return "services";
    }

    @RequestMapping("/contact")
    public String Contact(Model model) {
        // model.addAttribute("isUserPage", false);
        return "contact";
    }

    @RequestMapping("/login")
    public String Login(Model model) {
        // model.addAttribute("isUserPage", false);
        return "login";
    }

    @RequestMapping("/signup")
    public String SignUp(Model model) {
        UserForm userForm = new UserForm();
        model.addAttribute(userForm);
        // model.addAttribute("isUserPage", false);
        return "signup";
    }

    @RequestMapping(value = "/do-register", method = RequestMethod.POST)
    public String RegisterForm(@Valid @ModelAttribute UserForm userForm, BindingResult result, HttpSession session) {
        if (result.hasErrors())
            return "signup";

        // save the data to database
        User user = new User();

        System.out.println(userForm.toString());

        user.setName(userForm.getName());
        user.setEmail(userForm.getEmail());
        user.setPassword(userForm.getPassword());
        user.setPhoneNo(userForm.getPhoneNo());
        user.setAbout(userForm.getAbout());

        userService.SaveUser(user);

        // pop up message
        Message message = Message.builder().content("Registeration Successful").messageType(MessageType.green).build();
        session.setAttribute("message", message);

        return "redirect:/signup";
    }
}
