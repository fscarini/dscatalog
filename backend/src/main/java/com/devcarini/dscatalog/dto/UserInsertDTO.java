package com.devcarini.dscatalog.dto;


import com.devcarini.dscatalog.entity.User;

import java.util.Set;
import java.util.stream.Collectors;

public record UserInsertDTO(
        Long id,
        String firstName,
        String lastName,
        String password,
        String email,
        Set<RoleDTO> roles
) {
    public UserInsertDTO(User entity){
        this(
                entity.getId(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getPassword(), 
                entity.getEmail(),
                entity.getRoles().stream()
                        .map(RoleDTO::new)
                        .collect(Collectors.toSet())
        );
    }
}
