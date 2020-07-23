package com.yte.intern.project.manageclients.repository;

import com.yte.intern.project.manageclients.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    Client findByTcKimlikNo(String tcKimlikNo);

    void deleteByTcKimlikNo(String tcKimlikNo);

    Boolean existsByTcKimlikNo(String tcKimlikNo);

    Boolean existsByEmail(String email);

    Optional<Client> findByUsername(String username);

    Boolean existsByUsername(String username);

}
