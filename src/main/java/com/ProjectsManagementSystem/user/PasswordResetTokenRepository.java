package com.ProjectsManagementSystem.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Integer> {

    public PasswordResetToken findByToken(String token);

    public boolean existsByToken(String token);
}
