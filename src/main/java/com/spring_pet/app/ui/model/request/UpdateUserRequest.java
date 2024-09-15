package com.spring_pet.app.ui.model.request;

import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class UpdateUserRequest {
    @Size(min = 3, message = "'firstName' should contain more than 3 characters")
    private String firstName;
    @Size(min = 3, message = "'lastName' should contain more than 3 characters")
    private String lastName;
}
