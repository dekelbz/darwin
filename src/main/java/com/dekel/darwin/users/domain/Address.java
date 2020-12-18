package com.dekel.darwin.users.domain;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class Address {
    @NotEmpty
    private Point location;
    @NotEmpty
    private String city;
}
