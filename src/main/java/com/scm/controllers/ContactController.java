package com.scm.controllers;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.scm.message.Message;
import com.scm.message.MessageType;
import com.scm.services.ContactService;
import com.scm.services.ImageService;
import com.scm.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/user/contacts")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private Helper helper;

    @Autowired
    private UserService userService;

    private Logger logger = LoggerFactory.getLogger(ContactController.class);

    @RequestMapping("/add")
    public String addContactView(Model model) {
        model.addAttribute("isUserPage", true);

        ContactForm contactForm = new ContactForm();
        model.addAttribute("contactForm", contactForm);

        return "user/add_contact";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String SaveContact(@Valid @ModelAttribute ContactForm contactForm, BindingResult result,
            Authentication authentication, HttpSession session) {

        if (result.hasErrors()) {
            session.setAttribute("message",
                    Message.builder().content("Please correct the following errors").messageType(MessageType.red)
                            .build());

            return "user/add_contact";
        }

        String userName = helper.getEmailOfLoggedInUser(authentication);

        User user = userService.getUserByEmail(userName);

        String fileName = UUID.randomUUID().toString();
        String fileURL = imageService.uploadImage(contactForm.getPicture(), fileName);

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
        contact.setPicture(fileURL);

        contactService.save(contact);

        session.setAttribute("message",
                Message.builder().content("New Contact has been added").messageType(MessageType.green).build());

        return "redirect:/user/contacts/add";
    }

    @RequestMapping
    public String ViewContacts(Model model, Authentication authentication) {
        model.addAttribute("isUserPage", true);

        String username = helper.getEmailOfLoggedInUser(authentication);
        User user = userService.getUserByEmail(username);
        List<Contact> contacts = contactService.getByUser(user);

        model.addAttribute("contacts", contacts);

        return "user/contacts";
    }
}
