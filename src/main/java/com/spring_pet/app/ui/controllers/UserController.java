package com.spring_pet.app.ui.controllers;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring_pet.app.ui.model.request.UpdateUserRequest;
import com.spring_pet.app.ui.model.request.UserRequest;
import com.spring_pet.app.ui.model.response.UserResponse;
import com.spring_pet.app.userservice.CreateUserService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("users") // localhost:9090/users
public class UserController {

    public static Map<String, UserResponse> usersMap = new HashMap<>();

    @Autowired
    CreateUserService<UserResponse, UserRequest> userService;

    @GetMapping()
    public ResponseEntity<Collection<UserResponse>> getUsers(
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "50") int limit,
            @RequestParam(required = false, defaultValue = "desc") String sort

    ) {
        return ResponseEntity.ok(usersMap.values());
    }

    // current version doesnt require this produces, xml returns without it
    @GetMapping(path = "/{userId}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<UserResponse> getUser(@PathVariable String userId) {

        if (isUserByIdPresentInMap(userId)) {
            return ResponseEntity.ok(usersMap.get(userId));
        } else {
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<Object> createUser(@Valid @RequestBody UserRequest userData) {

        return ResponseEntity.ok(userService.createUser(userData));

    }

    @PatchMapping(path = "/{userId}", consumes = { MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<Object> updateUser(@PathVariable String userId,
            @Valid @RequestBody UpdateUserRequest userData) {
        if (!isUserByIdPresentInMap(userId)) {
            return new ResponseEntity<>(String.format("User by the id %s not exists", userId),
                    HttpStatus.BAD_REQUEST);
        } else {
            UserResponse user = usersMap.get(userId);
            user.setFirstName(isNotEmpty(userData.getFirstName()) ? userData.getFirstName() : user.getFirstName());
            user.setLastName(isNotEmpty(userData.getLastName()) ? userData.getLastName() : user.getLastName());

            // user = user.toBuilder().firstName(
            // StringUtils.isNotEmpty(userData.getFirstName()) ? userData.getFirstName() :
            // user.getFirstName())
            // .lastName(
            // StringUtils.isNotEmpty(userData.getLastName()) ? userData.getLastName()
            // : user.getLastName())
            // .build();

            // usersMap.replace(userId, user);

            return ResponseEntity.ok(user);
        }
    }

    @DeleteMapping(path = "/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable String userId) {
        usersMap.remove(userId);

        return ResponseEntity.noContent().build();
    }

    private boolean isUserByIdPresentInMap(String userId) {
        return usersMap.containsKey(userId);
    }
}
