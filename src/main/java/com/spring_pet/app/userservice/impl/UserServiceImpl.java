package com.spring_pet.app.userservice.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring_pet.app.exceptions.APIException;
import com.spring_pet.app.io.entity.UserEntity;
import com.spring_pet.app.io.repository.UserRepository;
import com.spring_pet.app.shared.dto.UserDto;
import com.spring_pet.app.shared.utils.Utils;
import com.spring_pet.app.ui.model.response.ErrorMessages;
import com.spring_pet.app.userservice.UserService;

import lombok.NoArgsConstructor;

@Service
@NoArgsConstructor
public class UserServiceImpl implements UserService<UserDto> {
    private final String USER_EXISTS_MESSAGE = "User by email '%s' already exists";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Utils utils;

    // @Autowired
    // BCryptPasswordEncoder passwordEncoder;

    @Override
    public List<UserDto> getUsers(int page, int limit) {
        List<UserDto> returnValue = new ArrayList<>();

        if (page > 0)
            page = page - 1;

        Pageable pageableRequest = PageRequest.of(page, limit);

        Page<UserEntity> usersPage = userRepository.findAll(pageableRequest);
        List<UserEntity> users = usersPage.getContent();

        for (UserEntity userEntity : users) {
            UserDto userDto = new UserDto();
            BeanUtils.copyProperties(userEntity, userDto);
            returnValue.add(userDto);
        }

        return returnValue;
    }

    @Override
    public UserDto getUserByUserId(String userId) {
        UserDto returnValue = new UserDto();
        UserEntity userEntity = userRepository.findByUserId(userId);

        if (userEntity == null)
            throw new APIException(500, "User with ID: " + userId + " not found", HttpStatus.INTERNAL_SERVER_ERROR);

        BeanUtils.copyProperties(userEntity, returnValue);

        return returnValue;
    }

    @Override
    public UserDto updateUser(String userId, UserDto user) {
        UserDto returnValue = new UserDto();

        UserEntity userEntity = userRepository.findByUserId(userId);

        if (userEntity == null)
            throw new APIException(500, ErrorMessages.NO_RECORD_FOUND.getErrorMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);

        userEntity.setFirstName(user.getFirstName());
        userEntity.setLastName(user.getLastName());

        // user = user.toBuilder().firstName(
        // StringUtils.isNotEmpty(userData.getFirstName()) ? userData.getFirstName() :
        // user.getFirstName())
        // .lastName(
        // StringUtils.isNotEmpty(userData.getLastName()) ? userData.getLastName()
        // : user.getLastName())
        // .build();

        // usersMap.replace(userId, user);

        UserEntity updatedUserDetails = userRepository.save(userEntity);
        returnValue = new ModelMapper().map(updatedUserDetails, UserDto.class);

        return returnValue;
    }

    @Override
    public UserDto createUser(UserDto userData) {
        String userEmail = userData.getEmail();

        if (!isUserInEmailPresentInDB(userEmail)) {

            UserEntity userEntity = new UserEntity();
            BeanUtils.copyProperties(userData, userEntity);

            String userId = utils.generateUserUUID();
            userEntity.setUserId(userId);
            // userEntity.setEncryptedPasswrod(passwordEncoder.encode(userData.getPassword()));
            userEntity.setEncryptedPasswrod("password hash");

            UserEntity storedEntity = userRepository.save(userEntity);

            UserDto returnObj = new UserDto();
            BeanUtils.copyProperties(storedEntity, returnObj);
            return returnObj;
        }

        throw new APIException(400, String.format(USER_EXISTS_MESSAGE, userEmail), HttpStatus.BAD_REQUEST);
    }

    private boolean isUserInEmailPresentInDB(String userEmail) {
        return userRepository.findByEmail(userEmail) != null;
    }

    @Transactional
    @Override
    public void deleteUser(String userId) {
        UserEntity userEntity = userRepository.findByUserId(userId);

        if (userEntity == null)
            throw new APIException(500, ErrorMessages.NO_RECORD_FOUND.getErrorMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);

        userRepository.delete(userEntity);
    }
}