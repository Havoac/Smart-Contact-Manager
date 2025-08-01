package com.scm.forms;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ContactForm {
    @NotBlank(message = "Name is required")
    private String contactName;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid Email Address")
    private String email;

    @NotBlank(message = "Phone no is required")
    @Pattern(regexp = "^[0-9]{10}$", message = "Invalid Phone No")
    private String phoneNo;

    private String address;
    private String description;
    private boolean favourite = false;
    private String websiteLink;
    private String linkedInLink;
    private MultipartFile picture;
}
