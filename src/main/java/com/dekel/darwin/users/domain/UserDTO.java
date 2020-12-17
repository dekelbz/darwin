package com.dekel.darwin.users.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    @Email
    private String email;
    @NotEmpty(message = "{error.empty.first.name}")
    @Size(min=2, max=30, message = "{error.size.first.name}")
    private String firstName;
    @NotEmpty(message = "{error.empty.last.name}")
    @Size(min=2, max=30, message = "{error.size.last.name}")
    private String lastName;
    @NotEmpty(message = "{error.empty.password}")
    @Size(min=8, max=30, message = "{error.size.password}")
    private String password;
    @NotEmpty(message = "{error.empty.phone.number}")
    @Size(min=10, max=15, message = "{error.size.phone.number}")
    private String phoneNumber;
    @NotEmpty(message = "{error.empty.department}")
    @Size(min=2, max=30, message = "{error.size.department}")
    private String department;
    @NotEmpty(message = "{error.empty.role.title}")
    @Size(min=2, max=30, message = "{error.size.role.title}")
    private String roleTitle;


}
