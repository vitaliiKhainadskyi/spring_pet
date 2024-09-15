package com.spring_pet.app.ui.model.response;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
// @JsonInclude(Include.NON_NULL)
public class ValidationResponse {
    private Date timeStamp;
    private int errorCode;
    private String errorMessage;
}