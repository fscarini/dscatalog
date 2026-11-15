package com.devcarini.dscatalog.dto;

import com.devcarini.dscatalog.entity.Role;

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
