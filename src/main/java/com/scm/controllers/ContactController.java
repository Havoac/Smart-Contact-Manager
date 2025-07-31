package com.scm.controllers;

import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.scm.entities.Contact;
import com.scm.entities.User;
import com.scm.forms.ContactForm;
import com.scm.helper.Helper;
import com.scm.services.ContactService;
import com.scm.services.UserService;

@Controller
@RequestMapping("/user/contacts")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @Autowired
    private Helper helper;

    @Autowired
    private UserService userService;

    @RequestMapping("/add")
    public String addContactView(Model model) {
        model.addAttribute("isUserPage", true);

        ContactForm contactForm = new ContactForm();
        model.addAttribute("contactForm", contactForm);

        return "user/add_contact";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String SaveContact(@ModelAttribute ContactForm contactForm, Authentication authentication) {
        String userName = helper.getEmailOfLoggedInUser(authentication);

        User user = userService.getUserByEmail(userName);

        // process the form data

        Contact contact = new Contact();

        contact.setContactName(contactForm.getContactName());
        contact.setFavourite(contactForm.isFavourite());
        contact.setEmail(contactForm.getEmail());
        contact.setPhoneNo(contactForm.getPhoneNo());
        contact.setAddress(contactForm.getAddress());
        contact.setDescription(contactForm.getDescription());
        contact.setUser(user);
        contact.setLinkedInLink(contactForm.getLinkedInLink());
        contact.setWebsiteLink(contactForm.getWebsiteLink());

        contactService.save(contact);

        return "redirect:/user/contacts/add";
    }
}
