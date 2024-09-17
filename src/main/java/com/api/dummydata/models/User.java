package com.api.dummydata.models;

import java.time.LocalDate;
import lombok.Data;

@Data
public class User {
    private int id;
    private String username;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String phone;
    private String email;
    private Address address;
    private String role;
}
