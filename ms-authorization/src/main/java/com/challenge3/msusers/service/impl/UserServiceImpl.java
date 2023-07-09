package com.challenge3.msusers.service.impl;

import com.challenge3.msusers.amqp.Producer;
import com.challenge3.msusers.entity.Role;
import com.challenge3.msusers.entity.User;
import com.challenge3.msusers.exception.JwtTokenMalformedException;
import com.challenge3.msusers.exception.JwtTokenMissingException;
import com.challenge3.msusers.exception.ResourceNotFoundException;
import com.challenge3.msusers.payload.LoginDto;
import com.challenge3.msusers.payload.UserDto;
import com.challenge3.msusers.repository.RoleRepository;
import com.challenge3.msusers.repository.UserRepository;
import com.challenge3.msusers.security.JwtUtil;
import com.challenge3.msusers.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final ModelMapper mapper;

    private final RoleRepository roleRepository;

    private final Producer producer;

    private final BCryptPasswordEncoder passwordEncoder;

    private final JwtUtil jwtUtil;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           ModelMapper mapper,
                           RoleRepository roleRepository,
                           Producer producer,
                           BCryptPasswordEncoder passwordEncoder,
                           JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.roleRepository = roleRepository;
        this.producer = producer;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        var user = mapToEntity(userDto);
        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);
        Set<Role> roles = new HashSet<>();
        for(Long roleId : user.getRole()){
            Role role = roleRepository.findById(roleId)
                    .orElseThrow(()-> new ResourceNotFoundException("Role", "id", roleId));
            roles.add(role);
        }
        user.setRoles(roles);
        var savedUser = userRepository.save(user);
        producer.sendMessageUserCreated(userDto);
        return mapToDto(savedUser);
    }

    @Override
    public UserDto getUserById(Long id) {
        var user = userRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("User", "id", id));
        return mapToDto(user);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Long id) {
        var user = userRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("User", "id", id));
        if(userDto.getFirstName() != null){
            user.setFirstName(userDto.getFirstName());
        }
        if(userDto.getLastName() != null){
            user.setLastName(userDto.getLastName());
        }
        if(userDto.getEmail() != null){
            user.setEmail(userDto.getEmail());
        }
        if(userDto.getPassword() != null){
            String encryptedPassword = passwordEncoder.encode(userDto.getPassword());
            user.setPassword(encryptedPassword);
        }
        if(userDto.getRole() != null){
            Set<Role> roles = new HashSet<>();
            for(Long roleId : userDto.getRole()){
                Role role = roleRepository.findById(roleId)
                        .orElseThrow(() -> new ResourceNotFoundException("Role", "id", roleId));
                roles.add(role);
            }
            user.setRoles(roles);
        }
        var updatedUser = userRepository.save(user);
        return mapToDto(updatedUser);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }

    @Override
    public String login(LoginDto loginDto) {
        User user = userRepository.getUserByEmail(loginDto.getEmail());
        if(user == null || !passwordEncoder.matches(loginDto.getPassword(), user.getPassword())){
            new ResourceNotFoundException("User", "email", loginDto.getEmail());
        }
        return jwtUtil.generateToken(loginDto.getEmail());
    }

    @Override
    public void validateToken(String token) throws JwtTokenMalformedException, JwtTokenMissingException {
        log.info("Token {}", token);
        jwtUtil.validateToken(token);
    }

    private UserDto mapToDto(User user){
        return mapper.map(user, UserDto.class);
    }

    private User mapToEntity(UserDto userDto){
        return mapper.map(userDto, User.class);
    }


}
