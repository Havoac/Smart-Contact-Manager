package com.smart.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.smart.dao.ContactRepository;
import com.smart.dao.UserRepository;
import com.smart.entities.Contact;
import com.smart.entities.User;
import com.smart.helper.Message;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ContactRepository contactRepository;

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
    public String processContact(@ModelAttribute Contact contact,
            @RequestParam("profileImage") MultipartFile file, Principal principal, HttpSession session, Model model) {
        try {
            String name = principal.getName();
            User user = this.userRepository.getUserByUserName(name);

            // processing and uploading the file
            if (!file.isEmpty()) {
                contact.setImage(file.getOriginalFilename());

                File saveFile = new ClassPathResource("/static/img").getFile();
                Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + file.getOriginalFilename());
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            } else {
                contact.setImage("null_profile.png");
            }

            contact.setUser(user);
            user.getContacts().add(contact);

            // saving to database
            this.userRepository.save(user);

            // successfull message
            session.setAttribute("message", new Message("Your Contact is added !! Add more", "success"));
        } catch (Exception e) {
            System.out.println("Error : " + e.getMessage());
            e.printStackTrace();

            // error message
            session.setAttribute("message", new Message("Something went wrong !! Try again", "danger"));
        }

        // removing the message alert, i.e, if we move to other pages from there, the
        // alert message is still visible
        if (session.getAttribute("message") != null) {
            model.addAttribute("message", session.getAttribute("message"));
            session.removeAttribute("message");
        }

        return "normal/add_contact_form";
    }

    // show contacts handler
    // per page = 2[n]
    // current page = 0 [page]
    @GetMapping("/show-contacts/{page}")
    public String showContacts(@PathVariable("page") Integer page, Model model, Principal principal) {
        model.addAttribute("title", "User Contacts");

        String userName = principal.getName();
        User user = this.userRepository.getUserByUserName(userName);

        PageRequest pageable = PageRequest.of(page, 2);

        Page<Contact> contacts = this.contactRepository.findContactsByUser(user.getId(), pageable);

        model.addAttribute("contacts", contacts);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", contacts.getTotalPages());

        return "normal/show_contacts";
    }

    // showing particular contact details
    @RequestMapping("/contact/{cId}")
    public String ShowContactDetail(@PathVariable("cId") Integer cId, Model model, Principal principal) {
        Optional<Contact> contactOptional = this.contactRepository.findById(cId);

        Contact contact = contactOptional.get();
        // Contact contact = this.contactRepository.getById(cId);

        String username = principal.getName();
        User user = this.userRepository.getUserByUserName(username);

        if (user.getId() == contact.getUser().getId()) {
            model.addAttribute("contact", contact);
            model.addAttribute("title", contact.getName());
        }

        return "normal/contact_detail";
    }

    // delete contact handler
    @GetMapping("/delete_contact/{cId}")
    public String DeleteContact(@PathVariable("cId") Integer cId, Model model, Principal principal,
            HttpSession session, RedirectAttributes redirectAttributes) {
        Contact contact = this.contactRepository.getById(cId); // it is depreciated

        String username = principal.getName();
        User user = this.userRepository.getUserByUserName(username);

        int userId = user.getId();
        int contactUserId = contact.getUser().getId();

        contact.setUser(null); // user is linked to contact, so had to set it to null to delete it

        if (userId == contactUserId) {
            this.contactRepository.delete(contact);
            redirectAttributes.addFlashAttribute("message", new Message("Contact deleted successfully....", "success"));
        } else {
            redirectAttributes.addFlashAttribute("message", new Message("Something went wrong !! Try again", "danger"));
        }

        return "redirect:/user/show-contacts/0";
    }

    // update contact handler
    @PostMapping("/update-contact/{cId}")
    public String UpdateContact(@PathVariable("cId") Integer cId, Model model) {
        model.addAttribute("title", "Update Contact");

        Contact contact = this.contactRepository.findById(cId).get();

        model.addAttribute("contact", contact);

        return "normal/update_form";
    }

    // process update contact handler
    @RequestMapping(value = "/process-update", method = RequestMethod.POST)
    public String UpdateHandler(@ModelAttribute Contact contact, @RequestParam("profileImage") MultipartFile file,
            Model model, HttpSession session, Principal principal) {

        try {
            Contact oldContact = this.contactRepository.findById(contact.getcId()).get();

            if (!file.isEmpty()) {
                // delete old profile image
                File deleteFile = new ClassPathResource("/static/img").getFile();
                File toBeDeletedFile = new File(deleteFile, oldContact.getImage());
                toBeDeletedFile.delete();

                // update new profile photo
                File saveFile = new ClassPathResource("/static/img").getFile();
                Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + file.getOriginalFilename());
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                contact.setImage(file.getOriginalFilename());
            } else {
                contact.setImage(oldContact.getImage());
            }

            User user = this.userRepository.getUserByUserName(principal.getName());
            contact.setUser(user);
            this.contactRepository.save(contact);

            session.setAttribute("message", new Message("Your Contact information is update !!", "success"));
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("message", new Message("Something went wrong !!", "danger"));
        }

        if (session.getAttribute("message") != null) {
            model.addAttribute("message", session.getAttribute("message"));
            session.removeAttribute("message");
        }

        return "redirect:/user/contact/" + contact.getcId();
    }
}
