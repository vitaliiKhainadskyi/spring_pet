package com.spring_pet.app.ui.controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

import com.spring_pet.app.shared.dto.UserDto;
import com.spring_pet.app.ui.model.request.UpdateUserRequest;
import com.spring_pet.app.ui.model.request.UserRequest;
import com.spring_pet.app.ui.model.response.UserResponse;
import com.spring_pet.app.userservice.UserService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("users") // localhost:9090/users
public class UserController {

    public static Map<String, UserResponse> usersMap = new HashMap<>();

    @Autowired
    UserService<UserDto> userService;

    @GetMapping()
    public ResponseEntity<Collection<UserResponse>> getUsers(
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "5") int limit,
            @RequestParam(required = false, defaultValue = "desc") String sort

    ) {
        List<UserResponse> returnValue = new ArrayList<>();

        List<UserDto> users = userService.getUsers(page, limit);

        for (UserDto userDto : users) {
            UserResponse userModel = new UserResponse();
            BeanUtils.copyProperties(userDto, userModel);
            returnValue.add(userModel);
        }

        return ResponseEntity.ok(returnValue);
    }

    @GetMapping(path = "/{userId}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<UserResponse> getUser(@PathVariable String userId) {
        UserResponse returnValue = new UserResponse();

        UserDto userDto = userService.getUserByUserId(userId);
        ModelMapper modelMapper = new ModelMapper();
        returnValue = modelMapper.map(userDto, UserResponse.class);

        return ResponseEntity.ok(returnValue);
    }

    @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<Object> createUser(@Valid @RequestBody UserRequest userData) {

        UserResponse returnObj = new UserResponse();

        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userData, userDto);

        UserDto createdUser = userService.createUser(userDto);

        BeanUtils.copyProperties(createdUser, returnObj);

        return ResponseEntity.ok(returnObj);
    }

    @PatchMapping(path = "/{userId}", consumes = { MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<Object> updateUser(@PathVariable String userId,
            @Valid @RequestBody UpdateUserRequest userData) {
        UserResponse returnValue = new UserResponse();

        UserDto userDto = new UserDto();
        userDto = new ModelMapper().map(userData, UserDto.class);

        UserDto updateUser = userService.updateUser(userId, userDto);
        returnValue = new ModelMapper().map(updateUser, UserResponse.class);

        return ResponseEntity.ok(returnValue);
    }

    @DeleteMapping(path = "/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);

        return ResponseEntity.noContent().build();
    }
}
