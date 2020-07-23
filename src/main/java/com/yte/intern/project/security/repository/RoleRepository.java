package com.yte.intern.project.security.repository;

import com.yte.intern.project.security.models.ERole;
import com.yte.intern.project.security.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    Optional<Role> findByName(ERole name);
}
