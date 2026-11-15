package com.devcarini.dscatalog.repository;

import com.devcarini.dscatalog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
