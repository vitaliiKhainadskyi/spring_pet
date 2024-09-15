package com.spring_pet.app.ui.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
@AllArgsConstructor
@Builder(toBuilder = true)
// @JsonIgnoreProperties(ignoreUnknown = true)
public class UserResponse {
    private String firstName;
    private String lastName;
    private String email;
    private String userId;
}
