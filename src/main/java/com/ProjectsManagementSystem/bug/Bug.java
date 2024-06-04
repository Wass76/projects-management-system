package com.ProjectsManagementSystem.bug;

import com.ProjectsManagementSystem.comment.Comment;
import com.ProjectsManagementSystem.task.Task;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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
public class Bug {
    @Id
    @SequenceGenerator(
            name = "bug_id",
            sequenceName = "bug_id",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "bug_id"
    )
    private Integer id;
    private String title;
    private String description;
    private Integer priority;

    @ManyToOne
    @JsonIgnore
    @NotNull
    @JoinColumn(name = "task_id", nullable = false ,updatable = false)
    private Task task;

    @OneToMany(mappedBy = "bug",fetch = FetchType.EAGER)
    private List<Comment> comments;


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
