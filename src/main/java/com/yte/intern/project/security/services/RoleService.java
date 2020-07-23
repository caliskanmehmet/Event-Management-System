package com.yte.intern.project.security.services;

import com.yte.intern.project.security.models.ERole;
import com.yte.intern.project.security.models.Role;
import com.yte.intern.project.security.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class RoleService {

    @Autowired
    RoleRepository roleRepository;

    @PostConstruct
    public void insertRoles() {
        roleRepository.save(new Role(ERole.ROLE_USER));
        roleRepository.save(new Role(ERole.ROLE_MODERATOR));
        roleRepository.save(new Role(ERole.ROLE_ADMIN));
    }
}
