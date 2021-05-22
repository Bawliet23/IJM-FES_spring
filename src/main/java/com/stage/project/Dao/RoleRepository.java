package com.stage.project.Dao;

import com.stage.project.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Role findFirstByRoleName(String role);
}
