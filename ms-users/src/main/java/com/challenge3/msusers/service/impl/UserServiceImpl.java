package com.challenge3.msusers.service.impl;

import com.challenge3.msusers.entity.Role;
import com.challenge3.msusers.entity.User;
import com.challenge3.msusers.exception.ResourceNotFoundException;
import com.challenge3.msusers.payload.UserDto;
import com.challenge3.msusers.repository.RoleRepository;
import com.challenge3.msusers.repository.UserRepository;
import com.challenge3.msusers.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private ModelMapper mapper;

    private RoleRepository roleRepository;

    public UserServiceImpl(UserRepository userRepository,
                           ModelMapper mapper,
                           RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        var user = mapToEntity(userDto);
        Set<Role> roles = new HashSet<>();
        for(Long roleId : user.getRole()){
            Role role = roleRepository.findById(roleId)
                    .orElseThrow(()-> new ResourceNotFoundException("Role", "id", roleId));
            roles.add(role);
        }
        user.setRoles(roles);
        var savedUser = userRepository.save(user);
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
            user.setPassword(userDto.getPassword());
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

    private UserDto mapToDto(User user){
        return mapper.map(user, UserDto.class);
    }

    private User mapToEntity(UserDto userDto){
        return mapper.map(userDto, User.class);
    }
}
