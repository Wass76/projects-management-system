package com.ProjectsManagementSystem.task;

import com.ProjectsManagementSystem.bug.Bug;
import com.ProjectsManagementSystem.project.Project;
import com.ProjectsManagementSystem.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Task {
    @Id
    @SequenceGenerator(
            name = "task_id",
            sequenceName = "task_id",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
    generator = "task_id")
    private Integer id;
//    private String title;
    private String description;
    private TaskStatus status;


    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "project_id" , nullable = false)
    private Project project;

    @OneToMany(mappedBy = "task" , fetch = FetchType.EAGER)
    List<Bug> bugList;

    @CreatedDate
    @Column(
            nullable = false,
            updatable = false
    )
    private LocalDateTime createDate;

    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime lastModified;

    @CreatedBy
    @Column(
            nullable = false,
            updatable = false
    )
    private Integer createdBy;

    @LastModifiedBy
    @Column(insertable = false
    )
    private Integer lastModifiedBy;
}

