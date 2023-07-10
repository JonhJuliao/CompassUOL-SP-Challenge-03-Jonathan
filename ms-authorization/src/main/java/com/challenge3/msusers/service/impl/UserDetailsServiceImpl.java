package com.challenge3.msusers.service.impl;

import com.challenge3.msusers.entity.User;
import com.challenge3.msusers.exception.ResourceNotFoundException;
import com.challenge3.msusers.payload.CustomUserDetails;
import com.challenge3.msusers.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.getUserByEmail(email);
        if(user == null){
            throw new ResourceNotFoundException("User", "email", email);
        }
        return new CustomUserDetails(
                user.getEmail(),
                user.getPassword(),
                user.getRoles()
        );
    }
}
