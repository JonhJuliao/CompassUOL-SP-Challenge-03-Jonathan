package com.challenge3.msusers.service.impl;

import com.challenge3.msusers.entity.Role;
import com.challenge3.msusers.payload.RoleDto;
import com.challenge3.msusers.repository.RoleRepository;
import com.challenge3.msusers.service.RoleService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    private RoleRepository roleRepository;

    private ModelMapper mapper;

    public RoleServiceImpl(RoleRepository roleRepository, ModelMapper mapper) {
        this.roleRepository = roleRepository;
        this.mapper = mapper;
    }

    @Override
    public RoleDto createRole(RoleDto roleDto) {
        var role = mapToEntity(roleDto);
        var savedRole = roleRepository.save(role);
        return mapToDto(savedRole);
    }
    private RoleDto mapToDto(Role role) {
        return mapper.map(role, RoleDto.class);
    }

    private Role mapToEntity(RoleDto roleDto) {
        return mapper.map(roleDto, Role.class);
    }
}
