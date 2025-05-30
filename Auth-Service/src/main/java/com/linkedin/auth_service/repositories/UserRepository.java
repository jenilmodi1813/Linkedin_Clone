package com.linkedin.auth_service.repositories;

import com.linkedin.auth_service.entities.Users;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users,Long> {
    Optional<Users> findByUsernameAndEmail(@NotEmpty String username, @Email String email);

    Optional<Users> findByEmail(@NotEmpty() @Email String email);
}
