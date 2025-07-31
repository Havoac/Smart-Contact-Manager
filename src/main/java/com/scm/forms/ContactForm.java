package com.scm.forms;

import org.springframework.web.multipart.MultipartFile;

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
    private String contactName;
    private String email;
    private String phoneNo;
    private String address;
    private String description;
    private boolean favourite = false;
    private String websiteLink;
    private String linkedInLink;
    private MultipartFile picture;
}
