package com.spring_pet.app.ui.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
@Data
public class UserRequest {
    @NotNull(message = "'firstName' should not be empty")
    @Size(min = 3, message = "'firstName' should contain more than 3 characters")
    private String firstName;
    @NotNull(message = "'lastName' should not be empty")
    @Size(min = 3, message = "'lastName' should contain more than 3 characters")
    private String lastName;
    @NotNull(message = "'email' should not be empty")
    @Email
    private String email;
    @NotNull(message = "'password' should not be empty")
    @Size(min = 6, max = 16, message = "'password' should contain from 6 to 16 characters")
    private String password;
}