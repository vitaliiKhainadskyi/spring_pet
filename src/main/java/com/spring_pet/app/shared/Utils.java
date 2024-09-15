package com.spring_pet.app.shared;

import java.util.UUID;

import org.springframework.stereotype.Service;

// This class is better to be static utility class, but for the sake of testing 
// The dependency injection let it be this way
@Service
public class Utils {
    public String generateUserUUID() {
        return UUID.randomUUID().toString();
    }
}