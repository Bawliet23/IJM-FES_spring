package com.stage.project.Dao;

import com.stage.project.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    User findUserByUsername(String username);
    Page<User> findAllByRole_RoleName(String role_name, Pageable pageable);
    Page<User> findAll(Pageable pageable);
}
