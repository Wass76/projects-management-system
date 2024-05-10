package com.ProjectsManagementSystem.project;

import com.ProjectsManagementSystem.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity( name = "project_membership")
@EntityListeners(AuditingEntityListener.class)
public class ProjectMembership {
    @Id
    @SequenceGenerator(
            name = "project_membership_id",
            sequenceName = "project_membership_id",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "project_membership_id"
    )
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String role;
}
