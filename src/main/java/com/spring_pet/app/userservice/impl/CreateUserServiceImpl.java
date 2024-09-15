package com.spring_pet.app.userservice.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.spring_pet.app.exceptions.APIException;
import com.spring_pet.app.shared.Utils;
import com.spring_pet.app.ui.controllers.UserController;
import com.spring_pet.app.ui.model.request.UserRequest;
import com.spring_pet.app.ui.model.response.UserResponse;
import com.spring_pet.app.userservice.CreateUserService;

@Service
public class CreateUserServiceImpl implements CreateUserService<UserResponse, UserRequest> {
    private final String USER_EXISTS_MESSAGE = "User by email '%s' already exists";
    @Autowired
    private Utils utils;

    // not needed at least for this class
    // @Autowired
    // public CreateUserServiceImpl(Utils utils) {
    // this.utils = new Utils();
    // }

    @Override
    public UserResponse createUser(UserRequest userData) {
        String userId = utils.generateUserUUID();
        String userEmail = userData.getEmail();

        UserResponse userResponse = new UserResponse(userData.getFirstName(), userData.getLastName(),
                userEmail,
                userId);

        if (!isUserByEmailPresentInMap(userEmail)) {
            UserController.usersMap.put(userId, userResponse);
            return userResponse;
        }

        throw new APIException(400, String.format(USER_EXISTS_MESSAGE, userEmail), HttpStatus.BAD_REQUEST);
    }

    private boolean isUserByEmailPresentInMap(String userEmail) {
        return UserController.usersMap.values().stream().anyMatch((user) -> user.getEmail().equals(userEmail));
    }
}