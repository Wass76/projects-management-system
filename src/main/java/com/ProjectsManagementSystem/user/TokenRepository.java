package com.ProjectsManagementSystem.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer> {
    @Query("SELECT t from Token t" +
            " where t.token = :token " +
            "AND t.isRevoked = false")
    public Optional<Token> findByToken(@Param("token") String token);


    @Query("SELECT t FROM Token t " +
            "WHERE t.isExpired = false " +  // Only tokens marked as expired
            "AND t.user.id = :userId " +
            "AND t.isRevoked = false")  // Not revoked
    public Token findByUserId(@Param("userId") Integer userId);

    public  List<Token>findTokensByUserId(Integer id);
}
