package com.spring_pet.app.shared.dto;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class UserDto implements Serializable {
    private static final long serialVersionUID = 683519260189364280L;
    private long id;
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String encryptedPasswrod;
    private String emailVerificationToken;
    private Boolean emailVerificationStatus = false;
}
