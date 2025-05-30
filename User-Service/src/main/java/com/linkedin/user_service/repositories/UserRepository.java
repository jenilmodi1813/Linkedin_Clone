package com.linkedin.user_service.repositories;

import com.linkedin.user_service.entities.Users;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users,Long> {
    Optional<Users> findByEmail(@NotEmpty String receiverEmail);

    Optional<Users> findByUsernameAndEmail(@NotEmpty String username, @Email String email);
}
