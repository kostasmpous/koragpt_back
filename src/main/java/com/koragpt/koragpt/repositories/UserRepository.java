package com.koragpt.koragpt.repositories;

import com.koragpt.koragpt.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);

    Optional<User> findUserByUsername(String username);

    Optional<User> findUserById(Long Id);
}
