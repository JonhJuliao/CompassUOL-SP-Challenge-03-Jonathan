package com.challenge3.msusers.service;

import com.challenge3.msusers.payload.UserDto;

public interface UserService {

    UserDto createUser(UserDto userDto);

    UserDto getUserById(Long id);

    UserDto updateUser(UserDto userDto, Long id);

}
