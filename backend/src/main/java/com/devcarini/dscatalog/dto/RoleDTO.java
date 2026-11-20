package com.devcarini.dscatalog.dto;

import com.devcarini.dscatalog.entities.Role;

public record RoleDTO(
        Long id,
        String authority
){

    public RoleDTO(Role entity){
        this(
                entity.getId(),
                entity.getAuthority()
        );
    }
}
