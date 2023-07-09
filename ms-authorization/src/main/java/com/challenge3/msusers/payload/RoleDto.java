package com.challenge3.msusers.payload;

import com.challenge3.msusers.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class RoleDto {

    private Long id;

    private String name;

    @JsonIgnore
    private Set<User> users = new HashSet<>();

}
