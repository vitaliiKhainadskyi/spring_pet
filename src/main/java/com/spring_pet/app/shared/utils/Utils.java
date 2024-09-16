package com.spring_pet.app.shared.utils;

import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class Utils {
    public String generateUserUUID() {
        return UUID.randomUUID().toString();
    }
}