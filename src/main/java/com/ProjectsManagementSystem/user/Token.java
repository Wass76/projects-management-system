package com.ProjectsManagementSystem.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Token {
@Id
    @SequenceGenerator(
            name = "token_id",
            sequenceName = "token_id",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "token_id"
    )
    private Integer id;

    private String token;
    @ManyToOne
    private User user;
    private Boolean isRevoked;
    private Boolean isExpired;
}
