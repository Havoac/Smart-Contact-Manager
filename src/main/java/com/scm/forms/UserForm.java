package com.scm.forms;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
public class UserForm {
    @NotBlank(message = "User name is required")
    @Size(min = 3, message = "Minimum size should be 3")
    @Column(name = "user_name", nullable = false)
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid Email")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;

    @NotBlank(message = "Phone Number is required")
    private String phoneNo;

    @NotBlank(message = "About is required")
    private String about;
}
