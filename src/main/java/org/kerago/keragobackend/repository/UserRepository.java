package org.kerago.keragobackend.repository;

import org.kerago.keragobackend.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<Users,Long> {

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);

    Optional<Users> findByUsername(String username);
}
