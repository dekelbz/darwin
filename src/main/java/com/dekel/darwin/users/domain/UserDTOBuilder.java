package com.dekel.darwin.users.domain;

public class UserDTOBuilder {

    private final UserDTO user = new UserDTO();

    UserDTOBuilder() {
    }

    public UserDTOBuilder email(String email) {
        user.setEmail(email);
        return this;
    }

    public UserDTOBuilder firstName(String firstName) {
        user.setFirstName(firstName);
        return this;
    }

    public UserDTOBuilder lastName(String lastName) {
        user.setLastName(lastName);
        return this;
    }

    public UserDTOBuilder password(String password) {
        user.setPassword(password);
        return this;
    }

    public UserDTOBuilder phoneNumber(String phoneNumber) {
        user.setPhoneNumber(phoneNumber);
        return this;
    }

    public UserDTOBuilder department(String department) {
        user.setDepartment(department);
        return this;
    }

    public UserDTOBuilder roleTitle(String roleTitle) {
        user.setRoleTitle(roleTitle);
        return this;
    }

    public UserDTO build() {
        return user;
    }
}
