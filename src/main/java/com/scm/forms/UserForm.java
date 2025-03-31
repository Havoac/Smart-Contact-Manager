package com.scm.forms;

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
    private String userName;
    private String email;
    private String password;
    private String phoneNo;
    private String about;
}
