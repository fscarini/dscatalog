package com.devcarini.dscatalog.repository;

import com.devcarini.dscatalog.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
