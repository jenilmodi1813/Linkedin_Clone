package com.linkedin.Company_Service.repositories;

import com.linkedin.Company_Service.entities.Category;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
    Optional<Category> findByName(@NotEmpty @Size(min = 3, max = 255) String name);
}
