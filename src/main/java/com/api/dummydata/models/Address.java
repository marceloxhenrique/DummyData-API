package com.api.dummydata.models;

import lombok.Data;

@Data
public class Address {
    private String country;
    private String state;
    private String city;
    private String street;
    private String postalCode;
}
