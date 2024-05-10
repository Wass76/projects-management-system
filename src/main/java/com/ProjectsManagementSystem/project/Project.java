package com.ProjectsManagementSystem.project;

import com.ProjectsManagementSystem.task.Task;
import com.ProjectsManagementSystem.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Project {
    @Id
    @SequenceGenerator(
            name = "project-id",
            sequenceName = "project_id",
            allocationSize = 1)
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "project_id"
    )
    private Integer id;

//    private String GUID;
    private String name;
    private String description;


    private ProjectStatus status;


    @OneToMany(mappedBy = "project", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Task> tasks;

    @JsonIgnore
    @ManyToMany(mappedBy = "projects")
    private List<User> members = new ArrayList<>();

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
    @Column(insertable = false)
    private Integer lastModifiedBy;

//    private Integer user_id;

}
