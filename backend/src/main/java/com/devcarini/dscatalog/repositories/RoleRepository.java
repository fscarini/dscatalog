package com.devcarini.dscatalog.repositories;

import com.devcarini.dscatalog.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
