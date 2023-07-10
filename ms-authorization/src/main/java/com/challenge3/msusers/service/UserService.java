package com.challenge3.msusers.service;

import com.challenge3.msusers.entity.User;
import com.challenge3.msusers.exception.JwtTokenMalformedException;
import com.challenge3.msusers.exception.JwtTokenMissingException;
import com.challenge3.msusers.payload.LoginDto;
import com.challenge3.msusers.payload.UserDto;

public interface UserService {

    UserDto createUser(UserDto userDto);

    UserDto getUserById(Long id);

    UserDto updateUser(UserDto userDto, Long id);

    User getUserByEmail(String email);

    String login(LoginDto loginDto);

    void validateToken(String token) throws JwtTokenMalformedException, JwtTokenMissingException;

    UserDto createAdmin(UserDto userDto);

    UserDto updateUserToAdmin(UserDto userDto, Long id);
}
