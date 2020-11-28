package com.dekel.darwin.users.domain;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

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

    public static UserDTOBuilder builder() {
        return new UserDTOBuilder();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getRoleTitle() {
        return roleTitle;
    }

    public void setRoleTitle(String roleTitle) {
        this.roleTitle = roleTitle;
    }

}
